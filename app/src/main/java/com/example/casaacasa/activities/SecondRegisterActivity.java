package com.example.casaacasa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.casaacasa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SecondRegisterActivity extends AppCompatActivity {
    private Intent startIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_register);

        startIntent=getIntent();
        registrar();
    }

    private void registrar() {
        EditText nombre=findViewById(R.id.nameRegisterP2EditText);
        EditText apellidos=findViewById(R.id.surnameRegisterP2EditText);
        EditText fechaNacimiento=findViewById(R.id.birthRegisterP2EditText);
        EditText telefono=findViewById(R.id.numberRegisterP2EditText);

        Button registrar= findViewById(R.id.registerButton);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombre.getText().toString().trim().equals("")&&!apellidos.getText().toString().trim().equals("")
                        &&!fechaNacimiento.getText().toString().trim().equals("")&&!telefono.getText().toString().trim().equals("")){
                    FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(startIntent.getStringExtra("email"),startIntent.getStringExtra("password"))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        //TODO Crear aquí el usuario y enviarlo a BBDD

                                        Intent intent =new Intent(SecondRegisterActivity.this, HomeActivity.class);
                                        intent.putExtra("email", startIntent.getStringExtra("email"));
                                        intent.putExtra("provider", ProviderType.BASIC);
                                        intent.putExtra("name", nombre.getText().toString());
                                        intent.putExtra("surname", apellidos.getText().toString());
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }
        });
    }

}