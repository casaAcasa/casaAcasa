package com.example.casaacasa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatosUsuario1Activity extends AppCompatActivity {
    private String IDusuario;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_datos);
        IDusuario=Constantes.getIdUsuarioLogueado();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recogerInformacionBBDD();
    }

    private void recogerInformacionBBDD() {
        Constantes.db.child("Usuario").child(IDusuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario=snapshot.getValue(Usuario.class);
                darTextoViews();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void darTextoViews() {
        //TODO quitar lo de cambiar correo porque no se puede

        EditText nombre=findViewById(R.id.userEditText);
        nombre.setText(usuario.getNombre());
        EditText apellidos=findViewById(R.id.surnameEditText);
        apellidos.setText(usuario.getApellidos());
        TextView fechaNacimiento=findViewById(R.id.birthEditText);
        //TODO Cambiar el formato del XML, para que parezca igual al resto

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
                long maxDateInMiliSeconds=calendar.getTimeInMillis();



                DatePickerDialog datePickerDialog = new DatePickerDialog(DatosUsuario1Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fechaNacimiento.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                }
                        ,dia,mes,ano);
                datePickerDialog.getDatePicker().setMaxDate(minDateInMilliSeconds);
                datePickerDialog.getDatePicker().setMinDate(maxDateInMiliSeconds);
                datePickerDialog.show();
            }
        });
        fechaNacimiento.setText(usuario.getFechaNacimiento());
        EditText numTel=findViewById(R.id.numberEditText);
        numTel.setText(String.valueOf(usuario.getTelefono()));

        TextView btnCambiarContrasena=findViewById(R.id.changePassword);
        btnCambiarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DatosUsuario1Activity.this, DatosUsuario2Activity.class);
                startActivity(intent);
            }
        });

        Button btnGuardar=findViewById(R.id.guardarDatosUsuario);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern phonePattern = Pattern.compile("^\\d{9}$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = phonePattern.matcher(numTel.getText().toString());
                Constantes.db.child("Usuario").child(IDusuario).child("nombre").setValue(nombre.getText().toString());
                Constantes.db.child("Usuario").child(IDusuario).child("apellidos").setValue(apellidos.getText().toString());
                Constantes.db.child("Usuario").child(IDusuario).child("fechaNacimiento").setValue(fechaNacimiento.getText().toString());
                if (!matcher.find()) {
                    Toast.makeText(DatosUsuario1Activity.this, "Debes escibi un número de teléfono válido", Toast.LENGTH_SHORT).show();
                } else {
                    Constantes.db.child("Usuario").child(IDusuario).child("telefono").setValue(Integer.parseInt(numTel.getText().toString()));
                }
            }
        });
    }
}