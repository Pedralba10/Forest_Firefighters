package Modelo

import java.io.Serializable

class Temperatura_old : Serializable {

    //Atributos
    var maxima = 0
    var minima = 0

    //ToString
    override fun toString(): String {
        return "PrediccionMunicipio{" +
                "maxima=" + maxima +
                ", minima=" + minima +
                '}'
    }
}