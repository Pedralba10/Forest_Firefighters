package Modelo

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class PrediccionMunicipio : Serializable {
    @SerializedName("origen")
    @Expose
    private var origen: Origen = Origen()

    @SerializedName("elaborado")
    @Expose
    var elaborado: String = ""

    @SerializedName("nombre")
    @Expose
    var nombre: String = ""

    @SerializedName("provincia")
    @Expose
    var provincia: String = ""

    @SerializedName("prediccion")
    @Expose
    private var prediccion: Prediccion = Prediccion()

    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("version")
    @Expose
    var version: Double = 0.0
    fun getOrigen(): Origen {
        return origen
    }

    fun setOrigen(origen: Origen) {
        this.origen = origen
    }

    fun getPrediccion(): Prediccion {
        return prediccion
    }

    fun setPrediccion(prediccion: Prediccion) {
        this.prediccion = prediccion
    }

    override fun toString(): String {
        return "PrediccionMunicipio{" +
                "origen=" + origen +
                ", elaborado='" + elaborado + '\'' +
                ", nombre='" + nombre + '\'' +
                ", provincia='" + provincia + '\'' +
                ", prediccion=" + prediccion +
                ", id=" + id +
                ", version=" + version +
                '}'
    }
}