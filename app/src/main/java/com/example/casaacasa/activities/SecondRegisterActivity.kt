package com.example.casaacasa.activities

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.casaacasa.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_second_register.*

class SecondRegisterActivity : AppCompatActivity() {

    private var missingFields = "Faltan campos para poder registrarte"
    /* si se quiere utilizar los datos del form se puede usar esto para pasarlo por el put extra a la pagina que se quiera en el intent con val o sin
    private val birth = birthRegisterP2EditText.text.toString()
    private val phone = numberRegisterP2EditText.text.toString()*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_register)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val password = bundle?.getString("password")
        add(email ?: "" , password ?: "")

    }
    //Funcion que registra al usuario con los datos de la priera pagina de registro
    private fun add(email: String, password: String) {
        registerButton.setOnClickListener {
            if(nameRegisterP2EditText.text.isNotEmpty() && surnameRegisterP2EditText.text.isNotEmpty()
                && birthRegisterP2EditText.text.isNotEmpty() && numberRegisterP2EditText.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        email,
                        password
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            continueToHome(
                                email, ProviderType.BASIC)
                        } else {
                            showAlert()
                        }
                    }
            } else {
                var toast = Toast.makeText(this, missingFields, Toast.LENGTH_LONG)
                toast.show()
            }

        }
    }

    private fun continueToHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
            putExtra("name", nameRegisterP2EditText.text.toString())
            putExtra("surname", surnameRegisterP2EditText.text.toString())
        }
        startActivity(homeIntent)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error registrando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}