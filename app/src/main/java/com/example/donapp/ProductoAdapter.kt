package com.example.donapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class ProductoAdapter(
    private val context: Context,
    private val productos: List<Producto>,
    private val onEliminarClick: (Producto) -> Unit,
    private val onProductoEliminado: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagen: ImageView = view.findViewById(R.id.imagenProducto)
        val nombre: TextView = view.findViewById(R.id.nombreProducto)
        val descripcion: TextView = view.findViewById(R.id.descripcionProducto)
        val donador: TextView = view.findViewById(R.id.textDonador)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminarProducto)
        val btnInteresar: Button = view.findViewById(R.id.btnInteresar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.nombre.text = producto.nombre
        holder.descripcion.text = producto.descripcion
        holder.donador.text = "Publicado por: ${producto.usuarioCorreo ?: "Desconocido"}"

        Glide.with(context)
            .load(producto.imagenUrl)
            .into(holder.imagen)

        val uidActual = FirebaseAuth.getInstance().currentUser?.uid
        val esDueno = producto.usuarioId == uidActual

        // Solo mostrar botón Eliminar si el usuario actual es el dueño
        holder.btnEliminar.visibility = if (esDueno) View.VISIBLE else View.GONE

        holder.btnEliminar.setOnClickListener {
            onEliminarClick(producto)
        }

        holder.btnInteresar.setOnClickListener {
            // Aquí podrías agregar lógica para iniciar chat o marcar como interesado
        }
    }

    override fun getItemCount(): Int = productos.size
}
