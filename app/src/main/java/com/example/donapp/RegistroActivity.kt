package com.example.donapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        auth = FirebaseAuth.getInstance()

        val editNombre = findViewById<EditText>(R.id.editNombre)
        val editCorreo = findViewById<EditText>(R.id.editCorreo)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val editDomicilio = findViewById<EditText>(R.id.editDomicilio)
        val editCodigo = findViewById<EditText>(R.id.editCodigoInstitucional)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)

        btnRegistrarse.setOnClickListener {
            val nombre = editNombre.text.toString().trim()
            val correo = editCorreo.text.toString().trim()
            val password = editPassword.text.toString().trim()
            val domicilio = editDomicilio.text.toString().trim()
            val codigo = editCodigo.text.toString().trim()

            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || domicilio.isEmpty() || codigo.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(this, "Correo inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!correo.endsWith("@alumnos.udg.mx") && !correo.endsWith("@cucei.udg.mx")) {
                Toast.makeText(this, "Solo se permite correo institucional de CUCEI", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear usuario en FirebaseAuth
            auth.createUserWithEmailAndPassword(correo, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid ?: return@addOnCompleteListener

                        // Enviar a TérminosActivity con datos
                        val intent = Intent(this, TerminosActivity::class.java)
                        intent.putExtra("uid", uid)
                        intent.putExtra("nombre", nombre)
                        intent.putExtra("correo", correo)
                        intent.putExtra("domicilio", domicilio)
                        intent.putExtra("codigo", codigo)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
