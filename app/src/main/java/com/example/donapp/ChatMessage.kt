package com.example.donapp

import com.google.firebase.Timestamp

data class ChatMessage(
    val mensaje: String = "",
    val emisor: String = "",
    val receptor: String = "",
    val fecha: Timestamp = Timestamp.now()
)
