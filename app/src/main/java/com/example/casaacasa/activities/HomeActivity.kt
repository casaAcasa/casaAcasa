package com.example.casaacasa.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.casaacasa.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_second_register.*

enum class ProviderType {
    //Tipo de autentificacion que tenemos - basica por email y contrasenya
    BASIC
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        val name = bundle?.getString("name")
        val surname = bundle?.getString("surname")
        setup(email ?:"", provider ?:"", name ?: "", surname ?: "")
    }

    private fun setup(email: String, provider: String, name : String, surname : String) {
        title = "Fin del Registro"
        homeEmailTextView.text = email
        homeproviderTextView.text = provider
        nombreRegistradoEjemplo.text = "Enorabuena $name $surname, te has registrado exitosamente!"

        goPerfilButton.setOnClickListener {
            val busquedaIntent = Intent(this, BusquedaActivity::class.java)
            startActivity(busquedaIntent)
        }
        //GUARDAR FUNCION PARA EL POP UP DE PERFIL Y CAMBIAR ONBACKPRESSED A INTENT A LOGIN (PERFIL -> LOGIN)
        logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}