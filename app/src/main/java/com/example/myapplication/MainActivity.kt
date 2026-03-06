package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val cuentaActual = mutableListOf<Plato>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Simulación de menú de un restaurante
        val listaMenu = listOf(
            Plato(1, "Ensalada César", "Lechuga, crotones, pollo a la parrilla", 15.0),
            Plato(2, "Pizza Margarita", "Tomate, mozzarella fresca, albahaca", 18.0),
            Plato(3, "Hamburguesa Deluxe", "Res, queso, tocino, papas fritas", 22.0),
            Plato(4, "Pasta Carbonara", "Crema, tocino, queso parmesano", 20.0),
            Plato(5, "Limonada Natural", "Fresca limonada recién hecha", 5.0)
        )

        val adapter = MenuAdapter(listaMenu) { plato ->
            cuentaActual.add(plato)
            Toast.makeText(this, "${plato.nombre} agregado", Toast.LENGTH_SHORT).show()
        }

        binding.rvMenu.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = adapter
        }

        binding.btnVerCuenta.setOnClickListener {
            if (cuentaActual.isEmpty()) {
                Toast.makeText(this, "No has agregado nada aún", Toast.LENGTH_SHORT).show()
            } else {
                val total = cuentaActual.sumOf { it.precio }
                val ticket = cuentaActual.joinToString("\n") { "${it.nombre}: $${it.precio}" }
                Toast.makeText(this, "Total: $${total}\n${ticket}", Toast.LENGTH_LONG).show()
            }
        }
    }
}