package com.example.donapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val mensajes: List<ChatMessage>,
    private val miCorreo: String
) : RecyclerView.Adapter<ChatAdapter.MensajeViewHolder>() {

    inner class MensajeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textoMensaje: TextView = view.findViewById(R.id.textoMensaje)
        val textoEmisor: TextView? = view.findViewById(R.id.textoEmisor) // solo en mensaje recibido
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            if (viewType == 0) R.layout.item_mensaje_enviado else R.layout.item_mensaje_recibido,
            parent,
            false
        )
        return MensajeViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val mensaje = mensajes[position]

        holder.textoMensaje.text = mensaje.mensaje

        // Solo para mensajes recibidos, muestra el emisor
        if (holder.textoEmisor != null) {
            holder.textoEmisor.text = mensaje.emisor
        }
    }

    override fun getItemCount(): Int = mensajes.size

    override fun getItemViewType(position: Int): Int {
        return if (mensajes[position].emisor == miCorreo) 0 else 1
    }
}


