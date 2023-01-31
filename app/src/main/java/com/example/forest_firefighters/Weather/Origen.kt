package Modelo

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class Origen : Serializable {
    @SerializedName("productor")
    @Expose
    lateinit var productor: String

    @SerializedName("web")
    @Expose
    lateinit var web: String

    @SerializedName("enlace")
    @Expose
    lateinit var enlace: String

    @SerializedName("language")
    @Expose
    lateinit var language: String

    @SerializedName("copyright")
    @Expose
    lateinit var copyright: String

    @SerializedName("notaLegal")
    @Expose
    lateinit var notaLegal: String
    override fun toString(): String {
        return "Origen{" +
                "productor='" + productor + '\'' +
                ", web='" + web + '\'' +
                ", enlace='" + enlace + '\'' +
                ", language='" + language + '\'' +
                ", copyright='" + copyright + '\'' +
                ", notaLegal='" + notaLegal + '\'' +
                '}'
    }
}