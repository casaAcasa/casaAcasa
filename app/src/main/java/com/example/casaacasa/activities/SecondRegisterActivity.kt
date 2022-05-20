package com.example.casaacasa.activities

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.casaacasa.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_second_register.*

class SecondRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_register)
        //  add - funcion que crea usuario en firebase
        add()

        // Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?:"", provider ?:"")

        //  acces
        access()

    }

    private fun add() {
        continueButton.setOnClickListener {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        emailRegisterP1EditText.text.toString(),
                        passwordRegisterP1EditText.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showSecondRegister(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            showAlert()
                        }
                    }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autentificando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showSecondRegister(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    private fun access() {
        registerButton.setOnClickListener {
            val context = applicationContext

            if(userRegisterP2EditText.text.isNotEmpty() && surnameRegisterP2EditText.text.isNotEmpty()
                && birthRegisterP2EditText.text.isNotEmpty() && numberRegisterP2EditText.text.isNotEmpty()) {
                //  if para el formato de fecha
                //  cualquiera que sea el valor que nos da hayq que cartearlo a string para guardarlo a la creacion del usuario

            }
        }
    }

    //volver invisible
    private fun setup(email: String, provider: String) {
        title = "Autentificacion"
        emailRP2TextView.text = email
        providerRP2TextView.text = provider
    }



}