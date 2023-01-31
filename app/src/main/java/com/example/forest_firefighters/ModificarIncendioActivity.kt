package com.example.forest_firefighters

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_modificar_incendio.*

private lateinit var handler: DBHelper
private var latitud: Double = 0.0
private var longitud: Double = 0.0


class ModificarIncendioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_incendio)

        handler = DBHelper(this)
        todosMarcadores(this)
        }

        fun lectura(context: Context): SQLiteDatabase {
            val dbHelper = DBHelper(context)
            return dbHelper.getReadableDatabase()
        }

        fun todosMarcadores(context: Context) {
            val objetoIntent: Intent = intent
            latitud = objetoIntent.getDoubleExtra("latitudar", 0.0)
            longitud = objetoIntent.getDoubleExtra("longitud", 0.0)
            if (latitud != 0.0 && longitud != 0.0) {
                val c: Cursor = lectura(context).rawQuery(
                    "SELECT * FROM incendio WHERE latitud = ${latitud} AND longitud = ${longitud}",
                    null
                )
                if (c.moveToFirst() == true) {
                    do {
                        tVNIncendio.text = c.getString(1)
                        textViewNProvincia.text = c.getString(2)
                        tVCharley.text = c.getString(3)
                        tVDelta.text = c.getString(4)
                        tVRomeo.text = c.getString(5)
                        tVHelico.text = c.getString(6)
                        tVAT.text = c.getString(7)
                        tVElif.text = c.getString(8)
                        tVComentarios.text = c.getString(9)
                    } while (c.moveToNext())

                }
            } else {
                Toast.makeText(this, "Error en la recepcion de la ubicacion", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

