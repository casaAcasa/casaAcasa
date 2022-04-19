package com.example.casaacasa.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.casaacasa.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    public static final DatabaseReference db=FirebaseDatabase.getInstance().getReference();
    public static final FirebaseStorage storage = FirebaseStorage.getInstance();
    public static final StorageReference storageRef=storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vivienda);
        Intent intent=new Intent(MainActivity.this, AuthActivity.class);
        startActivity(intent);

        /*Usuario u=new Usuario("Oscar","Arrocha","Gascon","03/05/2002","arrocha.0305@gmail.com",654847508,"OscarPruebas","1234");
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
        v.getImagenes().add("viviendas/piso1.jpg");
        v.getImagenes().add("viviendas/piso2.jpg");
        v.getImagenes().add("viviendas/piso3.jpg");
        v.getImagenes().add("viviendas/piso4.jpg");
        v.getImagenes().add("viviendas/piso5.jpg");
        v.getImagenes().add("viviendas/piso6.jpg");
        db.child("Vivienda").child(v.getUid()).setValue(v);
        Log.i("TAG", "usu "+u.getUid());*/

        //firebase
        //auth usario valido

        //--watching firebase bide %
        //TODO: []DELETE ALL COMMENTS WHEN COMMIT
        //TODO: []IMPLEMENT LOGIN AND REGISTER ACTIVITIES
            //->ur https://www.youtube.com/watch?v=KYPc7CAYJOw
            //->alredy exist in other project
        //TODO: []IMPLEMENT THE TABBAR FUNCIONALITY
            //->alredy exist in other project - check what activities and clases import to reach good funcionality
        //TODO: []ADD CHANGE USER DATA XML AND ACTIVY
        //TODO: []DELETE TODOS FOR PUSH CORRECTLY

    }
}