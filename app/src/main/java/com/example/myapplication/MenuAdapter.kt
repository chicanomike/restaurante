package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemMenuBinding

class MenuAdapter(
    private val platos: List<Plato>,
    private val onPlatoClick: (Plato) -> Unit,
    private val onAgregarClick: (Plato) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val plato = platos[position]
        holder.binding.apply {
            tvNombrePlato.text = plato.nombre
            tvPrecioPlato.text = "$${plato.precio}"
            
            Glide.with(root.context)
                .load(plato.imageUrl)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(ivPlato)

            // Si toca la foto o el nombre, abre el detalle
            ivPlato.setOnClickListener { onPlatoClick(plato) }
            tvNombrePlato.setOnClickListener { onPlatoClick(plato) }
            
            // Si toca el botón "+", se agrega directo a la cuenta
            btnAgregar.setOnClickListener { onAgregarClick(plato) }
        }
    }

    override fun getItemCount(): Int = platos.size
}