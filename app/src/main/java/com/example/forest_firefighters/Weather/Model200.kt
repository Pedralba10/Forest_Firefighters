package com.example.forest_firefighters.Weather

import java.io.Serializable

//Clase para almacenar el resultado de la petición principal
class Model200 : Serializable {
    lateinit var descripcion: String
    var estado = 0
    lateinit var datos: String
    lateinit var metadatos: String

    override fun toString(): String {
        return "Model200{" +
                "descripcion='" + descripcion + '\'' +
                ", estado=" + estado +
                ", datos='" + datos + '\'' +
                ", metadatos='" + metadatos + '\'' +
                '}'
    }
}