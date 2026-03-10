package com.example.myapplication

import java.io.Serializable

data class Mesa(
    val id: Int,
    var nombre: String,
    val items: MutableList<Plato> = mutableListOf()
) : Serializable