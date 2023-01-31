package com.example.forest_firefighters

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.registrar_incendio_mapa.*


private lateinit var handler: DBHelper
private var latitud: Double = 0.0
private var longitud: Double = 0.0

class RegistrarIncendioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrar_incendio_mapa)

        handler = DBHelper(this)
        val objetoIntent: Intent = intent
        latitud = objetoIntent.getDoubleExtra("latitud",0.0)
        longitud = objetoIntent.getDoubleExtra("longitud", 0.0)

        btnRegistrar.setOnClickListener {
            validateIncendio()

            if(latitud == 0.0 && longitud == 0.0){
                Toast.makeText(this, "Error en el registro del incendio", Toast.LENGTH_SHORT).show()
            }else{
                handler.guardarIncendio(this.nombre_incendio.editText?.text.toString(), this.etProvinmodi.editText?.text.toString(),
                    this.etCharley.text.toString(),this.etDelta.text.toString(),this.etCharley.text.toString(), this.etRomeo.text.toString(),
                    this.etAvion.text.toString(),this.etHelicoptero.text.toString(), this.etComentario.text.toString(),
                    latitud,
                    longitud)
                Toast.makeText(this, "Incendio registrado correctamente", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MapsActivity::class.java))
                MapsActivity().todosMarcadores(this)
            }
        }

    }
    fun validateIncendio() : Boolean {
        //Recuperamos el contenido del textInputLayout
        val email = nombre_incendio.editText?.text.toString()

        return if (email.isEmpty()) {
            nombre_incendio.error = "El campo no puede estar vacío"
            false
        }else{
            nombre_incendio.error = null
            true
        }
    }
}
