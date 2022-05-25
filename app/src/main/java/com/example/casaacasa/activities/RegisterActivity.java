package com.example.casaacasa.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Usuario;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        continuar();
    }

    private void continuar() {
        EditText email=findViewById(R.id.emailRegisterP1EditText);
        EditText pasword=findViewById(R.id.passwordRegisterP1EditText);
        EditText repPassword=findViewById(R.id.repeatPasswordRegisterP1EditText);
        EditText nombreUsu=findViewById(R.id.nombreUsuarioRegister);

        Button continuar= findViewById(R.id.continueButton);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombreUsu.getText().toString().trim().equals("")&&!email.getText().toString().trim().equals("")
                        && !pasword.getText().toString().trim().equals("")
                        && !repPassword.getText().toString().trim().equals("")){
                    Pattern emailPattern=Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = emailPattern.matcher(email.getText().toString());
                    int cont=0;
                    if(!matcher.find()){
                        Toast.makeText(RegisterActivity.this, "Debes escribir un correo válido", Toast.LENGTH_SHORT).show();
                        cont++;
                    }

                    if(pasword.getText().toString().length()<6){
                        Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos 6 carácteres", Toast.LENGTH_SHORT).show();
                        cont++;
                    }

                    if(!pasword.getText().toString().equals(repPassword.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                        cont++;
                    }
                    if(cont==0){

                        Intent intent=new Intent(RegisterActivity.this, SecondRegisterActivity.class);
                        intent.putExtra("userName", email.getText().toString());
                        intent.putExtra("email", email.getText().toString());
                        intent.putExtra("password", pasword.getText().toString());
                        startActivity(intent);
                    }
                } else{
                    Toast.makeText(RegisterActivity.this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}