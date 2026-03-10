package com.example.myapplication

import java.io.Serializable

data class Plato(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imageUrl: String
) : Serializable