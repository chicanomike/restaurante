package com.example.myapplication

data class Plato(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imageUrl: String // Nueva propiedad para la foto
)