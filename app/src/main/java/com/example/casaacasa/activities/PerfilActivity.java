package com.example.casaacasa.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.modelo.Valoracion;
import com.example.casaacasa.modelo.Vivienda;
import com.example.casaacasa.utils.Constantes;
import com.example.casaacasa.utils.TipoValoracion;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import android.content.DialogInterface;

public class PerfilActivity extends AppCompatActivity {
    private String IDUsuarioLogeado;
    private Vivienda vivienda;
    private LayoutInflater inflater;
    private TipoValoracion anfitrion;
    private TextView anfitrionTitulo;
    private TextView inquilinnoTitulo;
    private ActivityResultLauncher<Intent> activityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        inflater=LayoutInflater.from(PerfilActivity.this);
        anfitrion=TipoValoracion.INQUILINO;
        anfitrionTitulo=findViewById(R.id.anfitrionTitlePerfil);
        inquilinnoTitulo=findViewById(R.id.inquilinoTitlePerfil);
        tipoDeValoracion();
        IDUsuarioLogeado=Constantes.getIdUsuarioLogueado();


        //TODO A la view le falta el nombre de usuario
        // Decirle al Jorge que cuando junte los tabBars que mire como lo he hecho aquí, y explicárselo
        // O separar las fotos o poner una flechita para hacer que sea escroll
        // Mirar de mantener pulsada una imagen para que se elimine
        //TODO Hacer que las viviendas solo tengan un máximo de 10 fotos. Esto es haciendo un if de si el array tiene un size de 10 que no deje subir más fotos
        // Hacer que la imagen se muestre en un ImageView y meterlo en el layout en vez de recargar otra vez las imágenes porque se bugea
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Uri imageUri = data.getData();
                            Log.i("TAG",imageUri.getLastPathSegment());
                            String nombreImagen="viviendas/"+imageUri.getLastPathSegment()+" "+vivienda.getUid()+".jpg";
                            StorageReference filePath=Constantes.storageRef.child(nombreImagen);

                            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(PerfilActivity.this, "Se ha subido correctamente la foto", Toast.LENGTH_SHORT).show();
                                    vivienda.getImagenes().add(nombreImagen);
                                    Constantes.db.child("Vivienda").child(vivienda.getUid()).child("imagenes").setValue(vivienda.getImagenes());
                                }
                            });


                        }
                    }
                });

        recogerInformacionBBDD();
        seleccionarImagen();
    }

    private void tipoDeValoracion(){
        if(anfitrion.equals(TipoValoracion.INQUILINO)){
            inquilinnoTitulo.setTextColor(Color.WHITE);
            inquilinnoTitulo.setBackgroundColor(Color.parseColor("#7ACE67"));
            anfitrionTitulo.setTextColor(Color.parseColor("#d5d5d5"));
            anfitrionTitulo.setBackgroundColor(Color.parseColor("#31b094"));
        } else{
            anfitrionTitulo.setTextColor(Color.WHITE);
            anfitrionTitulo.setBackgroundColor(Color.parseColor("#7ACE67"));
            inquilinnoTitulo.setTextColor(Color.parseColor("#d5d5d5"));
            inquilinnoTitulo.setBackgroundColor(Color.parseColor("#31b094"));
        }
    }

    private void seleccionarImagen() {
        Button seleccionar=findViewById(R.id.seleccionarImagen);
        seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
            }
        });

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
                if(!vivienda.getImagenes().isEmpty()){
                    anadirImagenes();
                }
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
        if(!vivienda.getNormas().isEmpty()){
            normas.setText(getArraySringEnString(vivienda.getNormas()));
        }

        TextView servicios=findViewById(R.id.serviciosPerfil);
        if(!vivienda.getServicios().isEmpty()){
            servicios.setText(getArraySringEnString(vivienda.getServicios()));
        }


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
        gallery.removeAllViews();
        for (int i=0; i<vivienda.getImagenes().size(); i++){
            StorageReference ruta=Constantes.storageRef.child(vivienda.getImagenes().get(i));
            View view = inflater.inflate(R.layout.imagen, gallery, false);
            ImageView imageView=view.findViewById(R.id.imageView);

            final long ONE_MEGABYTE = 1024 * 1024 *5;
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
        tipoDeValoracion();
        leerValoraciones();
    }

    public void anfitrion(View view) {
        Log.i("TAG", "Anfitrion");
        anfitrion=TipoValoracion.ANFITRION;
        tipoDeValoracion();
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

    public void ajustes(View _){
        AlertDialog alertDialog = new AlertDialog.Builder(PerfilActivity.this).create();

        View view = inflater.inflate(R.layout.menu_opciones, null);
        alertDialog.setView(view);
        alertDialog.show();

        TextView cambiarDatosUsuario=view.findViewById(R.id.datosUsuPerf);
        TextView cerrarSesion=view.findViewById(R.id.cerrarSesionPerf);

        cambiarDatosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PerfilActivity.this, DatosUsuario1Activity.class);
                startActivity(intent);
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PerfilActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });
    }

    public void irCambiosDatosVivienda (View v){
        Intent intent=new Intent(PerfilActivity.this, DatosViviendaActivity.class);
        intent.putExtra("ViviendaID", vivienda.getUid());
        startActivity(intent);
    }
    public void irBusqueda (View v){
        Intent intent=new Intent(PerfilActivity.this, BusquedaActivity.class);
        startActivity(intent);
    }
    public void irChat (View v){
        Intent intent=new Intent(PerfilActivity.this, ChatActivity.class);
        startActivity(intent);
    }

    public void irQuedadas (View v){
        AlertDialog.Builder dialog= new AlertDialog.Builder(PerfilActivity.this);
        dialog.setTitle("Pagina no funcional.");
        View view = inflater.inflate(R.layout.popup_eliminar_chat, null);
        dialog.setView(view);
        dialog.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {

            }
        });
        dialog.show();
    }

    public void irMap (View v){
        AlertDialog.Builder dialog= new AlertDialog.Builder(PerfilActivity.this);
        dialog.setTitle("Pagina no funcional.");
        View view = inflater.inflate(R.layout.popup_eliminar_chat, null);
        dialog.setView(view);
        dialog.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {

            }
        });
        dialog.show();
    }

    public void irCambiosDatos (View v){
        Intent intent=new Intent(PerfilActivity.this, DatosViviendaActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(PerfilActivity.this, BusquedaActivity.class);
        startActivity(intent);
    }
}

