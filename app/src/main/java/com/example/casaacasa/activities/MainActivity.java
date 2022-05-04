package com.example.casaacasa.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.modelo.Vivienda;
import com.example.casaacasa.utils.Constantes;
import com.example.casaacasa.utils.TipoVivienda;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vivienda);
        Intent intent=new Intent(MainActivity.this, ChatActivity.class);
        startActivity(intent);
        /*Usuario u=new Usuario("Oscar","Arrocha","Gascon","03/05/2002","arrocha.0305@gmail.com",654847508,"OscarPruebas","1234");
        Constantes.db.child("Usuario").child(u.getUid()).setValue(u);
        String des="Este piso es muy bonita. Tiene vistas a toda la ciudad, a la playa y a la montaña, pero está en un 5º y no tiene ascensor";
        ArrayList<String> normas=new ArrayList<>();
        normas.add("No fiestas");
        normas.add("No molestar a los vecinos");
        normas.add("No fumar");
        ArrayList<String> servicios=new ArrayList<>();
        servicios.add("Wifi");
        servicios.add("Bitroceramica");
        servicios.add("Secador");
        Vivienda v=new Vivienda("Barcelona", String.valueOf(TipoVivienda.PISO), 4, 23, des, u.getUid(), normas, servicios);
        v.getImagenes().add("viviendas/piso1.jpg");
        v.getImagenes().add("viviendas/piso2.jpg");
        v.getImagenes().add("viviendas/piso3.jpg");
        v.getImagenes().add("viviendas/piso4.jpg");
        v.getImagenes().add("viviendas/piso5.jpg");
        v.getImagenes().add("viviendas/piso6.jpg");
        db.child("Vivienda").child(v.getUid()).setValue(v);
        Log.i("TAG", "usu "+u.getUid());
               Usuario u=new Usuario("Loguin","L","O","15/03/2022","usuario.logeado@gmail.com",654847508,"LoguinPruebas","1234");
        db.child("Usuario").child(u.getUid()).setValue(u);
        String des="Este piso es muy bonita. Tiene vistas a toda la ciudad, a la playa y a la montaña, pero está en un 5º y no tiene ascensor";
        ArrayList<String> normas=new ArrayList<>();
        normas.add("norma 1");
        normas.add("norma 2");
        normas.add("norma 3");
        ArrayList<String> servicios=new ArrayList<>();
        servicios.add("servicio 1");
        servicios.add("servicio 2");
        servicios.add("servicio 3");
        Vivienda v=new Vivienda("Madrid", String.valueOf(TipoVivienda.PISO), 6, 50, des, u.getUid(), normas, servicios);
        db.child("Vivienda").child(v.getUid()).setValue(v);*/

    }
}