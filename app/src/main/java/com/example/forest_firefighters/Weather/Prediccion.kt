package Modelo

import com.example.forest_firefighters.Weather.Dium
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class Prediccion : Serializable {
    @SerializedName("dia")
    @Expose
    private var dia: List<Dium> = listOf()
    fun getDia(): List<Dium> {
        return dia
    }

    fun setDia(dia: List<Dium>) {
        this.dia = dia
    }

    override fun toString(): String {
        return "Prediccion{" +
                "dia=" + dia +
                '}'
    }
}