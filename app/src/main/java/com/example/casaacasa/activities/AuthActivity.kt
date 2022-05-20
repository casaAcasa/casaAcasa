package com.example.casaacasa.activities

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.casaacasa.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Setup
        setup()
        registerText.setPaintFlags(registerText.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        registerText.setText("¿No estás registrado aun?")
    }

    private fun setup() {
        title = "Autentificacion"
        //  Log in button
        logInButton.setOnClickListener {
            if (emailAuthEditText.text.isNotEmpty() && passwordAuthEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        emailAuthEditText.text.toString(),
                        passwordAuthEditText.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            showAlert()
                        }
                    }
            }
        }
        //  Register URL
        registerText.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
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

    private fun showHome(email: String, provider: ProviderType) {
        val perfilIntent = Intent(this, PerfilActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(perfilIntent)
    }


}