package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityPantallaMesasBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PantallaMesasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPantallaMesasBinding
    private val listaMesas = mutableListOf<Mesa>()
    private lateinit var adapter: MesaAdapter

    private val tomarPedidoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val mesaActualizada = result.data?.getSerializableExtra("mesa") as? Mesa
            mesaActualizada?.let { mesaUpd ->
                val index = listaMesas.indexOfFirst { it.id == mesaUpd.id }
                if (index != -1) {
                    listaMesas[index] = mesaUpd
                    adapter.notifyItemChanged(index)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaMesasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.btnNuevaMesa.setOnClickListener {
            mostrarDialogoNuevaMesa()
        }
    }

    private fun setupRecyclerView() {
        adapter = MesaAdapter(listaMesas) { mesa ->
            irATomarPedido(mesa)
        }
        binding.rvMesas.layoutManager = LinearLayoutManager(this)
        binding.rvMesas.adapter = adapter
    }

    private fun mostrarDialogoNuevaMesa() {
        // Usamos MaterialAlertDialogBuilder que es más moderno y no requiere el style manual que fallaba
        val builder = MaterialAlertDialogBuilder(this)
        builder.setTitle("Nueva Mesa / Comanda")
        
        val input = EditText(this)
        input.hint = "Nombre de la mesa o cliente"
        input.setPadding(50, 40, 50, 40)
        builder.setView(input)

        builder.setPositiveButton("Crear") { _, _ ->
            val nombre = input.text.toString()
            if (nombre.isNotEmpty()) {
                val nuevaMesa = Mesa(id = listaMesas.size + 1, nombre = nombre)
                listaMesas.add(nuevaMesa)
                adapter.notifyItemInserted(listaMesas.size - 1)
                irATomarPedido(nuevaMesa)
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun irATomarPedido(mesa: Mesa) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("mesa", mesa)
        tomarPedidoLauncher.launch(intent)
    }
}