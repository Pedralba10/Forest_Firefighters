package com.example.forest_firefighters.Weather

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class Dato : Serializable {
    @SerializedName("value")
    @Expose
    var value: Int = 0

    @SerializedName("hora")
    @Expose
    var hora: Int = 0
    override fun toString(): String {
        return "Dato{" +
                "value=" + value +
                ", hora=" + hora +
                '}'
    }
}