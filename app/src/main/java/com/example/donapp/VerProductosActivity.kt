package com.example.donapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class VerProductosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val productos = mutableListOf<Producto>()
    private lateinit var adapter: ProductoPublicoAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_productos)

        recyclerView = findViewById(R.id.recyclerTodosProductos)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        adapter = ProductoPublicoAdapter(productos) { producto ->
            abrirChat(producto)
            registrarInteres(producto)
        }

        recyclerView.adapter = adapter
        cargarProductos()
    }

    private fun cargarProductos() {
        db.collection("productos")
            .get()
            .addOnSuccessListener { result ->
                productos.clear()
                for (doc in result) {
                    val producto = doc.toObject(Producto::class.java)
                    productos.add(producto)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar productos", Toast.LENGTH_SHORT).show()
            }
    }

    private fun abrirChat(producto: Producto) {
        val intent = Intent(this, ChatActivity::class.java).apply {
            putExtra("destinatarioEmail", producto.usuarioCorreo)
            putExtra("productoNombre", producto.nombre)
        }

        if (producto.usuarioCorreo.isNullOrBlank()) {
            Toast.makeText(this, "❌ Receptor no válido", Toast.LENGTH_SHORT).show()
            return
        }

        startActivity(intent)
    }

    private fun registrarInteres(producto: Producto) {
        val usuarioActual = FirebaseAuth.getInstance().currentUser ?: return

        val notificacion = hashMapOf(
            "producto" to producto.nombre,
            "interesadoEmail" to usuarioActual.email,
            "fecha" to Timestamp.now()
        )

        FirebaseFirestore.getInstance()
            .collection("usuarios/${producto.usuarioId}/notificaciones")
            .add(notificacion)
            .addOnSuccessListener {
                Toast.makeText(this, "✅ Se notificó al dueño", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "❌ Error al registrar interés", Toast.LENGTH_SHORT).show()
            }
    }
}
