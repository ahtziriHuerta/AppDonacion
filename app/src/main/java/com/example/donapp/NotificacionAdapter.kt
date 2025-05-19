package com.example.donapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class NotificacionAdapter(
    private val notificaciones: List<Pair<String, Notificacion>>, // (docId, notificacion)
    private val onEliminarClick: (String) -> Unit,
    private val onAceptarClick: (Notificacion) -> Unit
) : RecyclerView.Adapter<NotificacionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtInteresado: TextView = view.findViewById(R.id.txtInteresado)
        val txtProducto: TextView = view.findViewById(R.id.txtProducto)
        val txtFecha: TextView = view.findViewById(R.id.txtFecha)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminarNotificacion)
        val btnAceptar: Button = view.findViewById(R.id.btnAceptarNotificacion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notificacion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (docId, noti) = notificaciones[position]
        holder.txtInteresado.text = "Interesado: ${noti.interesadoEmail}"
        holder.txtProducto.text = "Producto: ${noti.producto}"

        val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        holder.txtFecha.text = "Fecha: ${dateFormat.format(noti.fecha?.toDate() ?: Date())}"

        holder.btnEliminar.setOnClickListener {
            onEliminarClick(docId)
        }

        holder.btnAceptar.setOnClickListener {
            onAceptarClick(noti)
        }
    }

    override fun getItemCount(): Int = notificaciones.size
}
