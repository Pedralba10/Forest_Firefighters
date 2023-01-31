package Modelo

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class Viento : Serializable {
    @SerializedName("direccion")
    @Expose
    var direccion: String = ""

    @SerializedName("velocidad")
    @Expose
    var velocidad: Int = 0

    @SerializedName("periodo")
    @Expose
    lateinit var periodo: String
    override fun toString(): String {
        return "Viento{" +
                "direccion='" + direccion + '\'' +
                ", velocidad=" + velocidad +
                ", periodo='" + periodo + '\'' +
                '}'
    }
}