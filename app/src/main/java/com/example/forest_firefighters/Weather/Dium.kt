package com.example.forest_firefighters.Weather

import Modelo.*
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class Dium : Serializable {
    @SerializedName("probPrecipitacion")
    @Expose
    private var probPrecipitacion: List<ProbPrecipitacion> = listOf()

    @SerializedName("cotaNieveProv")
    @Expose
    private var cotaNieveProv: List<CotaNieveProv> = listOf()

    @SerializedName("estadoCielo")
    @Expose
    private var estadoCielo: List<EstadoCielo> = listOf()

    @SerializedName("viento")
    @Expose
    private var viento: List<Viento> = listOf()

    @SerializedName("rachaMax")
    @Expose
    private var rachaMax: List<RachaMax> = listOf()

    @SerializedName("temperatura")
    @Expose
    private var temperatura: Temperatura_old = Temperatura_old()

    @SerializedName("sensTermica")
    @Expose
    private var sensTermica: SensTermica = SensTermica()

    @SerializedName("humedadRelativa")
    @Expose
    private var humedadRelativa: HumedadRelativa = HumedadRelativa()

    @SerializedName("uvMax")
    @Expose
    var uvMax: Int = 0

    @SerializedName("fecha")
    @Expose
    var fecha: String = ""
    fun getProbPrecipitacion(): List<ProbPrecipitacion> {
        return probPrecipitacion
    }

    fun setProbPrecipitacion(probPrecipitacion: List<ProbPrecipitacion>) {
        this.probPrecipitacion = probPrecipitacion
    }

    fun getCotaNieveProv(): List<CotaNieveProv> {
        return cotaNieveProv
    }

    fun setCotaNieveProv(cotaNieveProv: List<CotaNieveProv>) {
        this.cotaNieveProv = cotaNieveProv
    }

    fun getEstadoCielo(): List<EstadoCielo> {
        return estadoCielo
    }

    fun setEstadoCielo(estadoCielo: List<EstadoCielo>) {
        this.estadoCielo = estadoCielo
    }

    fun getViento(): List<Viento> {
        return viento
    }

    fun setViento(viento: List<Viento>) {
        this.viento = viento
    }

    fun getRachaMax(): List<RachaMax> {
        return rachaMax
    }

    fun setRachaMax(rachaMax: List<RachaMax>) {
        this.rachaMax = rachaMax
    }

    fun getTemperatura(): Temperatura_old {
        return temperatura
    }

    fun setTemperatura(temperatura: Temperatura_old) {
        this.temperatura = temperatura
    }

    fun getSensTermica(): SensTermica {
        return sensTermica
    }

    fun setSensTermica(sensTermica: SensTermica) {
        this.sensTermica = sensTermica
    }

    fun getHumedadRelativa(): HumedadRelativa {
        return humedadRelativa
    }

    fun setHumedadRelativa(humedadRelativa: HumedadRelativa) {
        this.humedadRelativa = humedadRelativa
    }

    override fun toString(): String {
        return "Dium{" +
                "probPrecipitacion=" + probPrecipitacion +
                ", cotaNieveProv=" + cotaNieveProv +
                ", estadoCielo=" + estadoCielo +
                ", viento=" + viento +
                ", rachaMax=" + rachaMax +
                ", temperatura=" + temperatura +
                ", sensTermica=" + sensTermica +
                ", humedadRelativa=" + humedadRelativa +
                ", uvMax=" + uvMax +
                ", fecha='" + fecha + '\'' +
                '}'
    }
}