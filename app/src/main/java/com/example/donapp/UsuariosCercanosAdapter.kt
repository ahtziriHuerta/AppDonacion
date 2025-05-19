package com.example.donapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuariosCercanosAdapter(
    private val usuarios: List<Pair<String, Double>> // correo, distancia
) : RecyclerView.Adapter<UsuariosCercanosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtCorreo: TextView = view.findViewById(R.id.txtCorreo)
        val txtDistancia: TextView = view.findViewById(R.id.txtDistancia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario_cercano, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = usuarios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (correo, distancia) = usuarios[position]
        holder.txtCorreo.text = correo
        holder.txtDistancia.text = String.format("Distancia: %.2f km", distancia)
    }
}

