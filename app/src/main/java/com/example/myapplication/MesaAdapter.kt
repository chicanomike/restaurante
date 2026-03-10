package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemMesaBinding

class MesaAdapter(
    private val mesas: List<Mesa>,
    private val onMesaClick: (Mesa) -> Unit
) : RecyclerView.Adapter<MesaAdapter.MesaViewHolder>() {

    class MesaViewHolder(val binding: ItemMesaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MesaViewHolder {
        val binding = ItemMesaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MesaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MesaViewHolder, position: Int) {
        val mesa = mesas[position]
        holder.binding.tvNombreMesa.text = mesa.nombre
        holder.binding.tvEstadoMesa.text = "${mesa.items.size} Artículos"
        holder.itemView.setOnClickListener { onMesaClick(mesa) }
    }

    override fun getItemCount(): Int = mesas.size
}