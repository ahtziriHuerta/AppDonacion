package com.example.donapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser
        val email = user?.email

        // Validación de inicio de sesión
        if (user == null || email.isNullOrBlank()) {
            redirectToLogin()
            return
        }

        // Validación de dominio CUCEI
        if (!email.endsWith("@alumnos.udg.mx") && !email.endsWith("@cucei.udg.mx")) {
            auth.signOut()
            Toast.makeText(this, "Solo usuarios de CUCEI pueden ingresar", Toast.LENGTH_LONG).show()
            redirectToLogin()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val btnAgregarProducto = findViewById<Button>(R.id.btnAgregarProducto)
        btnAgregarProducto.setOnClickListener {
            startActivity(Intent(this, AgregarProductoActivity::class.java))
        }
        val btnCercanos = findViewById<Button>(R.id.btnUsuariosCercanos)
        btnCercanos.setOnClickListener {
            startActivity(Intent(this, UsuariosCercanosActivity::class.java))
        }


        findViewById<Button>(R.id.btnMisProductos).setOnClickListener {
            startActivity(Intent(this, ListaProductosActivity::class.java))
        }

        findViewById<Button>(R.id.btnVerTodosProductos).setOnClickListener {
            startActivity(Intent(this, VerProductosActivity::class.java))
        }

        findViewById<Button>(R.id.btnVerNotificaciones).setOnClickListener {
            startActivity(Intent(this, NotificacionesActivity::class.java))
        }

        findViewById<Button>(R.id.btnVerChats).setOnClickListener {
            startActivity(Intent(this, MisChatsActivity::class.java))
        }

        findViewById<Button>(R.id.btnCerrarSesion).setOnClickListener {
            auth.signOut()
            redirectToLogin()
        }
    }

    private fun redirectToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

