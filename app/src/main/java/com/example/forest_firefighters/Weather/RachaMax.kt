package Modelo

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class RachaMax : Serializable {
    @SerializedName("value")
    @Expose
    lateinit var value: String

    @SerializedName("periodo")
    @Expose
    lateinit var periodo: String
    override fun toString(): String {
        return "RachaMax{" +
                "value='" + value + '\'' +
                ", periodo='" + periodo + '\'' +
                '}'
    }
}