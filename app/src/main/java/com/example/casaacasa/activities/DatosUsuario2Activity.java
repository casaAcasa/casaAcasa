package com.example.casaacasa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.utils.Constantes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class DatosUsuario2Activity extends AppCompatActivity {
    private String IDusuario;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psswd);
        IDusuario=Constantes.getIdUsuarioLogueado();
        infoViews();
    }

    private void infoViews() {
        EditText contrasena=findViewById(R.id.passwordEditText);
        EditText repContrasena=findViewById(R.id.repPassword);

        Button confirmar=findViewById(R.id.confirmarContrasena);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contrasena.getText().toString().trim().equals("")||repContrasena.getText().toString().trim().equals("")){
                    Toast.makeText(DatosUsuario2Activity.this, "Debes completar ambos campos", Toast.LENGTH_SHORT).show();
                } else {
                    if(contrasena.getText().toString().equals(repContrasena.getText().toString())){
                        Constantes.db.child("Usuario").child("password").setValue(contrasena.getText().toString());
                        //TODO Cambiar la contraseña en la autenticación

                        Toast.makeText(DatosUsuario2Activity.this, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(DatosUsuario2Activity.this, PerfilActivity.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(DatosUsuario2Activity.this, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}