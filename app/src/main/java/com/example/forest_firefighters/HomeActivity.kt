package com.example.forest_firefighters

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE)

        mapaButtom.setOnClickListener {
            startActivity(Intent(this,MapsActivity::class.java))
        }
        historialButton.setOnClickListener {
            startActivity(Intent(this,ListaActivity::class.java))
        }
        tiempoButtom.setOnClickListener {
            startActivity(Intent(this,LocalityWeatherActivity::class.java))
        }
        redesButton.setOnClickListener {
            //Asignando valores
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.activity_redes,null)
            val btnEmail: TextView = view.findViewById(R.id.correoElectronico)
            val btnInstagram: ImageButton = view.findViewById(R.id.instagramButton)
            val btnTwitter: ImageButton = view.findViewById(R.id.twitterButton)
            //Pasando la vista al builder
            builder.setView(view)
            //Creando dialog
            val dialogo = builder.create()
            builder.setView(view)
            btnEmail.setOnClickListener {
                val emailIntent =
                    Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "forestfighters.dc@gmail.com", null))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Android APP - ")
                startActivity(
                    Intent.createChooser(
                        emailIntent,"Enviar Email"))
            }
            btnInstagram.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/forestfighters_4/")))
            }
            btnTwitter.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/ForestFighters_")))
            }
            dialogo.show()
        }
    }

    private fun cerrarSesion() {
        preferences.edit().clear().apply()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_usuario, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.CerrarSesion -> {
                cerrarSesion()
                startActivity(Intent(this,SessionActivity::class.java))
                Toast.makeText(this, "Sesion Cerrada", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.salir -> {
                startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                finish()
                System.exit(0)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
