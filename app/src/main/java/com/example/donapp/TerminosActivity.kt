package com.example.donapp

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class TerminosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terminos)

        val textoTerminos = findViewById<TextView>(R.id.textoTerminos)
        textoTerminos.text = Html.fromHtml(
            getString(R.string.terminos_condiciones),
            Html.FROM_HTML_MODE_LEGACY
        )

        val btnAceptar = findViewById<Button>(R.id.btnAceptarTerminos)
        btnAceptar.setOnClickListener {
            val prefs = getSharedPreferences("manitas_prefs", MODE_PRIVATE)
            prefs.edit().putBoolean("terminos_aceptados", true).apply()

            // Obtener datos enviados desde RegistroActivity
            val uid = intent.getStringExtra("uid") ?: return@setOnClickListener
            val nombre = intent.getStringExtra("nombre") ?: ""
            val correo = intent.getStringExtra("correo") ?: ""
            val domicilio = intent.getStringExtra("domicilio") ?: ""
            val codigo = intent.getStringExtra("codigo") ?: ""

            // Crear el objeto del usuario con clave "email"
            val usuario = hashMapOf(
                "uid" to uid,
                "nombre" to nombre,
                "email" to correo,  // IMPORTANTE: usar 'email' y no 'correo'
                "domicilio" to domicilio,
                "codigoInstitucional" to codigo
            )

            // Guardar usuario en Firestore
            val db = FirebaseFirestore.getInstance()
            db.collection("usuarios").document(uid)
                .set(usuario)
                .addOnSuccessListener {
                    Toast.makeText(this, "Términos aceptados. Registro completado", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

