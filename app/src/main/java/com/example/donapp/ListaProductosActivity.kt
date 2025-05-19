package com.example.donapp

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ListaProductosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var textoVacio: TextView
    private lateinit var btnAgregarProducto: Button

    private val db = FirebaseFirestore.getInstance()
    private val productos = mutableListOf<Producto>()
    private lateinit var adapter: ProductoAdapter
    private val usuarioIdActual = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_productos)

        recyclerView = findViewById(R.id.recyclerProductos)
        textoVacio = findViewById(R.id.textoVacio)
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto)

        recyclerView.layoutManager = GridLayoutManager(this,2)

        adapter = ProductoAdapter(
            context = this,
            productos = productos,
            onEliminarClick = { producto -> eliminarProducto(producto) },
            onProductoEliminado = { producto -> eliminarLocalmente(producto) }
        )

        recyclerView.adapter = adapter

        btnAgregarProducto.setOnClickListener {
            startActivity(Intent(this, AgregarProductoActivity::class.java))
        }

        asignarUsuarioActualATodosLosProductos()
        cargarProductos()
        reemplazarImagenesRotasConDefecto()
    }

    private fun cargarProductos() {
        val usuarioId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("productos")
            .whereEqualTo("usuarioId", usuarioId)
            .get()
            .addOnSuccessListener { result ->
                productos.clear()
                for (doc in result) {
                    val producto = doc.toObject(Producto::class.java)
                    productos.add(producto)
                }

                adapter.notifyDataSetChanged()

                textoVacio.visibility = if (productos.isEmpty()) View.VISIBLE else View.GONE
                btnAgregarProducto.visibility = if (productos.isEmpty()) View.VISIBLE else View.GONE
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar productos", Toast.LENGTH_SHORT).show()
            }
    }

    private fun eliminarProducto(producto: Producto) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar producto")
            .setMessage("¿Estás seguro de que deseas eliminar '${producto.nombre}'?")
            .setPositiveButton("Sí") { _, _ ->
                val imagenUrl = producto.imagenUrl

                if (!imagenUrl.isNullOrEmpty()) {
                    try {
                        val ref = FirebaseStorage.getInstance().getReferenceFromUrl(imagenUrl)

                        ref.delete().addOnSuccessListener {
                            eliminarDocumentoFirestore(producto)
                        }.addOnFailureListener { e ->
                            // Si la imagen no existe, aún así eliminar el documento
                            if (e.message?.contains("Object does not exist") == true ||
                                e.message?.contains("404") == true) {
                                eliminarDocumentoFirestore(producto)
                            } else {
                                Toast.makeText(this, "Error al eliminar imagen: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }

                    } catch (e: Exception) {
                        // Si el URL es inválido, igual borramos el documento
                        eliminarDocumentoFirestore(producto)
                    }
                } else {
                    eliminarDocumentoFirestore(producto)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarDocumentoFirestore(producto: Producto) {
        db.collection("productos")
            .document(producto.id ?: return)
            .delete()
            .addOnSuccessListener {
                eliminarLocalmente(producto)
                Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al eliminar producto de Firestore", Toast.LENGTH_SHORT).show()
            }
    }


    private fun eliminarLocalmente(producto: Producto) {
        val index = productos.indexOf(producto)
        if (index >= 0) {
            productos.removeAt(index)
            adapter.notifyItemRemoved(index)
        }

        textoVacio.visibility = if (productos.isEmpty()) View.VISIBLE else View.GONE
        btnAgregarProducto.visibility = if (productos.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun asignarUsuarioActualATodosLosProductos() {
        val usuarioId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("productos")
            .get()
            .addOnSuccessListener { documentos ->
                for (documento in documentos) {
                    val idDocumento = documento.id
                    if (!documento.contains("usuarioId")) {
                        db.collection("productos")
                            .document(idDocumento)
                            .update("usuarioId", usuarioId)
                            .addOnSuccessListener {
                                Log.d("Migración", "Asignado usuarioId a $idDocumento")
                            }
                            .addOnFailureListener {
                                Log.e("Migración", "Error al asignar usuarioId a $idDocumento", it)
                            }
                    }
                }
            }
    }

    private fun reemplazarImagenesRotasConDefecto() {
        val urlImagenPorDefecto =
            "https://firebasestorage.googleapis.com/v0/b/manitas-4ae57.firebasestorage.app/o/default%2Fimagen%20rota.png?alt=media&token=0eb0bfe2-e1c4-4813-885a-b14dd68ab552"
        val storage = FirebaseStorage.getInstance()

        db.collection("productos")
            .get()
            .addOnSuccessListener { documentos ->
                for (doc in documentos) {
                    val productoId = doc.id
                    val imagenUrl = doc.getString("imagenUrl") ?: ""

                    if (imagenUrl == urlImagenPorDefecto || imagenUrl.isEmpty()) continue

                    try {
                        val ref = storage.getReferenceFromUrl(imagenUrl)

                        ref.metadata
                            .addOnSuccessListener {
                                Log.d("Limpieza", "✅ Imagen válida para $productoId")
                            }
                            .addOnFailureListener {
                                if (it.message?.contains("Object does not exist") == true || it.message?.contains("404") == true) {
                                    db.collection("productos").document(productoId)
                                        .delete()
                                        .addOnSuccessListener {
                                            Log.w("Limpieza", "🗑 Producto $productoId eliminado por imagen rota")
                                        }
                                } else {
                                    Log.e("Limpieza", "⚠️ Error inesperado en $productoId: ${it.message}")
                                }
                            }

                    } catch (e: Exception) {
                        Log.e("Limpieza", "❌ URL inválida en $productoId: ${e.message}")
                    }
                }
            }
            .addOnFailureListener {
                Log.e("Limpieza", "Error al obtener productos: ${it.message}")
            }
    }
}
