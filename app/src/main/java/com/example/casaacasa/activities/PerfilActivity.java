package com.example.casaacasa.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Solicitud;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.modelo.Vivienda;
import com.example.casaacasa.utils.Constantes;
import com.example.casaacasa.utils.Estado;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity {
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        inflater = LayoutInflater.from(PerfilActivity.this);
    }

    public void irBusqueda (View v){
        Intent intent=new Intent(PerfilActivity.this, BusquedaActivity.class);
        startActivity(intent);
    }
    public void irChat (View v){
        Intent intent=new Intent(PerfilActivity.this, ChatActivity.class);
        startActivity(intent);
    }

    public void irQuedadas (View v){
        AlertDialog.Builder dialog= new AlertDialog.Builder(PerfilActivity.this);
        dialog.setTitle("Pagina no funcional.");
        View view = inflater.inflate(R.layout.popup_eliminar_chat, null);
        dialog.setView(view);
        dialog.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {

            }
        });
        dialog.show();
    }

    public void irMap (View v){
        AlertDialog.Builder dialog= new AlertDialog.Builder(PerfilActivity.this);
        dialog.setTitle("Pagina no funcional.");
        View view = inflater.inflate(R.layout.popup_eliminar_chat, null);
        dialog.setView(view);
        dialog.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {

            }
        });
        dialog.show();
    }

    public void irCambiosDatos (View v){
        Intent intent=new Intent(PerfilActivity.this, DatosViviendaActivity.class);
        startActivity(intent);
    }

}
