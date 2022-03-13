package com.example.casaacasa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casaacasa.R;
import com.example.casaacasa.activities.ChatActivity;

public class SolicitudActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solicitud);
    }

    public void paginaChat(View v){
        Intent intent = new Intent(this, ChatActivity.class );
        startActivity(intent);
    }
}
