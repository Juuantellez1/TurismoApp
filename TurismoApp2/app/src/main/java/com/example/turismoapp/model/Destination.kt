package com.example.turismoapp.model

data class Destination(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val pais: String,
    val descripcion: String,
    val actividadRecomendada: String,
    val lat: Double?,
    val lon: Double?
)

