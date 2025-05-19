

package com.example.donapp

data class Producto(
    val nombre: String? = null,
    val descripcion: String? = null,
    val imagenUrl: String? = null,
    val usuarioCorreo: String? = null,
    val usuarioId: String? = null,
    var id: String? = null // ← ESTE CAMPO
)



