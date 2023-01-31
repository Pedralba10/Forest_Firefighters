package com.example.forest_firefighters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.util.PatternsCompat
import kotlinx.android.synthetic.main.activity_singup.*
import kotlinx.android.synthetic.main.activity_singup.registroContrasena
import kotlinx.android.synthetic.main.activity_singup.registroCorreo
import java.util.regex.Pattern

class SingupActivity : AppCompatActivity() {

    private lateinit var handler: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)

        handler = DBHelper(this)

        buttonRegistro.setOnClickListener {
            validate()
        }
    }
    fun validateEmail() : Boolean {
        //Recuperamos el contenido del textInputLayout
        val email = registroCorreo.editText?.text.toString()

        return if (email.isEmpty()) {
            registroCorreo.error = "El campo no puede estar vacío"
            false
        }else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            registroCorreo.error = "Por favor introduzca una dirección de correo electrónico válida"
            false
        }else{
            registroCorreo.error = null
            true
        }
    }

    fun validatePassword() : Boolean {
        //Recuperamos el contenido del textInputLayout
        val password = registroContrasena.editText?.text.toString()

        // Patrón con expresiones regulares
        val passwordRegex = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +        //at least 1 lower case letter
                    "(?=.*[A-Z])" +        //at least 1 upper case letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$"
        )

        return if (password.isEmpty()){
            registroContrasena.error = "El campo no puede estar vacío"
            false
        }else if (!passwordRegex.matcher(password).matches()){
            registroContrasena.error = "La contraseña tiene que tener 6 caracteres una mayúscula una minúscula y un número"
            false
        }else{
            registroContrasena.error = null
            true
        }
    }
    fun validate(){
        val result = arrayOf(validateEmail(), validatePassword())
        if (false in result){
            return
        }
        if (handler.consultarDB(this.registroNick.editText?.text.toString(), this.registroCorreo.editText?.text.toString())) {
            Toast.makeText(this, "Nick o correo ya registrado", Toast.LENGTH_SHORT).show()
        } else {
            handler.insertarDB(this.registroNombre.editText?.text.toString(), this.registroApellidos.editText?.text.toString(), this.registroNick.editText?.text.toString(), this.registroCorreo.editText?.text.toString(), this.registroContrasena.editText?.text.toString())
            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SessionActivity::class.java))

        }

    }
}