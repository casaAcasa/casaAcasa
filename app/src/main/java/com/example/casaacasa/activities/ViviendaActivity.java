package com.example.casaacasa.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.util.ArrayList;
import java.util.Date;


public class ViviendaActivity extends AppCompatActivity {
    private Vivienda vivienda;
    private TipoValoracion anfitrion;
    private TextView anfitrionTitulo;
    private TextView inquilinnoTitulo;
    private LayoutInflater inflater;
    private Intent startIntent;
    private String idUsuarioLogueado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vivienda);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        anfitrion = TipoValoracion.INQUILINO;
        anfitrionTitulo=findViewById(R.id.anfitrionTitle);
        inquilinnoTitulo=findViewById(R.id.inquilinoTitle);
        tipoDeValoracion();
        inflater = LayoutInflater.from(ViviendaActivity.this);
        startIntent = getIntent();

        idUsuarioLogueado = Constantes.getIdUsuarioLogueado();
        recogerInformacionVivienda();

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

    private void darTextoALasViews() {
        Constantes.db.child("Usuario")
                .child(vivienda.getUser_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Usuario u = snapshot.getValue(Usuario.class);
                        TextView nombreUsuario = findViewById(R.id.NomUsu);
                        nombreUsuario.setText(u.getNombreUsuario());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        TextView datosVivienda = findViewById(R.id.poblacion);
        datosVivienda.setText(vivienda.getPoblacion()
                + ", " + vivienda.getTipoVivienda().toLowerCase().substring(0, vivienda.getTipoVivienda().length() - 1) + ", " + vivienda.getMetrosCuadrados() + " m².");

        TextView descripcion = findViewById(R.id.contentDescripción);
        descripcion.setText(vivienda.getDescripcion());

        TextView normas = findViewById(R.id.contentNormas);
        normas.setText(getArraySringEnString(vivienda.getNormas()));
        TextView servicios = findViewById(R.id.contentServicios);
        servicios.setText(getArraySringEnString(vivienda.getServicios()));


    }

    private String getArraySringEnString(ArrayList<String> array) {
        String lista = "";
        for (int i = 0; i < array.size(); i++) {
            if (i == array.size()) {
                lista += array.get(i);
            } else {
                lista += array.get(i) + "\n";
            }
        }
        return lista;
    }

    private void recogerInformacionVivienda() {
        Constantes.db.child("Vivienda")
                .child(startIntent.getStringExtra("ViviendaID")).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        vivienda = snapshot.getValue(Vivienda.class);
                        darTextoALasViews();
                        anadirImagenes();
                        leerValoraciones();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void anadirValoraciones(ArrayList<Valoracion> valoraciones) {
        LinearLayout valorations = findViewById(R.id.valocinesList);
        valorations.removeAllViewsInLayout();
        double estrellasAnfitrion = 0;
        int numValoracionesAnfitrion = 0;
        double estrellasInquilino = 0;
        int numValoracionesInquilino = 0;
        for (Valoracion v : valoraciones) {
            if (v.getTipo().equals(TipoValoracion.ANFITRION)) {
                estrellasAnfitrion += v.getEstrellas();
                numValoracionesAnfitrion++;
            } else {
                estrellasInquilino += v.getEstrellas();
                numValoracionesInquilino++;
            }
            if (v.getTipo().equals(anfitrion)) {
                View view = inflater.inflate(R.layout.valoracion, valorations, false);
                ImageView imageView = view.findViewById(R.id.imageView2);
                TextView nombreUsu = view.findViewById(R.id.nombreUsuario);
                RatingBar rb = view.findViewById(R.id.smallRating);
                TextView comentarioVal = view.findViewById(R.id.comentarioValoracion);

                rb.setRating((float) v.getEstrellas());
                comentarioVal.setText(v.getDescripcion());
                nombreUsuarioValoracion(nombreUsu, valorations, view, v, imageView);
            }
        }
        String mediaA = "0.0";
        String mediaI = "0.0";
        DecimalFormat df = new DecimalFormat("#.0");
        if (numValoracionesAnfitrion > 0) {
            mediaA = df.format(estrellasAnfitrion / numValoracionesAnfitrion);
            mediaI = df.format(estrellasInquilino / numValoracionesInquilino);
        }
        if(mediaI.equals("NaN")){
            mediaI="0.0";
        }
        if(mediaA.equals("NaN")){
           mediaA="0.0";
        }

        Constantes.db.child("Vivienda").child(vivienda.getUid()).child("valoracionMediaA").setValue(-Double.valueOf(mediaA.toString().replace(",", "")));
        Constantes.db.child("Vivienda").child(vivienda.getUid()).child("valoracionMediaI").setValue(-Double.valueOf(mediaI.toString().replace(",", "")));
        Constantes.db.child("Vivienda").child(vivienda.getUid()).child("valoracionMediaConjunta").setValue(-(Double.valueOf(mediaA.toString().replace(",", "")) + Double.valueOf(mediaI.toString().replace(",", ""))) / 2);
        String estrella = new String(Character.toChars(0x2B50));
        TextView anfitrion = (TextView) findViewById(R.id.anfitrionTitle);
        anfitrion.setText("Anfitrion " + mediaA + " " + estrella);
        TextView inquilino = (TextView) findViewById(R.id.inquilinoTitle);
        inquilino.setText("Inquilino " + mediaI + " " + estrella);
    }

    public void valorar(View _) {
        Constantes.db.child("Intercambio").orderByChild("receptor").equalTo(vivienda.getUser_id())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Intercambio inter = null;
                        for (DataSnapshot i : snapshot.getChildren()) {
                            Intercambio intercambio = i.getValue(Intercambio.class);
                            if (intercambio.getEmisor().equals(idUsuarioLogueado)) {
                                inter = intercambio;
                            }
                        }
                        if (inter == null) {
                            Constantes.db.child("Intercambio").orderByChild("emisor").equalTo(vivienda.getUser_id())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Intercambio inter2 = null;
                                            for (DataSnapshot i : snapshot.getChildren()) {
                                                Intercambio intercambio = i.getValue(Intercambio.class);
                                                if (intercambio.getReceptor().equals(idUsuarioLogueado)) {
                                                    inter2 = intercambio;
                                                }
                                            }
                                            if (inter2 != null && inter2.getFechaFinal()
                                                    .before(new Date())) {
                                                condicionesParaValorar();
                                            } else Toast.makeText(ViviendaActivity.this,
                                                    "Debes haber acabado un intercambio con el propietario de esta vivienda para poder valorarla.",
                                                    Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        } else {
                            if (inter != null && inter.getFechaFinal()
                                    .before(new Date())) {
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

    private void condicionesParaValorar() {
        Constantes.db.child("Valoracion").orderByChild("receptor").equalTo(vivienda.getUser_id())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int cont = 0;
                        for (DataSnapshot v : snapshot.getChildren()) {
                            Valoracion valoracion = v.getValue(Valoracion.class);
                            if (valoracion.getEmisor().equals(idUsuarioLogueado)) {
                                cont++;
                            }
                        }
                        if (cont >= 2) {
                            Toast.makeText(ViviendaActivity.this,
                                    "Ya has valorado a esta vivienda.", Toast.LENGTH_SHORT).show();
                        } else enviarValoracion();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void enviarValoracion() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ViviendaActivity.this);
        dialog.setTitle("Vas a valorar al usuario como inquilino.");

        View view = inflater.inflate(R.layout.popup_valorar, null);
        dialog.setView(view);

        dialog.setPositiveButton("SIGUIENTE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText et = view.findViewById(R.id.mensajeValoracion);
                RatingBar rb = view.findViewById(R.id.rbpopup);
                if(rb.getRating()==0||et.getText().toString().trim().equals("")){
                    Toast.makeText(ViviendaActivity.this, "Debes dar un número de estrellas y poner un mensaje para enviar la valoración", Toast.LENGTH_LONG).show();
                }else{
                    Valoracion v = new Valoracion(idUsuarioLogueado,
                            vivienda.getUser_id(), TipoValoracion.INQUILINO,
                            et.getText().toString(), rb.getRating());

                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(ViviendaActivity.this);
                    dialog2.setTitle("Vas a valorar al usuario como anftrión.");

                    View view = inflater.inflate(R.layout.popup_valorar, null);
                    dialog2.setView(view);

                    dialog2.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText et = view.findViewById(R.id.mensajeValoracion);
                            RatingBar rb = view.findViewById(R.id.rbpopup);

                            if(rb.getRating()==0||et.getText().toString().trim().equals("")){
                                Toast.makeText(ViviendaActivity.this, "Debes dar un número de estrellas y poner un mensaje para enviar la valoración", Toast.LENGTH_LONG).show();
                            }else{
                                Valoracion v2 = new Valoracion(idUsuarioLogueado,
                                        vivienda.getUser_id(), TipoValoracion.ANFITRION,
                                        et.getText().toString(), rb.getRating());
                                Constantes.db.child("Valoracion").child(v.getUid()).setValue(v);
                                Constantes.db.child("Valoracion").child(v2.getUid()).setValue(v2);

                                Toast.makeText(ViviendaActivity.this, "Valorarión enviada", Toast.LENGTH_SHORT).show();
                            }
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
        Constantes.db.child("Vivienda").orderByChild("user_id").equalTo(idUsuarioLogueado)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Vivienda viviendaUsuarioLogueaado=null;
                        for(DataSnapshot v: snapshot.getChildren()){
                            viviendaUsuarioLogueaado= v.getValue(Vivienda.class);
                        }
                        if (!viviendaUsuarioLogueaado.viviendaNoMostrable()) {
                            Constantes.db.child("Solicitud").orderByChild("receptor").equalTo(vivienda.getUser_id())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            int cont = 0;
                                            for (DataSnapshot s : snapshot.getChildren()) {
                                                Solicitud solicitud = s.getValue(Solicitud.class);
                                                if (solicitud != null && solicitud.getEmisor().equals(idUsuarioLogueado)) {
                                                    cont++;
                                                }
                                            }
                                            if (cont >= 1) {
                                                Toast.makeText(ViviendaActivity.this,
                                                        "Ya has enviado una solicitud a esta vivienda.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Constantes.db.child("Solicitud").orderByChild("emisor").equalTo(vivienda.getUser_id())
                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                int cont = 0;
                                                                for (DataSnapshot s : snapshot.getChildren()) {
                                                                    Solicitud solicitud = s.getValue(Solicitud.class);
                                                                    if (solicitud != null && solicitud.getReceptor().equals(idUsuarioLogueado)) {
                                                                        cont++;
                                                                    }
                                                                }
                                                                if (cont >= 1) {
                                                                    Toast.makeText(ViviendaActivity.this,
                                                                            "Ya has recivido una solicitud de esta vivienda.", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    enviarSolicitud();
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        } else {
                            Toast.makeText(ViviendaActivity.this, "Debes rellenar la información de tu vivienda para poder enviar solicitud a otras viviendas", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void enviarSolicitud() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ViviendaActivity.this);
        dialog.setTitle("Vas ha enviar una solicitud de intercambio.");

        View view = inflater.inflate(R.layout.popup_enviar_solicitud, null);
        dialog.setView(view);

        dialog.setPositiveButton("ENVIAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText = (EditText) view.findViewById(R.id.contMensaje);
                if(editText.getText().toString().trim().equals("")){
                    Toast.makeText(ViviendaActivity.this, "Debes escribir un mensaje para enviar la solicitud", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(ViviendaActivity.this, "Solicitud enviada."
                        , Toast.LENGTH_SHORT).show();
                Solicitud s = new Solicitud(idUsuarioLogueado,
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
                        for (DataSnapshot v : snapshot.getChildren()) {
                            Vivienda vi = v.getValue(Vivienda.class);
                            StorageReference ruta = Constantes.storageRef.child(vi.getImagenes().get(0));
                            final long ONE_MEGABYTE = 1024 * 1024;
                            ruta.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {

                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    imageView.setImageBitmap(bitmap);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
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
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Usuario u = snapshot.getValue(Usuario.class);
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
        for (int i = 0; i < vivienda.getImagenes().size(); i++) {
            StorageReference ruta = Constantes.storageRef.child(vivienda.getImagenes().get(i));
            View view = inflater.inflate(R.layout.imagen, gallery, false);
            ImageView imageView = view.findViewById(R.id.imageView);

            final long ONE_MEGABYTE = 1024 * 1024;
            ruta.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);
                    gallery.addView(view);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        }
    }

    public void inquilino(View view) {
        anfitrion = TipoValoracion.INQUILINO;
        tipoDeValoracion();
        leerValoraciones();
    }

    public void anfitrion(View view) {
        anfitrion = TipoValoracion.ANFITRION;
        tipoDeValoracion();
        leerValoraciones();
    }

    private void leerValoraciones() {
        Constantes.db.child("Valoracion").orderByChild("receptor").equalTo(vivienda.getUser_id())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LinearLayout valorations = findViewById(R.id.valocinesList);
                        valorations.removeAllViews();
                        ArrayList<Valoracion> valoraciones = new ArrayList<>();
                        for (DataSnapshot v : snapshot.getChildren()) {
                            Valoracion vo = v.getValue(Valoracion.class);
                            valoraciones.add(vo);
                        }
                        anadirValoraciones(valoraciones);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
