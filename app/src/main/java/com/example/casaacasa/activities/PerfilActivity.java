package com.example.casaacasa.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.modelo.Valoracion;
import com.example.casaacasa.modelo.Vivienda;
import com.example.casaacasa.utils.Constantes;
import com.example.casaacasa.utils.TipoValoracion;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import id.zelory.compressor.Compressor;

public class PerfilActivity extends AppCompatActivity {
    private String IDUsuarioLogeado;
    private Vivienda vivienda;
    private LayoutInflater inflater;
    private TipoValoracion anfitrion;

    private ProgressDialog cargando;
    private Bitmap bitmap;
    private ImageView foto;
    private StorageReference guardadoImagenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        inflater=LayoutInflater.from(PerfilActivity.this);
        anfitrion=TipoValoracion.INQUILINO;
        IDUsuarioLogeado="d5edaee4-9498-48c4-a4c4-baa3978adfeb";

        //TODO A la view le falta el nombre de usuario
        // Decirle al Jorge que cuando junte los tabBars que mire como lo he hecho aquí, y explicárselo



        recogerInformacionBBDD();
        seleccionarImagen();
    }

    private void seleccionarImagen() {
        LinearLayout gallery = findViewById(R.id.galleryPerfil);
        View view = inflater.inflate(R.layout.imagen, gallery, false);
        foto=view.findViewById(R.id.imageView);
        guardadoImagenes=Constantes.storageRef.child("Viviendas");
        Button seleccionar=findViewById(R.id.seleccionarImagen);
        seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(PerfilActivity.this);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE&&resultCode== Activity.RESULT_OK){
            Uri imagenUri=CropImage.getPickImageResultUri(this,data);

            //Recortar imagen

            CropImage.activity(imagenUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setRequestedSize(640,480)
                    .setAspectRatio(2,1).start(PerfilActivity.this);


        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);

            if(resultCode==RESULT_OK){
                Uri resultUri=result.getUri();

                File url=new File(resultUri.getPath());
                Picasso.with(this).load(url).into(foto);

                //Comprimiendo imagen

                try{
                    bitmap=new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(90)
                            .compressToBitmap(url);
                } catch(IOException e){
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
                final byte [] byte1=byteArrayOutputStream.toByteArray();

                //Fin del compresor

                int p=(int) (Math.random()+25+1); int s=(int) (Math.random()+25+1);
                int t=(int) (Math.random()+25+1); int c=(int) (Math.random()+25+1);
                int numero1=(int)(Math.random()+1012+2111);
                int numero2=(int)(Math.random()+1012+2111);
                String[] elementos={"a","b","c","d","e","f","g","h","i","j","k",
                "k", "l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
                final String aleatorio=elementos[p]+elementos[s]+
                        numero1+elementos[t]+elementos[c]+numero2+"comprimido.jpg";

                //Código para subir la foto al github
                cargando.setTitle("Subiendo foto");
                cargando.setMessage("espere wey");
                cargando.show();

                final StorageReference ref=guardadoImagenes.child(aleatorio);
                UploadTask uploadTask=ref.putBytes(byte1);

                //Subir imagen en Storage

                Task<Uri> uriTask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw Objects.requireNonNull(task.getException());
                        }
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Uri downloadUri=task.getResult();
                        vivienda.getImagenes().add(downloadUri.toString());
                        Constantes.db.child("Vivienda").child(vivienda.getUid()).child("imagenes").setValue(vivienda.getImagenes());
                        cargando.dismiss();
                        Toast.makeText(PerfilActivity.this, downloadUri.toString()+" "+aleatorio, Toast.LENGTH_SHORT).show();
                    }
                });

                //31:43

            }
        }
    }


    private void recogerInformacionBBDD() {
        Constantes.db.child("Vivienda")
                .orderByChild("user_id").equalTo(IDUsuarioLogeado).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot v: snapshot.getChildren()) {
                    Log.i("TAG","si");
                    vivienda=v.getValue(Vivienda.class);
                }
                Log.i("TAG","no");
                darTextoALasViews();
                anadirImagenes();
                leerValoraciones();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void darTextoALasViews() {
        Constantes.db.child("Usuario")
                .child(vivienda.getUser_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario u=snapshot.getValue(Usuario.class);
                TextView nombreUsuario= findViewById(R.id.nombreUsuPerfil);
                nombreUsuario.setText(u.getNombreUsuario());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TextView descripcion= findViewById(R.id.descripcionPerfil);
        descripcion.setText(vivienda.getDescripcion());

        //TODO Si el desto está vacio petará

        TextView normas=findViewById(R.id.normasPerfil);
        normas.setText(getArraySringEnString(vivienda.getNormas()));
        TextView servicios=findViewById(R.id.serviciosPerfil);
        servicios.setText(getArraySringEnString(vivienda.getServicios()));

        Button guardarCambios=findViewById(R.id.guardarCambios);
        guardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO No tiene que ser usuarios, tiene que ser su vivienda. Y TENGO QUE ESCRIBIRLO TODO BIEN
                // Comprovar qie me lo haga bien. Sino mirar los temas de las arrays de Strings

                Constantes.db.child("Vivienda").child(vivienda.getUid()).child("descripcion").setValue(descripcion.getText().toString());
                Constantes.db.child("Vivienda").child(vivienda.getUid()).child("normas").setValue(getStringEnArrayString(normas.getText().toString()));
                Constantes.db.child("Vivienda").child(vivienda.getUid()).child("servicios").setValue(getStringEnArrayString(servicios.getText().toString()));

            }
        });

    }

    private void anadirImagenes() {

        LinearLayout gallery = findViewById(R.id.galleryPerfil);
        for (int i=0; i<vivienda.getImagenes().size(); i++){
            StorageReference ruta=Constantes.storageRef.child(vivienda.getImagenes().get(i));
            View view = inflater.inflate(R.layout.imagen, gallery, false);
            ImageView imageView=view.findViewById(R.id.imageView);

            final long ONE_MEGABYTE = 1024 * 1024;
            ruta.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    //Pasar de bytes a ImageView
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);
                    gallery.addView(view);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        }
    }

    private void leerValoraciones(){
        Constantes.db.child("Valoracion").orderByChild("receptor").equalTo(vivienda.getUser_id())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LinearLayout valorations=findViewById(R.id.valocinesListPerfil);
                        valorations.removeAllViews();
                        ArrayList<Valoracion> valoraciones=new ArrayList<>();
                        for(DataSnapshot v:snapshot.getChildren()){
                            Valoracion vo=v.getValue(Valoracion.class);
                            valoraciones.add(vo);
                        }
                        anadirValoraciones(valoraciones);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void anadirValoraciones(ArrayList<Valoracion> valoraciones){
        //TODO Podría poner aquí el condicionante de TipoSolicitud. De esta forma podria utilizar la información de las otras valoraciones para calcular la media de tanto inquilino como anfitrion
        LinearLayout valorations=findViewById(R.id.valocinesListPerfil);
        valorations.removeAllViewsInLayout();
        double estrellasAnfitrion=0;
        int numValoracionesAnfitrion=0;
        double estrellasInquilino=0;
        int numValoracionesInquilino=0;
        for (Valoracion v: valoraciones) {
            if(v.getTipo().equals(TipoValoracion.ANFITRION)){
                estrellasAnfitrion+=v.getEstrellas();
                numValoracionesAnfitrion++;
            } else{
                estrellasInquilino+=v.getEstrellas();
                numValoracionesInquilino++;
            }
            if(v.getTipo().equals(anfitrion)){
                View view=inflater.inflate(R.layout.valoracion, valorations,false);
                ImageView imageView=view.findViewById(R.id.imageView2);
                TextView nombreUsu=view.findViewById(R.id.nombreUsuario);
                RatingBar rb=view.findViewById(R.id.smallRating);
                TextView comentarioVal=view.findViewById(R.id.comentarioValoración);

                rb.setRating((float) v.getEstrellas());
                comentarioVal.setText(v.getDescripcion());
                nombreUsuarioValoracion(nombreUsu, valorations, view, v, imageView);
            }
        }
        String mediaA="0.0";
        String mediaI="0.0";
        DecimalFormat df = new DecimalFormat("#.0");
        if(numValoracionesAnfitrion>0){
            mediaA=df.format(estrellasAnfitrion/numValoracionesAnfitrion);
            mediaI=df.format(estrellasInquilino/numValoracionesInquilino);
        }

        //TODO Mejorar esta parte, por lo de los nuevos atributos en vivienda de valoraciónMedia
        // A lo mejor tengo el problema de los Double en otro lado
        Constantes.db.child("Vivienda").child(vivienda.getUid()).child("valoraciónMediaA").setValue(-Double.valueOf(mediaA.toString().replace(",", "")));
        Constantes.db.child("Vivienda").child(vivienda.getUid()).child("valoraciónMediaI").setValue(-Double.valueOf(mediaI.toString().replace(",", "")));
        Constantes.db.child("Vivienda").child(vivienda.getUid()).child("valoraciónMediaConjunta").setValue(-(Double.valueOf(mediaA.toString().replace(",", ""))+Double.valueOf(mediaI.toString().replace(",", "")))/2);
        String estrella=new String(Character.toChars(0x2B50));
        TextView anfitrion= (TextView) findViewById(R.id.anfitrionTitlePerfil);
        anfitrion.setText("Anfitrion "+mediaA+" "+estrella);
        TextView inquilino= (TextView) findViewById(R.id.inquilinoTitlePerfil);
        inquilino.setText("Inquilino "+mediaI+" "+estrella);
    }

    private void nombreUsuarioValoracion(TextView nombreUsu, LinearLayout valorations, View view, Valoracion vo, ImageView imageView) {
        Constantes.db.child("Usuario").child(vo.getEmisor())
                .addValueEventListener(new ValueEventListener() {
                    /**
                     * Cualquier consulta orderByChild devuelve una lista de registros, aunque solo haya uno.
                     * Si lo que queremos es buscarlo por su ID debemos hacerlo como en la query de esta funcion
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Usuario u=snapshot.getValue(Usuario.class);
                        nombreUsu.setText(u.getNombreUsuario());
                        valorations.addView(view);
                        imagenValoracion(imageView, u.getUid());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void imagenValoracion(ImageView imageView, String userId) {
        Constantes.db.child("Vivienda").orderByChild("user_id").equalTo(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot v : snapshot.getChildren()){
                            Vivienda vi=v.getValue(Vivienda.class);
                            StorageReference ruta=Constantes.storageRef.child(vi.getImagenes().get(0));
                            final long ONE_MEGABYTE = 1024 * 1024;
                            ruta.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {

                                    //Pasar de bytes a ImageView
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    imageView.setImageBitmap(bitmap);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public void inquilino(View view) {
        Log.i("TAG", "Inquilino");
        anfitrion=TipoValoracion.INQUILINO;
        leerValoraciones();
    }

    public void anfitrion(View view) {
        Log.i("TAG", "Anfitrion");
        anfitrion=TipoValoracion.ANFITRION;
        leerValoraciones();
    }

    private String getArraySringEnString(ArrayList<String> array) {
        String lista="";
        for (int i=0; i< array.size(); i++){
            if(i==array.size()){
                lista+=array.get(i);
            } else{
                lista+=array.get(i)+"\n";
            }
        }
        return lista;
    }

    private ArrayList<String> getStringEnArrayString(String string){
        return new ArrayList<String>(Arrays.asList(string.split("\n")));
    }


    /*
    //Perfil
    public void irPerfil(View view) {
        Intent intent = new Intent(this, Perfil.class);
        startActivity(intent);
    }
    //Chat
    public void irChat(View view) {
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);
    }
    //Busqueda
    public void irBusqueda(View view) {
        Intent intent = new Intent(this, Busqueda.class);
        startActivity(intent);
    }
    //Quedadas
    public void irQuedadas(View view) {
        Intent intent = new Intent(this, Quedadas.class);
        startActivity(intent);
    }
    //Map
    public void irMap(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }*/
}

//TODO : A;adir scroll y dentro el linearlayout para agregar la valoracion
//TODO : PENDIENTE LOGICA DYLAN + OSCAR
//TODO : En clase hablar de los colores para asignarlos en xml colors