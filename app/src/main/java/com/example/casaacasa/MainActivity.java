package com.example.casaacasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.casaacasa.modelo.Chat;
import com.example.casaacasa.modelo.ListAdaptor;
import com.example.casaacasa.modelo.ListElement;
import com.example.casaacasa.modelo.Solicitud;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, Chat.class );
        startActivity(intent);
    }
}