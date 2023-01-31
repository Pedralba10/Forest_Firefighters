package Modelo

import java.io.Serializable

class PrediccionMunicipio_old : Serializable {
    //private Temperatura temperatura;
    lateinit var nombre: String
    lateinit var provincia: String
    override fun toString(): String {
        return "PrediccionMunicipio{" +
                "nombre='" + nombre + '\'' +
                ", provincia='" + provincia + '\'' +
                '}'
    }
}