package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemMenuBinding

class MenuAdapter(
    private val platos: List<Plato>,
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
            tvDescripcionPlato.text = plato.descripcion
            tvPrecioPlato.text = "$${plato.precio}"
            btnAgregar.setOnClickListener { onAgregarClick(plato) }
        }
    }

    override fun getItemCount(): Int = platos.size
}