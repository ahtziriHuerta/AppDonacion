package com.example.donapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AgregarProductoActivity : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var descripcionEditText: EditText
    private lateinit var btnSeleccionarImagen: Button
    private lateinit var btnSubir: Button
    private lateinit var imageView: ImageView
    private lateinit var mensajeConfirmacion: TextView
    private lateinit var btnVolverDashboard: Button

    private var imagenUri: Uri? = null
    private val storage = FirebaseStorage.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val PICK_IMAGE_REQUEST = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)

        nombreEditText = findViewById(R.id.editNombre)
        descripcionEditText = findViewById(R.id.editDescripcion)
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        btnSubir = findViewById(R.id.btnSubir)
        imageView = findViewById(R.id.imageViewProducto)
        mensajeConfirmacion = findViewById(R.id.mensajeConfirmacion)
        btnVolverDashboard = findViewById(R.id.btnVolverDashboard)

        mensajeConfirmacion.visibility = View.GONE
        btnVolverDashboard.visibility = View.GONE

        btnSeleccionarImagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnSubir.setOnClickListener {
            subirProducto()
        }

        btnVolverDashboard.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imagenUri = data.data
            imageView.setImageURI(imagenUri)
        }
    }

    private fun subirProducto() {
        val nombre = nombreEditText.text.toString().trim()
        val descripcion = descripcionEditText.text.toString().trim()
        val usuarioId = auth.currentUser?.uid
        val correo = auth.currentUser?.email

        if (nombre.isEmpty() || descripcion.isEmpty() || imagenUri == null || usuarioId == null) {
            Toast.makeText(this, "Completa todos los campos e inicia sesión", Toast.LENGTH_SHORT).show()
            return
        }

        val nombreArchivo = "${UUID.randomUUID()}.jpg"
        val rutaStorage = "productos/$usuarioId/$nombreArchivo"
        val referenciaStorage = storage.reference.child(rutaStorage)

        btnSubir.isEnabled = false
        btnSubir.text = "Subiendo..."

        referenciaStorage.putFile(imagenUri!!)
            .addOnSuccessListener {
                referenciaStorage.downloadUrl.addOnSuccessListener { uri ->
                    val producto = hashMapOf(
                        "nombre" to nombre,
                        "descripcion" to descripcion,
                        "imagenUrl" to uri.toString(),
                        "usuarioCorreo" to correo,
                        "usuarioId" to usuarioId
                    )

                    db.collection("productos")
                        .add(producto)
                        .addOnSuccessListener {
                            Toast.makeText(this, "✅ Producto subido exitosamente", Toast.LENGTH_SHORT).show()
                            nombreEditText.text.clear()
                            descripcionEditText.text.clear()
                            imageView.setImageDrawable(null)
                            imagenUri = null
                            btnSubir.text = "Subir"
                            btnSubir.isEnabled = true
                            mensajeConfirmacion.visibility = View.VISIBLE
                            btnVolverDashboard.visibility = View.VISIBLE
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error al guardar en Firestore", Toast.LENGTH_SHORT).show()
                            btnSubir.text = "Subir"
                            btnSubir.isEnabled = true
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "❌ Error al subir imagen: ${e.message}", Toast.LENGTH_LONG).show()
                btnSubir.text = "Subir"
                btnSubir.isEnabled = true
                e.printStackTrace()
            }
    }
}

