package com.example.donapp

import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UsuariosCercanosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    private val usuariosCercanos = mutableListOf<Pair<String, Double>>() // correo y distancia

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios_cercanos)

        recyclerView = findViewById(R.id.recyclerUsuariosCercanos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        obtenerYCompararUbicaciones()
    }

    private fun obtenerYCompararUbicaciones() {
        val usuarioActual = FirebaseAuth.getInstance().currentUser ?: return
        val userId = usuarioActual.uid

        db.collection("ubicaciones").document(userId).get().addOnSuccessListener { doc ->
            val lat = doc.getDouble("lat") ?: return@addOnSuccessListener
            val lng = doc.getDouble("lng") ?: return@addOnSuccessListener

            val miUbicacion = Location("").apply {
                latitude = lat
                longitude = lng
            }

            db.collection("ubicaciones").get().addOnSuccessListener { result ->
                val total = result.size()
                var procesados = 0

                for (document in result) {
                    val otroId = document.id
                    val otroLat = document.getDouble("lat") ?: continue
                    val otroLng = document.getDouble("lng") ?: continue

                    val ubicacionOtro = Location("").apply {
                        latitude = otroLat
                        longitude = otroLng
                    }

                    val distancia = miUbicacion.distanceTo(ubicacionOtro) / 1000.0 // en km

                    db.collection("usuarios").document(otroId).get().addOnSuccessListener { userDoc ->
                        var correo = userDoc.getString("correo") ?: "Desconocido"
                        if (otroId == userId) {
                            correo += " (Tú)"
                        }
                        usuariosCercanos.add(Pair(correo, distancia))

                        procesados++
                        if (procesados == total) {
                            usuariosCercanos.sortBy { it.second }
                            recyclerView.adapter = UsuariosCercanosAdapter(usuariosCercanos)
                        }
                    }.addOnFailureListener {
                        procesados++
                        if (procesados == total) {
                            usuariosCercanos.sortBy { it.second }
                            recyclerView.adapter = UsuariosCercanosAdapter(usuariosCercanos)
                        }
                    }
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error al obtener ubicación", Toast.LENGTH_SHORT).show()
        }
    }
}
