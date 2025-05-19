package com.example.donapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MisChatsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val listaChats = mutableListOf<ChatPreview>()
    private lateinit var adapter: ChatPreviewAdapter
    private val db = FirebaseFirestore.getInstance()
    private val miCorreo = FirebaseAuth.getInstance().currentUser?.email.orEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_chats)

        recyclerView = findViewById(R.id.recyclerMisChats)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ChatPreviewAdapter(
            chats = listaChats,
            onChatClick = { chat ->
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("destinatarioEmail", chat.correoDestino)
                intent.putExtra("productoNombre", "Conversación")
                startActivity(intent)
            },
            onEliminarClick = { chat ->
                eliminarChatCompleto(chat.correoDestino)
            }
        )

        recyclerView.adapter = adapter
        cargarChats()
    }

    private fun cargarChats() {
        db.collection("chats_participantes")
            .document(miCorreo)
            .collection("conversaciones")
            .get()
            .addOnSuccessListener { result ->
                listaChats.clear()
                for (doc in result) {
                    val chat = doc.toObject(ChatPreview::class.java)
                    if (chat.activo) {
                        listaChats.add(chat)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar chats", Toast.LENGTH_SHORT).show()
            }
    }

    private fun eliminarChatCompleto(correoDestino: String) {
        db.collection("chats_participantes")
            .document(miCorreo)
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
}


