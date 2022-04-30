package com.example.casaacasa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViviendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout gallery = findViewById(R.id.gallery);

        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i=0; i<6; i++){
            View view = inflater.inflate(R.layout.imagen,gallery, false);
            TextView t= view.findViewById(R.id.text);
            t.setText("item"+i);
            ImageView imageView=view.findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.ic_launcher_background);
            gallery.addView(view);
        }
    }
}
