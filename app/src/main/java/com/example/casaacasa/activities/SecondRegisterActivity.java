package com.example.casaacasa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.modelo.Vivienda;
import com.example.casaacasa.utils.Constantes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondRegisterActivity extends AppCompatActivity {
    private Intent startIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_register);

        startIntent = getIntent();
        registrar();
    }

    private void registrar() {
        EditText nombre = findViewById(R.id.nameRegisterP2EditText);
        EditText apellidos = findViewById(R.id.surnameRegisterP2EditText);
        TextView fechaNacimiento = findViewById(R.id.birthRegisterP2EditText);
        EditText telefono = findViewById(R.id.numberRegisterP2EditText);

        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int ano = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int anoMinimo = ano - 18;
                calendar.set(anoMinimo, mes, dia);
                long minDateInMilliSeconds = calendar.getTimeInMillis();

                int anoMaximo = ano - 100;
                calendar.set(anoMaximo, mes, dia);
                long maxDateInMiliSeconds = calendar.getTimeInMillis();


                DatePickerDialog datePickerDialog = new DatePickerDialog(SecondRegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fechaNacimiento.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }
                        , dia, mes, ano);
                datePickerDialog.getDatePicker().setMaxDate(minDateInMilliSeconds);
                datePickerDialog.getDatePicker().setMinDate(maxDateInMiliSeconds);
                datePickerDialog.show();
            }
        });

        Button registrar = findViewById(R.id.registerButton);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nombre.getText().toString().trim().equals("") && !apellidos.getText().toString().trim().equals("")
                        && !fechaNacimiento.getText().toString().trim().equals("") && !telefono.getText().toString().trim().equals("")) {
                    Pattern phonePattern = Pattern.compile("^\\d{9}$", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = phonePattern.matcher(telefono.getText().toString());

                    if (!matcher.find()) {
                        Toast.makeText(SecondRegisterActivity.this, "Debes escibi un número de teléfono válido", Toast.LENGTH_SHORT).show();
                    } else {
                        FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(startIntent.getStringExtra("email"), startIntent.getStringExtra("password"))
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            //TODO Crear aquí el usuario y enviarlo a BBDD
                                            Usuario usuarioLogeado = new Usuario(nombre.getText().toString(),
                                                    apellidos.getText().toString(), fechaNacimiento.getText().toString(), startIntent.getStringExtra("email"),
                                                    Integer.parseInt(telefono.getText().toString()), startIntent.getStringExtra("userName"), startIntent.getStringExtra("password"));
                                            Constantes.db.child("Usuario").child(usuarioLogeado.getUid()).setValue(usuarioLogeado);
                                            Vivienda viviendaUsuarioLogueado = new Vivienda("", 0, 0, "", usuarioLogeado.getUid(), new ArrayList<String>(), new ArrayList<String>(), 0.0, 0.0, 0.0, "", "", "");
                                            Constantes.db.child("Vivienda").child(viviendaUsuarioLogueado.getUid()).setValue(viviendaUsuarioLogueado);

                                            Constantes.setIdUsuarioLogueado(usuarioLogeado.getUid());

                                            Intent intent = new Intent(SecondRegisterActivity.this, HomeActivity.class);
                                            intent.putExtra("email", startIntent.getStringExtra("email"));
                                            intent.putExtra("provider", ProviderType.BASIC);
                                            intent.putExtra("name", nombre.getText().toString());
                                            intent.putExtra("surname", apellidos.getText().toString());
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(SecondRegisterActivity.this, "El correo escrito anteriormente ya es usado por otra cuenta", Toast.LENGTH_LONG).show();
                                            Log.i("TAG", task.getException().toString());
                                        }
                                    }
                                });
                    }

                } else {
                    Toast.makeText(SecondRegisterActivity.this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}