package com.example.forest_firefighters

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import Modelo.PrediccionMunicipio
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    //Atributos del TextView para mostrar la información
    private lateinit var tvFecha: TextView
    private lateinit var tvlocalidad1: TextView
    private lateinit var tvTempMax1: TextView
    private lateinit var tvTempMin1: TextView
    private lateinit var tvTempMed1: TextView
    private lateinit var tvVRacha1: TextView
    private lateinit var tvVMed1: TextView
    private lateinit var tvVPrec1: TextView
    private lateinit var tvEstadoCielo1: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        //Asocio los textView con el layout
        tvFecha = findViewById(R.id.tvFecha)
        tvlocalidad1 = findViewById(R.id.tvlocalidad)
        tvTempMax1 = findViewById(R.id.tvTempMax1)
        tvTempMin1 = findViewById(R.id.tvTempMin1)
        tvTempMed1 = findViewById(R.id.tvTempMed1)
        tvVRacha1 = findViewById(R.id.tvVRacha1)
        tvVMed1 = findViewById(R.id.tvVMed1)
        tvVPrec1 = findViewById(R.id.tvVPrec1)
        tvEstadoCielo1 = findViewById(R.id.tvEstadoCielo1)

        //Paso del objeto prediccion
        val prediccion1 =
            intent.extras!!.getSerializable("prediccionMunicipio1") as PrediccionMunicipio?

        //Obtencion de datos relevantes prediccion
        val tmax1 = prediccion1!!.getPrediccion().getDia()[1].getTemperatura().maxima
        val tmin1 = prediccion1.getPrediccion().getDia()[1].getTemperatura().minima
        val tmed1 = ((tmax1 + tmin1) / 2).toFloat()
        val probPrec1 = getProbPrecMax(prediccion1)
        val vientoMedia1 = prediccion1.getPrediccion().getDia()[0].getViento()[0].velocidad
        val vientoMax1 = getVientoMax(prediccion1)
        val vientoMin1 = getVientoMin(prediccion1)
        val estadoCielo1 = prediccion1.getPrediccion().getDia()[1].getEstadoCielo()[0].descripcion
        val municipio1 = prediccion1.nombre
        val provincia1 = prediccion1.provincia


        val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var fecha1: Date? = Date()
        try {
            fecha1 = dateFormat1.parse(prediccion1.getPrediccion().getDia()[1].fecha)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val fechaComparacion = Calendar.getInstance().time
        println(fechaComparacion.toString())
        val dateFormat2 = SimpleDateFormat("dd/MM/yyyy")
        tvFecha.setText("Predicciones para el día " + dateFormat2.format(fecha1))
        tvlocalidad1.setText(prediccion1.nombre)
        tvTempMax1.setText("$tmax1°C")
        tvTempMin1.setText("$tmin1°C")
        tvTempMed1.setText("$tmed1°C")
        tvVRacha1.setText("" + vientoMax1 + "km/h")
        tvVMed1.setText("" + vientoMedia1 + "km/h")
        tvVPrec1.setText("$probPrec1%")
        tvEstadoCielo1.setText(estadoCielo1)
    }

    private fun getVientoMax(prediccion: PrediccionMunicipio?): Int {
        val vientoList = prediccion!!.getPrediccion().getDia()[0].getViento()
        var vientoMax = vientoList[0].velocidad
        for (viento in vientoList) {
            if (viento.velocidad > vientoMax) {
                vientoMax = viento.velocidad
            }
        }
        return vientoMax
    }

    private fun getVientoMin(prediccion: PrediccionMunicipio?): Int {
        val vientoList = prediccion!!.getPrediccion().getDia()[0].getViento()
        var vientoMin = vientoList[0].velocidad
        for (viento in vientoList) {
            if (viento.velocidad < vientoMin) {
                vientoMin = viento.velocidad
            }
        }
        return vientoMin
    }

    private fun getProbPrecMax(prediccion: PrediccionMunicipio?): Int {
        val probPrecipitacionList = prediccion!!.getPrediccion().getDia()[0].getProbPrecipitacion()
        var probPrecipitacion = probPrecipitacionList[0].value
        for (precipitacion in probPrecipitacionList) {
            if (precipitacion.value > probPrecipitacion) {
                probPrecipitacion = precipitacion.value
            }
        }
        return probPrecipitacion
    }
}