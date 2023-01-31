package Modelo

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.example.forest_firefighters.Weather.Dato
import java.io.Serializable

class HumedadRelativa : Serializable {
    @SerializedName("maxima")
    @Expose
    var maxima: Int = 0

    @SerializedName("minima")
    @Expose
    var minima: Int = 0

    @SerializedName("dato")
    @Expose
    lateinit var dato: List<Dato>
    override fun toString(): String {
        return "HumedadRelativa{" +
                "maxima=" + maxima +
                ", minima=" + minima +
                ", dato=" + dato +
                '}'
    }
}