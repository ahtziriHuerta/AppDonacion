package com.example.donapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var editMensaje: EditText
    private lateinit var btnEnviar: Button
    private lateinit var chatHeader: TextView

    private val db = FirebaseFirestore.getInstance()
    private val mensajes = mutableListOf<ChatMessage>()
    private lateinit var adapter: ChatAdapter

    private lateinit var emisor: String
    private lateinit var receptor: String
    private lateinit var chatId: String

    private val canalNotificacion = "nuevo_mensaje"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.recyclerMensajes)
        editMensaje = findViewById(R.id.editMensaje)
        btnEnviar = findViewById(R.id.btnEnviar)
        chatHeader = findViewById(R.id.textChatHeader)

        emisor = FirebaseAuth.getInstance().currentUser?.email ?: ""
        receptor = intent.getStringExtra("destinatarioEmail") ?: ""
        val producto = intent.getStringExtra("productoNombre") ?: ""

        Log.d("ChatActivity", "Emisor: $emisor")
        Log.d("ChatActivity", "Receptor: $receptor")
        Log.d("ChatActivity", "Producto: $producto")

        if (receptor.isBlank()) {
            Toast.makeText(this, "Receptor no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        chatHeader.text = getString(R.string.chat_header, producto, receptor)

        chatId = if (emisor < receptor) "$emisor-$receptor" else "$receptor-$emisor"

        adapter = ChatAdapter(mensajes, emisor)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        crearCanalDeNotificacion()

        btnEnviar.setOnClickListener {
            val texto = editMensaje.text.toString().trim()
            if (texto.isNotEmpty()) {
                val mensaje = ChatMessage(
                    mensaje = texto,
                    emisor = emisor,
                    receptor = receptor,
                    fecha = Timestamp.now()
                )

                Log.d("ChatActivity", "Enviando mensaje: $texto")
                db.collection("chats/$chatId/mensajes").add(mensaje)

                db.collection("chats_participantes")
                    .document(emisor)
                    .collection("conversaciones")
                    .document(receptor)
                    .set(mapOf("correoDestino" to receptor, "activo" to true))

                db.collection("chats_participantes")
                    .document(receptor)
                    .collection("conversaciones")
                    .document(emisor)
                    .set(mapOf("correoDestino" to emisor, "activo" to true))

                editMensaje.setText("")
            }
        }

        db.collection("chats/$chatId/mensajes")
            .orderBy("fecha")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val mensajesAnteriores = mensajes.size
                    mensajes.clear()
                    for (doc in snapshot) {
                        mensajes.add(doc.toObject(ChatMessage::class.java))
                    }
                    adapter.notifyDataSetChanged()
                    recyclerView.scrollToPosition(mensajes.size - 1)

                    if (mensajes.size > mensajesAnteriores) {
                        val ultimo = mensajes.last()
                        if (ultimo.emisor != emisor) {
                            mostrarNotificacion(ultimo.mensaje)
                        }
                    }
                }
            }
    }

    private fun crearCanalDeNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                canalNotificacion,
                "Mensajes Nuevos",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificaciones de nuevos mensajes"
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal)
        }
    }

    private fun mostrarNotificacion(texto: String) {
        val sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001)
                return
            }
        }

        val notificacion = NotificationCompat.Builder(this, canalNotificacion)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Nuevo mensaje")
            .setContentText(texto)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(sonido)
            .build()

        NotificationManagerCompat.from(this).notify(
            System.currentTimeMillis().toInt(),
            notificacion
        )
    }
}
