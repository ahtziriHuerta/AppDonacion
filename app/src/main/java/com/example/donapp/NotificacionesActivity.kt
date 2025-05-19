package com.example.donapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NotificacionesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificacionAdapter
    private lateinit var textoSinNotificaciones: TextView

    private val db = FirebaseFirestore.getInstance()
    private val notificaciones = mutableListOf<Pair<String, Notificacion>>()

    private val usuarioId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificaciones)

        recyclerView = findViewById(R.id.recyclerNotificaciones)
        textoSinNotificaciones = findViewById(R.id.textoSinNotificaciones)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = NotificacionAdapter(
            notificaciones,
            onEliminarClick = { docId -> eliminarNotificacion(docId) },
            onAceptarClick = { notificacion -> iniciarChat(notificacion) }
        )

        recyclerView.adapter = adapter

        cargarNotificaciones()
    }

    private fun cargarNotificaciones() {
        db.collection("usuarios")
            .document(usuarioId)
            .collection("notificaciones")
            .addSnapshotListener { snapshot, _ ->
                notificaciones.clear()
                if (snapshot != null) {
                    for (doc in snapshot) {
                        val noti = doc.toObject(Notificacion::class.java)
                        notificaciones.add(doc.id to noti)
                    }
                    adapter.notifyDataSetChanged()

                    textoSinNotificaciones.visibility =
                        if (notificaciones.isEmpty()) View.VISIBLE else View.GONE
                } else {
                    textoSinNotificaciones.visibility = View.VISIBLE
                }
            }
    }

    private fun eliminarNotificacion(docId: String) {
        db.collection("usuarios")
            .document(usuarioId)
            .collection("notificaciones")
            .document(docId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Notificación eliminada", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
            }
    }

    private fun iniciarChat(noti: Notificacion) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("correoDestino", noti.interesadoEmail)
        startActivity(intent)
    }
}

