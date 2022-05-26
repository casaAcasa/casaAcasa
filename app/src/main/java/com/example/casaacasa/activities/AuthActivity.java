package com.example.casaacasa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.utils.Constantes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //TODO Comprobar que todo funcione
        // Hay que quitar lo de cambiar email y cambiar contraseña, por el tema de la autenticación de usuario
        // Me falta limitar los intercambios y que se cambie el color del usuario

        reguistrarse();
        loguearse();
    }

    private void reguistrarse() {
        TextView registro=findViewById(R.id.registerText);
        registro.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AuthActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loguearse() {
        Button login=findViewById(R.id.logInButton);

        EditText email=findViewById(R.id.emailAuthEditText);
        EditText password=findViewById(R.id.passwordAuthEditText);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().trim().equals("")&&!password.getText().toString().trim().equals("")){
                    FirebaseAuth.getInstance()
                            .signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        //TODO Recuperar aquí el usuario de BBDD
                                        Constantes.db.child("Usuario").orderByChild("mail").equalTo(email.getText().toString())
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        Usuario usuario = null;
                                                        for (DataSnapshot u:snapshot.getChildren()) {
                                                            usuario=u.getValue(Usuario.class);
                                                        }
                                                        Constantes.setIdUsuarioLogueado(usuario.getUid());
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                        Intent intent=new Intent(AuthActivity.this, BusquedaActivity.class);
                                        startActivity(intent);
                                    } else{
                                        Toast.makeText(AuthActivity.this, "El correo o la constraseña no son correctos", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(AuthActivity.this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}