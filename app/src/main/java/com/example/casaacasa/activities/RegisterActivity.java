package com.example.casaacasa.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.casaacasa.R;

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
                    if(pasword.getText().toString().equals(repPassword.getText().toString())){
                        Intent intent=new Intent(RegisterActivity.this, SecondRegisterActivity.class);
                        intent.putExtra("userName", email.getText().toString());
                        intent.putExtra("email", email.getText().toString());
                        intent.putExtra("pasword", pasword.getText().toString());
                        startActivity(intent);
                    } else{
                        Toast.makeText(RegisterActivity.this, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(RegisterActivity.this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}