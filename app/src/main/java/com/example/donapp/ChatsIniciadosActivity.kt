package com.example.donapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatsIniciadosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatPreviewAdapter
    private val chats = mutableListOf<ChatPreview>()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun eliminarChatCompleto(correoDestino: String) {
        val correoActual = auth.currentUser?.email ?: return

        db.collection("chats_participantes")
            .document(correoActual)
            .collection("conversaciones")
            .document(correoDestino)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Chat eliminado", Toast.LENGTH_SHORT).show()
                cargarChats()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_chats)


        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ChatPreviewAdapter(
            chats = chats,
            onChatClick = { chat -> abrirChat(chat) },
            onEliminarClick = { chat -> eliminarChatCompleto(chat.correoDestino) }
        )


        recyclerView.adapter = adapter

        cargarChats()
    }

    private fun cargarChats() {
        val correoActual = auth.currentUser?.email ?: return

        db.collection("chats_participantes")
            .document(correoActual)
            .collection("conversaciones")
            .get()
            .addOnSuccessListener { result ->
                chats.clear()
                for (document in result) {
                    val chat = document.toObject(ChatPreview::class.java)
                    if (chat.activo) {
                        chats.add(chat)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar chats", Toast.LENGTH_SHORT).show()
                Log.e("ChatsIniciados", "Fallo en carga: ${it.message}", it)
            }
    }

    private fun abrirChat(chat: ChatPreview) {
        val correoActual = auth.currentUser?.email ?: return
        val receptor = chat.correoDestino

        if (receptor.isBlank() || receptor == correoActual) {
            Toast.makeText(this, "Receptor no válido", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, ChatActivity::class.java).apply {
            putExtra("destinatarioEmail", receptor)
            putExtra("productoNombre", "Conversación") // 👈 Puedes mostrar algo genérico o último producto
        }
        startActivity(intent)
    }
}
