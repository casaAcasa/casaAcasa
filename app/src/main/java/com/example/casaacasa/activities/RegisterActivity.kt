package com.example.casaacasa.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.casaacasa.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    private var missingFields = "Faltan campos obligatorios por rellenar"
    private var passwordnotmatch = "Las contraseñas no coinciden"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //  add
        secondRegister()
    }


    private fun secondRegister() {
        continueButton.setOnClickListener {
            //val context = applicationContext
            if (emailRegisterP1EditText.text.isNotEmpty() && passwordRegisterP1EditText.text.isNotEmpty()
                && repeatPasswordRegisterP1EditText.text.isNotEmpty()) {
                if(passwordRegisterP1EditText.text.toString() == repeatPasswordRegisterP1EditText.text.toString()) {
                    val secondRegisterIntent = Intent(this, SecondRegisterActivity::class.java).apply {
                        putExtra("email", emailRegisterP1EditText.text.toString())
                        putExtra("password", passwordRegisterP1EditText.text.toString())
                    }
                    startActivity(secondRegisterIntent)
                } else {
                    var toast = Toast.makeText(this, passwordnotmatch, Toast.LENGTH_SHORT)
                    toast.show()
                }

            } else {
                var toast = Toast.makeText(this, missingFields, Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }


    /*
    private fun add() {
        continueButton.setOnClickListener {
            val context = applicationContext

            if (userRegisterP1EditText.text.isNotEmpty() && emailRegisterP1EditText.text.isNotEmpty()
                && passwordRegisterP1EditText.text.isNotEmpty() && repeatPasswordRegisterP1EditText.text.isNotEmpty()) {
                if(passwordRegisterP1EditText.text.toString() == repeatPasswordRegisterP1EditText.text.toString()){
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
                } else {
                    var toast = Toast.makeText(context, passwordnotmatch, Toast.LENGTH_SHORT)
                    toast.show()
                }

            } else {
                var toast = Toast.makeText(context, missingFields, Toast.LENGTH_LONG)
                toast.show()
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
        val registerIntent = Intent(this, SecondRegisterActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(registerIntent)
    }
     */
}




//https://developer.android.com/training/basics/firstapp/starting-activity
