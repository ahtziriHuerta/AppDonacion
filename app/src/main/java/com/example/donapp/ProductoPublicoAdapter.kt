package com.example.donapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductoPublicoAdapter(
    private val productos: List<Producto>,
    private val onInteresClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoPublicoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProducto: ImageView = view.findViewById(R.id.imgProducto)
        val txtNombre: TextView = view.findViewById(R.id.txtNombre)
        val txtDescripcion: TextView = view.findViewById(R.id.txtDescripcion)
        val txtDonador: TextView = view.findViewById(R.id.textDonador)
        val btnMeInteresa: Button = view.findViewById(R.id.btnMeInteresa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_publico, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.txtNombre.text = producto.nombre
        holder.txtDescripcion.text = producto.descripcion
        holder.txtDonador.text = holder.itemView.context.getString(
            R.string.publicado_por,
            producto.usuarioCorreo ?: "Desconocido"
        )

        Glide.with(holder.itemView.context)
            .load(producto.imagenUrl)
            .into(holder.imgProducto)

        holder.btnMeInteresa.setOnClickListener {
            onInteresClick(producto)
        }
    }

    override fun getItemCount(): Int = productos.size
}
