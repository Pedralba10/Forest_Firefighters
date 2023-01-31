package com.example.forest_firefighters


import androidx.appcompat.app.AppCompatActivity
import Modelo.PrediccionMunicipio
import android.annotation.SuppressLint
import android.os.Bundle
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.forest_firefighters.Weather.Model200
import android.content.Intent
import android.view.View
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class LocalityWeatherActivity : AppCompatActivity() {
    //Atributos
    lateinit var spinnerProvincia: Spinner
    lateinit var spinnerLocalidad: Spinner
    lateinit var buttonCompara: Button
    var baseUrl = "https://opendata.aemet.es/"
    lateinit var prediccion1: PrediccionMunicipio

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locality_weather)

        //Une los atributos con sus elementos del activity
        spinnerProvincia = findViewById<View>(R.id.spinnerProvincia) as Spinner
        spinnerLocalidad = findViewById<View>(R.id.spinnerLocalidad) as Spinner
        val opciones = arrayOf("Selecciona un municipio")

        //Leer el archivo .csv con el nombre de  las provincias para pasarselo a los spinners de selección de provincia
        val inputStream = resources.openRawResource(R.raw.nombreprovicias)
        val csvFile = CSVFile(inputStream)
        val csvList = csvFile.read()

        //Accede a la primera fila del archivo .csv
        val provincias = csvList[0]

        //crea la lista
        val provinciasList: MutableList<String> = ArrayList()

        //Se agrega como primero el texto informativo
        provinciasList.add("Selecciona una provincia")

        //Se convierte el array de strings a una lista
        for (i in provincias.indices) {
            provinciasList.add(provincias[i])
        }

        //Se añade el contenido correspondiente a cada spinner
        val provincia1 = ArrayAdapter(this, R.layout.spinner_item_disenio, provinciasList)
        spinnerProvincia!!.adapter = provincia1
        val localidad1 = ArrayAdapter(this, R.layout.spinner_item_disenio, opciones)
        spinnerLocalidad!!.adapter = localidad1
        spinnerLocalidad!!.isEnabled = false
        spinnerLocalidad!!.isClickable = false

        //Define el comportamiento al seleccionar opción en spinner de provincias
        spinnerProvincia!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

                if (pos != 0) {
                    //Crea un string con el nombre de los .csv de los municipios a cargar
                    var municipiosDeProvincia = "municipios_" + parent.getItemAtPosition(pos)
                    //Se necesita convertir el nombre a minúsculas y eliminar ñ y ´
                    municipiosDeProvincia = municipiosDeProvincia.toLowerCase()
                    municipiosDeProvincia = municipiosDeProvincia.replace("ñ", "n")
                    municipiosDeProvincia = municipiosDeProvincia.replace('á', 'a')
                    municipiosDeProvincia = municipiosDeProvincia.replace('é', 'e')
                    municipiosDeProvincia = municipiosDeProvincia.replace('í', 'i')
                    municipiosDeProvincia = municipiosDeProvincia.replace('ó', 'o')
                    municipiosDeProvincia = municipiosDeProvincia.replace('ú', 'u')
                    municipiosDeProvincia = municipiosDeProvincia.replace(" ", "_")

                    //Se abre el .csv de municipios utilizando el nombre, no el id
                    val isMunicipios1 = resources.openRawResource(resources.getIdentifier(municipiosDeProvincia, "raw", packageName))

                    //Leer el archivo .csv con nombre de los municipios de la provincia seleccionada en los spinners de municipio
                    val csvFileMunicipios1 = CSVFile(isMunicipios1)
                    val csvListMunicipios1 = csvFileMunicipios1.read()

                    //Accede a la primera fila del archivo .csv
                    val municipios = csvListMunicipios1[0]

                    //Se crea la lista
                    val municipiosList: MutableList<String> = ArrayList()

                    //Se agrega como primer elemento el texto informativo
                    municipiosList.add("Selecciona un municipio")

                    //Se convierte el array de strings a una lista
                    for (i in municipios.indices) {
                        municipiosList.add(municipios[i])
                    }

                    val localidad1 = ArrayAdapter(this@LocalityWeatherActivity, R.layout.spinner_item_disenio, municipiosList)
                    spinnerLocalidad!!.adapter = localidad1
                    spinnerLocalidad!!.isEnabled = true
                    spinnerLocalidad!!.isClickable = true

                } else {
                    val defaultText: MutableList<String> = ArrayList()
                    defaultText.add("Selecciona un municipio")
                    val localidad1 = ArrayAdapter(this@LocalityWeatherActivity, R.layout.spinner_item_disenio, defaultText)
                    spinnerLocalidad!!.adapter = localidad1
                    spinnerLocalidad!!.isEnabled = false
                    spinnerLocalidad!!.isClickable = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        buttonCompara =
            findViewById(R.id.buttonClima) //Se le une el objeto botón de clima con el botón del layout
        buttonCompara.setOnClickListener(
            View.OnClickListener

            {
                var provincia1 = ""
                var localidad1 = ""

                if (spinnerProvincia!!.selectedItemPosition != 0) { //provincia seleccionada
                    provincia1 = spinnerProvincia!!.selectedItem.toString()

                    localidad1 = if (spinnerLocalidad!!.selectedItemPosition != 0) { //localidad seleccionada
                            spinnerLocalidad!!.selectedItem.toString()
                        } else {
                            Toast.makeText(this@LocalityWeatherActivity, getString(R.string.TextSelectMun), Toast.LENGTH_SHORT).show()
                            return@OnClickListener
                        }
                } else {
                    Toast.makeText(this@LocalityWeatherActivity, getString(R.string.TextSelectProv), Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                println("Localidades y provincias validas")
                val cod1 = obtenerCodigoLocalidad(provincia1, localidad1)
                if (cod1 != -1) {
                    obtenerClima(cod1)
                } else {
                    Toast.makeText(this@LocalityWeatherActivity, getString(R.string.TextErrorCodProv), Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun obtenerCodigoLocalidad(provincia: String, localidad: String): Int {
        var codigo = -1
        val `is` = resources.openRawResource(R.raw.codmunpro)
        val csvFile = CSVFile(`is`)
        val csvList = csvFile.read()
        println(csvList.size)
        for (stringArray in csvList) {
            //El array que contiene la localidad es stringArray[4]
            //El array que contiene la provincia es stringArray[5]
            //los dos primeros son el id
            if (stringArray[4] == localidad && stringArray[5] == provincia) {
                codigo = stringArray[1].toInt() * 1000 + stringArray[2].toInt()
                break
            }
        }
        return codigo
    }

    private fun obtenerClima(cod1: Int) {
        getModel200(cod1, 1)
    }

    private fun getModel200(cod: Int, index: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Se llama a la interfaz
        val jsonPlaceHolderApi = retrofit.create(
            JsonPlaceHolderApi::class.java
        )
        @SuppressLint("DefaultLocale") val url =
            "/opendata/api/prediccion/especifica/municipio/diaria/" + String.format("%05d", cod) +
                    "/?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZHJpODZ2aWdvQGdtYWlsLmNvbSIsImp0aSI6IjJkNzgzYjhhLTFjMTUtNGJjNS04ZmJkLTMwZmY4NWM2NWUyNSIsImlzcyI6IkFFTUVUIiwiaWF0IjoxNjAzMDE5NTg0LCJ1c2VySWQiOiIyZDc4M2I4YS0xYzE1LTRiYzUtOGZiZC0zMGZmODVjNjVlMjUiLCJyb2xlIjoiIn0.a1IIOmDUM1FI6neNmgLeT728iLAKa26mxia-Oe5sOWs"
        val call = jsonPlaceHolderApi.getModel200(url)
        call.enqueue(object : Callback<Model200?> {
            override fun onResponse(call: Call<Model200?>, response: Response<Model200?>) {
                if (!response.isSuccessful) {
                    println("Primera Operacion Incorrecta")
                    return
                }
                getPrediction(response.body(), index)
            }

            override fun onFailure(call: Call<Model200?>, t: Throwable) {}
        })
    }

    private fun getPrediction(model200: Model200?, index: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        var url2 = model200!!.datos
        url2 = url2.replace(baseUrl, "")
        println("Lanzando prediccion$index")
        val callPrediccion = jsonPlaceHolderApi.getPrediccion(url2)
        callPrediccion.enqueue(object : Callback<List<PrediccionMunicipio?>> {
            override fun onResponse(
                call: Call<List<PrediccionMunicipio?>>,
                response: Response<List<PrediccionMunicipio?>>
            ) {
                if (!response.isSuccessful) {
                    println("Operacion Incorrecta")
                    return
                }
                val prediccionMunicipioList = response.body()!!
                val prediccion = prediccionMunicipioList[0]
                println("Recibido prediccion 1")
                comprobarRecepcion(prediccion, index)
            }

            override fun onFailure(call: Call<List<PrediccionMunicipio?>>, t: Throwable) {}
        })
    }

    private fun comprobarRecepcion(prediccionMunicipio: PrediccionMunicipio?, index: Int) {
        prediccion1 = prediccionMunicipio!!
        enviarDatosClima()

    }

    private fun enviarDatosClima() {

        val intent = Intent(this, WeatherActivity::class.java)
        println("Provincia; " + prediccion1!!.provincia + "Localidad: " + prediccion1!!.nombre)
        intent.putExtra("prediccionMunicipio1", prediccion1)
        startActivity(intent)
    }
}