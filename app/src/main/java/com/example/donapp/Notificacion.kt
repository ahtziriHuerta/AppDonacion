package com.example.donapp

import com.google.firebase.Timestamp

data class Notificacion(
    var producto: String = "",
    var interesadoEmail: String = "",
    var fecha: Timestamp? = null
)
