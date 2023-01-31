package com.example.forest_firefighters.Weather

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class EstadoCielo : Serializable {
    @SerializedName("value")
    @Expose
    lateinit var value: String

    @SerializedName("periodo")
    @Expose
    lateinit var periodo: String

    @SerializedName("descripcion")
    @Expose
    lateinit var descripcion: String
    override fun toString(): String {
        return "EstadoCielo{" +
                "value='" + value + '\'' +
                ", periodo='" + periodo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}'
    }
}