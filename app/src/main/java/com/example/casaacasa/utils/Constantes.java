package com.example.casaacasa.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Constantes {
    public static final DatabaseReference db= FirebaseDatabase.getInstance().getReference();
    public static final FirebaseStorage storage = FirebaseStorage.getInstance();
    public static final StorageReference storageRef=storage.getReference();

    public static String idUsuarioLogueado="";

    public static String getIdUsuarioLogueado() {
        return idUsuarioLogueado;
    }

    public static void setIdUsuarioLogueado(String idUsuarioLogueado) {
        Constantes.idUsuarioLogueado = idUsuarioLogueado;
    }
}
