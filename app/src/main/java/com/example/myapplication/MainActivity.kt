package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val cuentaActual = mutableListOf<Plato>()
    private lateinit var listaMenu: List<Plato>

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val id = result.data?.getIntExtra("id", -1) ?: -1
            listaMenu.find { it.id == id }?.let {
                cuentaActual.add(it)
                actualizarResumen()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMenu()
    }

    private fun setupMenu() {
        listaMenu = listOf(
            Plato(1, "Clásica", "Corn dog tradicional con masa crujiente", 5.50, 
                "https://images.unsplash.com/photo-1623653387945-2fd25214f8fc?q=80&w=500&auto=format&fit=crop"),
            Plato(2, "Papa", "Cubierto con cubos de papa frita", 6.50, 
                "https://images.unsplash.com/photo-1594212699903-ec8a3eca50f5?q=80&w=500&auto=format&fit=crop"),
            Plato(3, "Ramen Dog", "Cubierto con fideos ramen crujientes", 7.00, 
                "https://images.unsplash.com/photo-1612929633738-8fe44f7ec841?q=80&w=500&auto=format&fit=crop"),
            Plato(4, "Mozza Dog", "Relleno de puro queso mozzarella", 6.00, 
                "https://images.unsplash.com/photo-1528605248644-14dd04cb11c7?q=80&w=500&auto=format&fit=crop"),
            Plato(7, "Coca Cola", "Refresco de 600ml bien frío", 2.50, 
                "https://images.unsplash.com/photo-1622483767028-3f66f32aef97?q=80&w=500&auto=format&fit=crop"),
            Plato(8, "Té Helado", "Té natural con limón y hielo", 3.00, 
                "https://images.unsplash.com/photo-1556679343-c7306c1976bc?q=80&w=500&auto=format&fit=crop")
        )

        val adapter = MenuAdapter(
            listaMenu,
            onPlatoClick = { plato -> 
                val intent = Intent(this, DetallePlatoActivity::class.java).apply {
                    putExtra("id", plato.id)
                    putExtra("nombre", plato.nombre)
                    putExtra("descripcion", plato.descripcion)
                    putExtra("precio", plato.precio)
                    putExtra("imageUrl", plato.imageUrl)
                }
                getResult.launch(intent)
            },
            onAgregarClick = { plato -> 
                cuentaActual.add(plato)
                actualizarResumen()
            }
        )

        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            this.adapter = adapter
        }
    }

    private fun actualizarResumen() {
        val total = cuentaActual.sumOf { it.precio }
        binding.tvItemCount.text = "${cuentaActual.size} ITEMS"
        binding.tvTotalValue.text = "$${String.format("%.2f", total)}"

        binding.orderList.removeAllViews()
        
        val itemsAgrupados = cuentaActual.groupBy { it.nombre }
        
        for ((nombre, items) in itemsAgrupados) {
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(0, 4, 0, 4) }
                
                // Opción para eliminar: Si dejas presionado el ítem, se borra de la lista
                setOnLongClickListener {
                    val platoABorrar = cuentaActual.find { it.nombre == nombre }
                    if (platoABorrar != null) {
                        cuentaActual.remove(platoABorrar)
                        actualizarResumen()
                    }
                    true
                }
            }

            val tvQty = TextView(this).apply {
                text = "${items.size}x "
                setTextColor(resources.getColor(R.color.primary_orange))
                setTypeface(null, android.graphics.Typeface.BOLD)
                textSize = 14f
            }

            val tvName = TextView(this).apply {
                text = nombre
                setTextColor(resources.getColor(android.R.color.white))
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                textSize = 14f
            }

            val tvPrice = TextView(this).apply {
                text = "$${String.format("%.2f", items.sumOf { it.precio })}"
                setTextColor(resources.getColor(android.R.color.white))
                textSize = 14f
            }

            row.addView(tvQty)
            row.addView(tvName)
            row.addView(tvPrice)
            
            binding.orderList.addView(row)
        }
    }
}