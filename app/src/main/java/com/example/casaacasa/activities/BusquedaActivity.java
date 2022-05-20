package com.example.casaacasa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Usuario;
import com.example.casaacasa.modelo.Vivienda;
import com.example.casaacasa.utils.Constantes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class BusquedaActivity extends AppCompatActivity {
    private LayoutInflater inflater;
    private String ciudadPueblo;
    private String casaPisoApartamento;
    private SearchView searchView;
    private int numHabitaciones;
    private int metrosCuadrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        inflater = LayoutInflater.from(BusquedaActivity.this);
        ciudadPueblo = ".";
        casaPisoApartamento = ".";
        numHabitaciones = 0;
        metrosCuadrados = 0;

        //TODO Podria hacer una tabla de dos columnas en vez del Linear layout. El número de filas seria = al número de viviendas / entre columnas
        // f=v/c; Redondeado al mayor, 12,3==13;
        //TODO Buscar como hacer una tabla variable (GridView, creo);

        //TODO Para que la casa sea visible para los otros usuarios que sea obligatorio una foto mínimo, los otros datos los podemos subir vacios, o con espacios mejor dicho

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buscarViviendas(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        viviendasRecomendadas();

    }

    private void viviendasRecomendadas() {
        Constantes.db.child("Vivienda").orderByChild("valoracionMediaConjunta").limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.listaViviendas);
                linearLayout.removeAllViewsInLayout();

                for (DataSnapshot vi : snapshot.getChildren()) {
                    Vivienda vivienda = vi.getValue(Vivienda.class);
                    if(!vivienda.getUser_id().equals("26a08f75-5967-434d-a283-a8b60e70135a")){
                        rellenarVivienadas(vivienda, linearLayout);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void buscarViviendas(String query) {

        Query q;
        if (query.equals("")) q = Constantes.db.child("Vivienda");
        else {
            if (query.contains(",")) {
                q = Constantes.db.child("Vivienda").orderByChild("direccionExacta").equalTo(query);
            } else {
                q = Constantes.db.child("Vivienda").orderByChild("poblacion").equalTo(query);
            }
        }
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.listaViviendas);
                linearLayout.removeAllViewsInLayout();
                TextView listaFiltros=(TextView) findViewById(R.id.listaFiltros);
                TextView titulo=(TextView) findViewById(R.id.recomendados);
                titulo.setText("Resultado de la búsqueda");

                //TODO No se puede buscar algo vacio, mirar de cambiarlo
                // Depende los filtros que pongo se cambiam en el texto o no. Esto puede pasar por lo de los filtros, que se rellenan en el else

                String cp="";
                String cpa="";
                String nh="";
                String m2="";
                if(!ciudadPueblo.equals(".")) cp=" "+ciudadPueblo.substring(0, ciudadPueblo.length()-1);
                if(!casaPisoApartamento.equals(".")) cpa=" "+casaPisoApartamento.substring(0, casaPisoApartamento.length()-1);
                if(numHabitaciones!=0) nh=" "+numHabitaciones+" hab.";
                if(metrosCuadrados!=0) m2=" "+metrosCuadrados+" m²";

                listaFiltros.setText("Filtros en uso:"+cp+cpa+nh+m2);

                for (DataSnapshot vi : snapshot.getChildren()) {
                    Vivienda vivienda = vi.getValue(Vivienda.class);
                    if(!vivienda.getUser_id().equals("26a08f75-5967-434d-a283-a8b60e70135a")){//Usuario logueado
                        if (numHabitaciones != 0 && metrosCuadrados != 0) {
                            if (vivienda.getTipoVivienda().contains(casaPisoApartamento.toUpperCase())
                                    && vivienda.getTipoPoblacion().contains(ciudadPueblo)
                                    && vivienda.getNumHabitaciones() == numHabitaciones
                                    && vivienda.getMetrosCuadrados() == metrosCuadrados) {
                                rellenarVivienadas(vivienda, linearLayout);
                            }
                        } else if (numHabitaciones != 0 && metrosCuadrados == 0) {
                            if (vivienda.getTipoVivienda().contains(casaPisoApartamento.toUpperCase())
                                    && vivienda.getTipoPoblacion().contains(ciudadPueblo)
                                    && vivienda.getNumHabitaciones() == numHabitaciones) {
                                rellenarVivienadas(vivienda, linearLayout);
                            }
                        } else if (numHabitaciones == 0 && metrosCuadrados != 0) {
                            if (vivienda.getTipoVivienda().contains(casaPisoApartamento.toUpperCase())
                                    && vivienda.getTipoPoblacion().contains(ciudadPueblo)
                                    && vivienda.getMetrosCuadrados() == metrosCuadrados) {
                                rellenarVivienadas(vivienda, linearLayout);
                            }
                        } else {
                            Log.i("TAG", vivienda.getTipoVivienda().contains(casaPisoApartamento.toUpperCase())
                                    +" "+vivienda.getTipoVivienda()+", "+casaPisoApartamento.toUpperCase());
                            Log.i("TAG", vivienda.getTipoPoblacion().contains(ciudadPueblo)
                                    +" "+vivienda.getTipoPoblacion()+", "+ ciudadPueblo);
                            if (vivienda.getTipoVivienda().contains(casaPisoApartamento.toUpperCase())
                                    && vivienda.getTipoPoblacion().contains(ciudadPueblo)) {
                                rellenarVivienadas(vivienda, linearLayout);
                            }
                            //TODO Acordarme de quitar el . en los sitios que haga falta
                            // Ya funciona, pero tengo que cambiar recomendados por resultados de búsqueda y cuando no hayan viviendas poner, donde está el scroll de viviendas, un mensaje diciendo que no hay viviendas
                            // Cuando quiero borrar los filtros despues de hacer una búsqueda no me aparecen marcados o con sus valores. Mejorar esto.
                            // A la hora de recoger las casas de la BBDD debor evitar el coger la casa del usuario logueado
                        }
                    }
                }
                if(linearLayout.getChildCount()==0){
                    TextView textView=new TextView(BusquedaActivity.this);
                    textView.setText("No se han encontrado viviendas.");
                    linearLayout.addView(textView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void rellenarVivienadas(Vivienda vivienda, LinearLayout linearLayout) {
        View v = inflater.inflate(R.layout.vivienda_busqueda, linearLayout, false);
        nombreUsuario(v, vivienda);
        recogerImagen(v, vivienda);

        TextView descripcion = v.findViewById(R.id.vbDescripcion);
        TextView datosVivienda = v.findViewById(R.id.vbDatosVivienda);
        TextView anfitrion=v.findViewById(R.id.a);
        TextView inquilino=v.findViewById(R.id.i);

        descripcion.setText(vivienda.getDescripcion());
        datosVivienda.setText(vivienda.getPoblacion() + ", " +
                vivienda.getTipoVivienda().toLowerCase().substring(0, vivienda.getTipoVivienda().length()-1) + ", " +
                vivienda.getMetrosCuadrados() + " m².");
        String estrella=new String(Character.toChars(0x2B50));
        double va=vivienda.getValoracionMediaA();
        double vi=vivienda.getValoracionMediaI();
        if(va!=0) va=va*-1;
        if(vi!=0) vi=vi*-1;
        anfitrion.setText("A "+(va)+" "+estrella);
        inquilino.setText("I "+(vi)+" "+estrella);

        linearLayout.addView(v);
        enivarPaginaVivienda(v, vivienda);
    }

    private void enivarPaginaVivienda(View v, Vivienda vivienda) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusquedaActivity.this, ViviendaActivity.class);
                intent.putExtra("ViviendaID", vivienda.getUid());
                startActivity(intent);
            }
        });
    }

    private void recogerImagen(View v, Vivienda vivienda) {
        ImageView imageView = v.findViewById(R.id.vbImagen);
        Constantes.db.child("Vivienda").child(vivienda.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Vivienda vi = snapshot.getValue(Vivienda.class);
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
                                // Handle any errors
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void nombreUsuario(View v, Vivienda vivienda) {
        Constantes.db.child("Usuario")
                .child(vivienda.getUser_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario u = snapshot.getValue(Usuario.class);
                TextView nombre = v.findViewById(R.id.vbNombreUsuario);
                nombre.setText(u.getNombreUsuario());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void mostrarFiltros(View v) {
        AlertDialog alertDialog = new AlertDialog.Builder(BusquedaActivity.this).create();

        View view = inflater.inflate(R.layout.filtros, null);
        alertDialog.setView(view);
        TextView ciudad = view.findViewById(R.id.ciudad);
        TextView pueblo = view.findViewById(R.id.pueblo);
        TextView casa = view.findViewById(R.id.casa);
        TextView piso = view.findViewById(R.id.piso);
        TextView apartamento = view.findViewById(R.id.apartamento);
        EditText numH = view.findViewById(R.id.numHabitaciones);
        EditText m2 = view.findViewById(R.id.metrosCuadrados);
        Button aplicar = view.findViewById(R.id.aplicar);
        Button borrar = view.findViewById(R.id.borrar);

        filtrosAplicados(ciudad, pueblo, casa, piso, apartamento, numH, m2);

        btnCiudad(ciudad, pueblo);
        btnPueblo(pueblo, ciudad);
        btnCasa(casa, piso, apartamento);
        btnPiso(piso, apartamento, casa);
        btnApartamento(apartamento, casa, piso);

        alertDialog.show();
        btnAplicar(aplicar, alertDialog, numH, m2);
        btnBorrar(borrar, alertDialog);

    }

    private void filtrosAplicados(TextView ciudad, TextView pueblo, TextView casa, TextView piso, TextView apartamento, EditText numH, EditText m2) {
        if(ciudadPueblo.equals("ciudad.")){
           ciudad.setBackgroundColor(Color.GREEN);
        } else if(ciudadPueblo.equals("pueblo.")){
            pueblo.setBackgroundColor(Color.GREEN);
        } else{
            ciudad.setBackgroundColor(Color.WHITE);
            pueblo.setBackgroundColor(Color.WHITE);
        }

        if(casaPisoApartamento.equals("casa.")){
            casa.setBackgroundColor(Color.GREEN);
        } else if(casaPisoApartamento.equals("piso.")){
            piso.setBackgroundColor(Color.GREEN);
        } else if(casaPisoApartamento.equals("apartamento.")){
            apartamento.setBackgroundColor(Color.GREEN);
        } else{
            casa.setBackgroundColor(Color.WHITE);
            piso.setBackgroundColor(Color.WHITE);
            apartamento.setBackgroundColor(Color.WHITE);
        }

        String numH2="";
        String m22="";
        if(numHabitaciones!=0) numH2=""+numHabitaciones;
        if(metrosCuadrados!=0) m22=""+metrosCuadrados;
        numH.setText(numH2, TextView.BufferType.EDITABLE);
        m2.setText(m22, TextView.BufferType.EDITABLE);
    }

    private void btnBorrar(Button borrar, AlertDialog alertDialog) {
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                ciudadPueblo = ".";
                casaPisoApartamento = ".";
                numHabitaciones = 0;
                metrosCuadrados = 0;
                TextView listaFiltros=(TextView) findViewById(R.id.listaFiltros);
                listaFiltros.setText("Filtros en uso:");
                String q = searchView.getQuery().toString();
                buscarViviendas(q);

            }
        });
    }

    private void btnAplicar(Button aplicar, AlertDialog alertDialog, EditText numH, EditText m2) {
        aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!numH.getText().toString().equals(""))
                    numHabitaciones = Integer.parseInt(numH.getText().toString());
                if (!m2.getText().toString().equals(""))
                    metrosCuadrados = Integer.parseInt(m2.getText().toString());
                String q = searchView.getQuery().toString();
                buscarViviendas(q);
                alertDialog.dismiss();
            }
        });
    }

    private void btnCiudad(TextView ciudad, TextView pueblo) {
        ciudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ciudadPueblo.equals("ciudad.")) {
                    ciudadPueblo = "ciudad.";
                    ciudad.setBackgroundColor(Color.GREEN);
                    pueblo.setBackgroundColor(Color.WHITE);
                } else {
                    ciudadPueblo = ".";
                    ciudad.setBackgroundColor(Color.WHITE);
                }
            }
        });
    }

    private void btnPueblo(TextView pueblo, TextView ciudad) {
        pueblo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ciudadPueblo.equals("pueblo.")) {
                    ciudadPueblo = "pueblo.";
                    pueblo.setBackgroundColor(Color.GREEN);
                    ciudad.setBackgroundColor(Color.WHITE);
                } else {
                    ciudadPueblo = ".";
                    pueblo.setBackgroundColor(Color.WHITE);
                }
            }
        });
    }

    private void btnCasa(TextView casa, TextView piso, TextView apartamento) {
        casa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!casaPisoApartamento.equals("casa.")) {
                    casaPisoApartamento = "casa.";
                    casa.setBackgroundColor(Color.GREEN);
                    piso.setBackgroundColor(Color.WHITE);
                    apartamento.setBackgroundColor(Color.WHITE);
                } else {
                    casaPisoApartamento = ".";
                    casa.setBackgroundColor(Color.WHITE);
                }
            }
        });
    }

    private void btnPiso(TextView piso, TextView apartamento, TextView casa) {
        piso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!casaPisoApartamento.equals("piso.")) {
                    casaPisoApartamento = "piso.";
                    piso.setBackgroundColor(Color.GREEN);
                    casa.setBackgroundColor(Color.WHITE);
                    apartamento.setBackgroundColor(Color.WHITE);
                } else {
                    casaPisoApartamento = ".";
                    piso.setBackgroundColor(Color.WHITE);
                }
            }
        });

    }

    private void btnApartamento(TextView apartamento, TextView casa, TextView piso) {
        apartamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!casaPisoApartamento.equals("apartamento.")) {
                    casaPisoApartamento = "apartamento.";
                    apartamento.setBackgroundColor(Color.GREEN);
                    piso.setBackgroundColor(Color.WHITE);
                    casa.setBackgroundColor(Color.WHITE);
                } else {
                    casaPisoApartamento = ".";
                    apartamento.setBackgroundColor(Color.WHITE);
                }
            }
        });

    }

    public void irPerfil(View v){
        Intent intent=new Intent(BusquedaActivity.this, PerfilActivity.class);
        startActivity(intent);
    }

    public void irChat (View v){
        Intent intent=new Intent(BusquedaActivity.this, ChatActivity.class);
        startActivity(intent);
    }

    public void irQuedadas (View v){
        AlertDialog.Builder dialog= new AlertDialog.Builder(BusquedaActivity.this);
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
        AlertDialog.Builder dialog= new AlertDialog.Builder(BusquedaActivity.this);
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

}