package Modelo

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class ProbPrecipitacion : Serializable {
    @SerializedName("value")
    @Expose
    var value: Int = 0

    @SerializedName("periodo")
    @Expose
    lateinit var periodo: String
    override fun toString(): String {
        return "ProbPrecipitacion{" +
                "value=" + value +
                ", periodo='" + periodo + '\'' +
                '}'
    }
}