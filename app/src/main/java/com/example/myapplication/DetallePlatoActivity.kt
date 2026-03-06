package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityDetallePlatoBinding

class DetallePlatoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetallePlatoBinding
    private var basePrecio = 0.0
    private var imageUrlOriginal = ""
    private val imageUrlExtraCheese = "https://images.unsplash.com/photo-1552767059-ce182ead6c1b?q=80&w=500&auto=format&fit=crop"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePlatoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("nombre") ?: ""
        val desc = intent.getStringExtra("descripcion") ?: ""
        basePrecio = intent.getDoubleExtra("precio", 0.0)
        imageUrlOriginal = intent.getStringExtra("imageUrl") ?: ""

        binding.tvDetalleNombre.text = nombre
        binding.tvDetalleDesc.text = desc
        actualizarPrecioBoton()

        cargarImagen(imageUrlOriginal)

        binding.btnBack.setOnClickListener { finish() }

        // Animación suave al cambiar imagen
        binding.cbCheese.setOnCheckedChangeListener { _, isChecked ->
            actualizarPrecioBoton()
            val nuevaUrl = if (isChecked) imageUrlExtraCheese else imageUrlOriginal
            
            val fadeOut = AlphaAnimation(1.0f, 0.2f).apply { duration = 300 }
            val fadeIn = AlphaAnimation(0.2f, 1.0f).apply { duration = 300 }
            
            binding.ivDetallePlato.startAnimation(fadeOut)
            fadeOut.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                    cargarImagen(nuevaUrl)
                    binding.ivDetallePlato.startAnimation(fadeIn)
                }
                override fun onAnimationStart(animation: android.view.animation.Animation?) {}
                override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
            })
        }

        binding.cbPotato.setOnCheckedChangeListener { _, _ -> actualizarPrecioBoton() }

        binding.btnAddToOrder.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("id", intent.getIntExtra("id", -1))
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun cargarImagen(url: String) {
        Glide.with(this).load(url).centerCrop().into(binding.ivDetallePlato)
    }

    private fun actualizarPrecioBoton() {
        var extra = 0.0
        if (binding.cbCheese.isChecked) extra += 1.50
        if (binding.cbPotato.isChecked) extra += 1.00
        val total = basePrecio + extra
        binding.btnAddToOrder.text = "Add to Order - $${String.format("%.2f", total)}"
    }
}