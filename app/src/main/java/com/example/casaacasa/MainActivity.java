package com.example.casaacasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.modelo.Vivienda;
import com.example.casaacasa.utils.TipoVivienda;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final DatabaseReference db=FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=new Intent(MainActivity.this, ViviendaActivity.class);
        startActivity(intent);
        /*Usuario u=new Usuario("Oscar","Arrocha","Gascon","03/05/2002","arrocha.0305@gmsil.com",654847508,"OscarPruebas","1234");
        db.child("Usuario").child(u.getUid()).setValue(u);
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
        db.child("Vivienda").child(v.getUid()).setValue(v);
        Log.i("TAG", "usu "+u.getUid());*/

    }
}