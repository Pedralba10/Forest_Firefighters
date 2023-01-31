package com.example.forest_firefighters

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class DBHelper(context: Context) : SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        internal const val name = "forest_firefighters"
        internal val factory = null
        internal const val version = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        //Sentencia para crear la tabla de la base de datos
        db.execSQL("create table usuario(nombre text, apellidos text, _id primary key, nick text, correo text, contrasena text, estado int)")
        db.execSQL("create table incendio(_id primary key, nombre_incendio text, provincia text, charley text, delta text, romeo text , helicoptero text, avion text, c_helitransportada text, comentario text, latitud decimal(10,8), longitud decimal(10,8), fecha text)")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS usuario")
        db.execSQL("DROP TABLE IF EXISTS incendio")
    }

    fun insertarDB(nombre: String,apellidos: String, nick: String, correo: String,contrasena: String) {
        val db: SQLiteDatabase = writableDatabase
        val registro = ContentValues()

        registro.put("nombre", nombre)
        registro.put("apellidos", apellidos)
        registro.put("nick", nick)
        registro.put("correo", correo)
        registro.put("contrasena", contrasena)

        db.insert("usuario", null, registro)
        db.close()

    }

    fun buscarDB(mail: String, password: String): Boolean {
        val db: SQLiteDatabase = writableDatabase
        val query =
            "select correo, contrasena from usuario where correo = '${mail}' and contrasena = '${password}'"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            cursor.close()
            return true
        }
        cursor.close()
        return false
    }

    fun consultarDB(nick: String, username: String): Boolean {
        val db: SQLiteDatabase = writableDatabase
        val query =
            "select nick, correo from usuario where nick = '${nick}' or correo = '${username}'"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            cursor.close()
            return true
        }
        cursor.close()
        return false
    }

    @SuppressLint("Recycle")
    fun recorer(latitud: Double, longitud: Double): Boolean {
        val db: SQLiteDatabase = readableDatabase
        val query = "SELECT nombre_incendio FROM incendio WHERE latitud = '${latitud}' and longitud '${longitud}'"
        val cursor = db.rawQuery(query,null)

        if (cursor.moveToFirst()){
            cursor.close()
            return true
        }else{
            cursor.close()
            return false
        }
    }

    fun guardarIncendio(nombre_incendio: String, provincia: String, charley: String, delta: String, romeo: String, helicoptero: String, avion: String, c_helitransportada: String, comentario: String, latitud: Double,longitud: Double){
        val db: SQLiteDatabase = writableDatabase
        val registro = ContentValues()

        registro.put("nombre_incendio", nombre_incendio)
        registro.put("provincia", provincia)
        registro.put("charley", charley)
        registro.put("delta", delta)
        registro.put("romeo", romeo)
        registro.put("helicoptero", helicoptero)
        registro.put("avion", avion)
        registro.put("c_helitransportada", c_helitransportada)
        registro.put("comentario", comentario)
        registro.put("latitud", latitud)
        registro.put("longitud", longitud)
        registro.put("fecha", getDate())


        db.insert("incendio", null, registro)
        db.close()
    }

    private fun getDate(): String {
        var dateFormat: SimpleDateFormat = SimpleDateFormat("EEEE dd/MM/yyyy hh:mm a", Locale.getDefault())
        var date: Date = Date()
        return dateFormat.format(date)
    }

    fun consultarIncendio(nombre_incendio: String): Boolean {
        val db: SQLiteDatabase = readableDatabase
        val query = "SELECT nombre_incendio FROM incendio WHERE nombre_incendio = ${nombre_incendio}"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            cursor.close()
            return true
        }
        cursor.close()
        return false
    }
    fun modificarIncendio(nombre_incendio: String, provincia: String, charley: String, delta: String, romeo: String, helicoptero: String, avion: String, c_helitransportada: String, comentario: String) {
        val args = arrayOf(nombre_incendio.toString())

        val registro = ContentValues()
        registro.put("nombre_incendio", nombre_incendio)
        registro.put("provincia", provincia)
        registro.put("charley", charley)
        registro.put("delta", delta)
        registro.put("romeo", romeo)
        registro.put("helicoptero", helicoptero)
        registro.put("avion", avion)
        registro.put("c_helitransportada", c_helitransportada)
        registro.put("comentario", comentario)

        val db = this.writableDatabase
        db.update("incendio",registro,"nombre_incendio = ?",args)
        db.close()
    }
    fun eliminar(nombre_incendio: String): Int {
        val args = arrayOf(nombre_incendio.toString())

        val db = this.writableDatabase
        val borrados = db.delete("incendio","nombre_incendio = ?",args)

        //db.execSQL("DELETE FROM incendio WHERE nombre_incendio = ?",args)
        db.close()
        return borrados
    }
}