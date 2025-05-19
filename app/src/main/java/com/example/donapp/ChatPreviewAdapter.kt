package com.example.donapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatPreviewAdapter(
    private val chats: List<ChatPreview>,
    private val onChatClick: (ChatPreview) -> Unit,
    private val onEliminarClick: (ChatPreview) -> Unit
) : RecyclerView.Adapter<ChatPreviewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtCorreo: TextView = view.findViewById(R.id.txtCorreoDestino)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminarChat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_preview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chats[position]
        holder.txtCorreo.text = chat.correoDestino

        holder.itemView.setOnClickListener {
            onChatClick(chat)
        }

        holder.btnEliminar.setOnClickListener {
            onEliminarClick(chat)
        }
    }

    override fun getItemCount(): Int = chats.size
}

