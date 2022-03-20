package com.example.casaacasa.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FnucionesBBDD {
    public static final DatabaseReference db= FirebaseDatabase.getInstance().getReference();
    public static final FirebaseStorage storage = FirebaseStorage.getInstance();
    public static final StorageReference storageRef=storage.getReference();

    public FnucionesBBDD(){

    }



}
