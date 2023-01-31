package com.example.forest_firefighters.Weather

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class CotaNieveProv : Serializable {
    @SerializedName("value")
    @Expose
    lateinit var value: String

    @SerializedName("periodo")
    @Expose
    lateinit var periodo: String
    override fun toString(): String {
        return "CotaNieveProv{" +
                "value='" + value + '\'' +
                ", periodo='" + periodo + '\'' +
                '}'
    }
}