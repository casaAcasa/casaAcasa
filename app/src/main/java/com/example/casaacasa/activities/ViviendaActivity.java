package com.example.casaacasa.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Intercambio;
import com.example.casaacasa.modelo.Solicitud;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.modelo.Valoracion;
import com.example.casaacasa.modelo.Vivienda;
import com.example.casaacasa.utils.Constantes;
import com.example.casaacasa.utils.Estado;
import com.example.casaacasa.utils.TipoValoracion;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


public class ViviendaActivity extends AppCompatActivity {
//TODO tengo que poner bien las valoraciones, la media con solo dos decimales me refiero
    private Vivienda vivienda;
    private TipoValoracion anfitrion;
    private LayoutInflater inflater;
    private Intent startIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vivienda);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        anfitrion=TipoValoracion.INQUILINO;
        inflater=LayoutInflater.from(ViviendaActivity.this);
        startIntent=getIntent();
        /**
         * Recoger por el inent el UUID de la vivienda que se ha pulsado y del usuario de la misma. O del objeto vivienda en si. Pero deberé cambiar varias queries para que todo tenga sentida
         * FireBase pasa los datos de forma asíncrona, por lo que los datos no pueden salir del metodo onDataChange
         * Me falta:
         *  Ajustar tamaño imagenes (Esto lo tendrá que hacer el Dylan en la página de perfil)
         *  Botones cambiar valoraciones. No se porque envia para arriba. Tendré que buscar una forma para que no lo haga.
         *  Valoracion media.
         */
        recogerInformacionVivienda();

    }

    private void darTextoALasViews() {
        Constantes.db.child("Usuario")
                .child(vivienda.getUser_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario u=snapshot.getValue(Usuario.class);
                TextView nombreUsuario= findViewById(R.id.NomUsu);
                nombreUsuario.setText(u.getNombreUsuario());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TextView datosVivienda= findViewById(R.id.poblacion);
        datosVivienda.setText(vivienda.getPoblacion().substring(0, vivienda.getPoblacion().length()-1)
                +", "+vivienda.getTipoVivienda().toLowerCase().substring(0, vivienda.getTipoVivienda().length()-1)+", "+vivienda.getMetrosCuadrados()+" m².");

        TextView descripcion= findViewById(R.id.contentDescripción);
        descripcion.setText(vivienda.getDescripcion());

        TextView normas=findViewById(R.id.contentNormas);
        normas.setText(getArraySringEnString(vivienda.getNormas()));
        TextView servicios=findViewById(R.id.contentServicios);
        servicios.setText(getArraySringEnString(vivienda.getServicios()));


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

    private void recogerInformacionVivienda() {
        //Aquí tendré que poner el uid de la vivienda que haya seleccionado
        Constantes.db.child("Vivienda")
                .child(startIntent.getStringExtra("ViviendaID")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vivienda=snapshot.getValue(Vivienda.class);
                darTextoALasViews();
                anadirImagenes();
                leerValoraciones();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void anadirValoraciones(ArrayList<Valoracion> valoraciones){
        LinearLayout valorations=findViewById(R.id.valocinesList);
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
        Constantes.db.child("Vivienda").child(vivienda.getUid()).child("valoraciónMediaA").setValue(-Double.valueOf(mediaA.toString().replace(",", "")));
        Constantes.db.child("Vivienda").child(vivienda.getUid()).child("valoraciónMediaI").setValue(-Double.valueOf(mediaI.toString().replace(",", "")));
        Constantes.db.child("Vivienda").child(vivienda.getUid()).child("valoraciónMediaConjunta").setValue(-(Double.valueOf(mediaA.toString().replace(",", ""))+Double.valueOf(mediaI.toString().replace(",", "")))/2);
        String estrella=new String(Character.toChars(0x2B50));
        TextView anfitrion= (TextView) findViewById(R.id.anfitrionTitle);
        anfitrion.setText("Anfitrion "+mediaA+" "+estrella);
        TextView inquilino= (TextView) findViewById(R.id.inquilinoTitle);
        inquilino.setText("Inquilino "+mediaI+" "+estrella);
    }

    public void valorar(View _){
        /*Date hoy = new Date();
        Date ayer = new Date( hoy.getTime()-86400000);
        Date antesDeAyer= new Date(ayer.getTime()-86400000);
        Intercambio intercambio=new Intercambio("26a08f75-5967-434d-a283-a8b60e70135a", vivienda.getUser_id(), antesDeAyer, ayer);
        Constantes.db.child("Intercambio").child(intercambio.getUid()).setValue(intercambio);
*/
        Constantes.db.child("Intercambio").orderByChild("receptor").equalTo(vivienda.getUser_id())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Intercambio inter=null;
                        for(DataSnapshot i : snapshot.getChildren()){
                            Intercambio intercambio= i.getValue(Intercambio.class);
                            if(intercambio.getEmisor().equals("26a08f75-5967-434d-a283-a8b60e70135a")){
                                inter=intercambio;
                            }
                        }
                        if(inter==null){
                            Constantes.db.child("Intercambio").orderByChild("emisor").equalTo(vivienda.getUser_id())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Intercambio inter2=null;
                                            for(DataSnapshot i : snapshot.getChildren()){
                                                Intercambio intercambio= i.getValue(Intercambio.class);
                                                if(intercambio.getReceptor().equals("26a08f75-5967-434d-a283-a8b60e70135a")){
                                                    inter2=intercambio;
                                                }
                                            }
                                            if(inter2!=null&&inter2.getFechaFinal()
                                                    .before(new Date())){
                                                condicionesParaValorar();
                                            } else Toast.makeText(ViviendaActivity.this,
                                                    "Debes haber acabado un intercambio con el propietario de esta vivienda para poder valorarla.",
                                                    Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        } else{
                            if(inter!=null&&inter.getFechaFinal()
                                    .before(new Date())){
                                condicionesParaValorar();
                            } else Toast.makeText(ViviendaActivity.this,
                                    "Debes haber acabado un intercambio con el propietario de esta vivienda para poder valorarla.",
                                    Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void condicionesParaValorar(){
        Constantes.db.child("Valoracion").orderByChild("receptor").equalTo(vivienda.getUser_id())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int cont=0;
                        for(DataSnapshot v: snapshot.getChildren()){
                            Valoracion valoracion=v.getValue(Valoracion.class);
                            //Si el emisor es igual al usuario logueado
                            if(valoracion.getEmisor().equals("26a08f75-5967-434d-a283-a8b60e70135a")){
                                cont++;
                            }
                        }
                        if(cont>=2){
                            Toast.makeText(ViviendaActivity.this,
                                    "Ya has valorado a esta vivienda.", Toast.LENGTH_SHORT).show();
                        } else enviarValoracion();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void enviarValoracion(){
        AlertDialog.Builder dialog= new AlertDialog.Builder(ViviendaActivity.this);
        dialog.setTitle("Vas a valorar la vivienda de un usuario.");

        View view = inflater.inflate(R.layout.popup_valorar, null);
        dialog.setView(view);

        dialog.setPositiveButton("SIGUIENTE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText et=view.findViewById(R.id.mensajeValoracion);
                RatingBar rb=view.findViewById(R.id.rbpopup);

                Valoracion v=new Valoracion("26a08f75-5967-434d-a283-a8b60e70135a",
                        vivienda.getUser_id(), TipoValoracion.ANFITRION,
                        et.getText().toString(), rb.getRating());
                Constantes.db.child("Valoracion").child(v.getUid()).setValue(v);
                AlertDialog.Builder dialog2= new AlertDialog.Builder(ViviendaActivity.this);
                dialog2.setTitle("Vas a valorar el comportamiento de un usuario en tu vivienda.");

                View view = inflater.inflate(R.layout.popup_valorar, null);
                dialog2.setView(view);

                dialog2.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et=view.findViewById(R.id.mensajeValoracion);
                        RatingBar rb=view.findViewById(R.id.rbpopup);

                        Valoracion v=new Valoracion("26a08f75-5967-434d-a283-a8b60e70135a",
                                vivienda.getUser_id(), TipoValoracion.INQUILINO,
                                et.getText().toString(), rb.getRating());
                        Constantes.db.child("Valoracion").child(v.getUid()).setValue(v);

                        Toast.makeText(ViviendaActivity.this, "Valocarión enviada", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog2.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ViviendaActivity.this, "La valoración no se ha enviado.", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                dialog2.show();
            }
        });

        dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ViviendaActivity.this, "La valoración no se ha enviado.", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void solicitud(View _) {
        if(!vivienda.viviendaNoMostrable()){
            //receptor=Usuario de la vivenda pasado por intent
            Constantes.db.child("Solicitud").orderByChild("receptor").equalTo(vivienda.getUser_id())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int cont=0;
                            for(DataSnapshot s: snapshot.getChildren()){
                                Solicitud solicitud=s.getValue(Solicitud.class);
                                //Si el emisor es igual al usuario logueado
                                if(solicitud!=null&&solicitud.getEmisor().equals("26a08f75-5967-434d-a283-a8b60e70135a")){
                                    cont++;
                                }
                            }
                            if(cont>=1){
                                Toast.makeText(ViviendaActivity.this,
                                        "Ya has enviado una solicitud a esta vivienda.", Toast.LENGTH_SHORT).show();
                            } else enviarSolicitud();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        } else{
            Toast.makeText(ViviendaActivity.this, "Debes rellenar la información de tu vivienda para poder enviar solicitud a otras viviendas", Toast.LENGTH_LONG).show();
        }

    }

    private void enviarSolicitud(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(ViviendaActivity.this);
        dialog.setTitle("Vas ha enviar una solicitud de intercambio.");

        //Hay que crear el view para luego acceder a su contenido
        View view = inflater.inflate(R.layout.popup_enviar_solicitud, null);
        dialog.setView(view);

        dialog.setPositiveButton("ENVIAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText=(EditText) view.findViewById(R.id.contMensaje);

                Toast.makeText(ViviendaActivity.this, "Solicitud enviada."
                        , Toast.LENGTH_SHORT).show();
                //Emisor=Usuario logueado
                //Receptor=Usuario al que el logueado visita
                Solicitud s=new Solicitud("26a08f75-5967-434d-a283-a8b60e70135a",
                        vivienda.getUser_id(), Estado.PENDIENTE, editText.getText().toString());
                Constantes.db.child("Solicitud").child(s.getUid()).setValue(s);
            }
        });

        dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ViviendaActivity.this, "La solicitud no se ha enviado.", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
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

    private void anadirImagenes() {

        LinearLayout gallery = findViewById(R.id.gallery);
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

    public void inquilino(View view) {
        Log.i("TAG", "Inquilino");
        anfitrion=TipoValoracion.INQUILINO;
        leerValoraciones();
        //Que se llame por aqui (aparte de donde está) la funcion de las valoraciones y cambiar el condicionante, que depende del boleano de arriba.
        //Mirar si lo del condicionante puede estar en el onClick o tiene que ser dentro del onDataChange
        //Al ser asíncrono mirar si el boleano se cambia antes de llamar a la función onDataChange
    }

    public void anfitrion(View view) {
        Log.i("TAG", "Anfitrion");
        anfitrion=TipoValoracion.ANFITRION;
        leerValoraciones();
    }

    private void leerValoraciones(){
        Constantes.db.child("Valoracion").orderByChild("receptor").equalTo(vivienda.getUser_id())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LinearLayout valorations=findViewById(R.id.valocinesList);
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

    public void volverAtras(View v){
        Intent intent=new Intent(ViviendaActivity.this, BusquedaActivity.class);
        startActivity(intent);
    }
}
