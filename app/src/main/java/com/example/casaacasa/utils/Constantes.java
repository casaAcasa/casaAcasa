package com.example.casaacasa.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Constantes {

    public static final String URL_FOTO_POR_DEFECTO_USUARIOS = "https://firebasestorage.googleapis.com/v0/b/fir-chat-1ab2c.appspot.com/o/foto_perfil%2Ffoto_por_defecto.png?alt=media&token=40eed45f-bd7f-4178-8e7c-092d7eb1fb64";
    public static final String NODO_USUARIOS = "Usuarios";
    public static final String NODO_MENSAJES = "Mensajes";
    public static final DatabaseReference db= FirebaseDatabase.getInstance().getReference();
    public static final FirebaseStorage storage = FirebaseStorage.getInstance();
    public static final StorageReference storageRef=storage.getReference();

}
