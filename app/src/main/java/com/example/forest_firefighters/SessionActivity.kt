package com.example.forest_firefighters


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.PatternsCompat
import kotlinx.android.synthetic.main.activity_session.*
import java.util.regex.Pattern

class SessionActivity : AppCompatActivity() {

    lateinit var handler: DBHelper
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)

        handler = DBHelper(this)
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE)

        cuentaIniciada()

        registrarse.setOnClickListener {
            startActivity(Intent(this, SingupActivity::class.java))
        }

        btnPress.setOnClickListener {
            validate()
        }
    }
    fun validateEmail() : Boolean {
        //Recuperamos el contenido del textInputLayout
        val email = loginCorreo.editText?.text.toString()

        return if (email.isEmpty()) {
            loginCorreo.error = "El campo no puede estar vacío"
            false
        }else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            loginCorreo.error = "Por favor introduzca una dirección de correo electrónico válida"
            false
        }else{
            loginCorreo.error = null
            true
        }
    }

    fun validatePassword() : Boolean {
        //Recuperamos el contenido del textInputLayout
        val password = loginContrasena.editText?.text.toString()

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
            loginContrasena.error = "El campo no puede estar vacío"
            false
        }else if (!passwordRegex.matcher(password).matches()){
            loginContrasena.error = "La contraseña tiene que tener 6 caracteres una mayúscula una minúscula y un número"
            false
        }else{
            loginContrasena.error = null
            true
        }
    }

    fun validate(){
        val result = arrayOf(validateEmail(), validatePassword())
        if (false in result){
            return
        }
        if (handler.buscarDB(this.loginCorreo.editText?.text.toString(), this.loginContrasena.editText?.text.toString())) {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putString("usuarioCorreo", loginCorreo.toString())
            editor.putString("usuarioContrasena", loginContrasena.toString())
            editor.commit()
            Toast.makeText(this, "Sesión iniciada", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            Toast.makeText(this, "Correo o contraseña no son correctos", Toast.LENGTH_SHORT).show()
        }
    }

    fun cuentaIniciada() {
        val usuarioCorreo = preferences.getString("usuarioCorreo",null)
        val usuarioContrasena = preferences.getString("usuarioContrasena",null)

        if(usuarioCorreo != null && usuarioContrasena != null) {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}