package com.example.casaacasa.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

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
        //TODO para los botones de filtro poner interruptores en los botones para qube si unmo está pulsado no se pueda pulsar el otro y biceversa

    }

    private void viviendasRecomendadas() {
        Constantes.db.child("Vivienda").orderByChild("valoracionMediaConjunta").limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.listaViviendas);
                linearLayout.removeAllViewsInLayout();

                for (DataSnapshot vi : snapshot.getChildren()) {
                    Vivienda vivienda = vi.getValue(Vivienda.class);
                    rellenarVivienadas(vivienda, linearLayout);
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

                for (DataSnapshot vi : snapshot.getChildren()) {
                    Vivienda vivienda = vi.getValue(Vivienda.class);
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
                    }
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

        descripcion.setText(vivienda.getDescripcion());
        datosVivienda.setText(vivienda.getPoblacion() + ", " +
                vivienda.getTipoVivienda().toLowerCase() + ", " +
                vivienda.getMetrosCuadrados() + " m².");
        linearLayout.addView(v);
        enivarPaginaVivienda(v, vivienda);
    }

    private void enivarPaginaVivienda(View v, Vivienda vivienda) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Le podria pasar toda la vivienda para tener ya la mayor parte de la información. Mirar si ve bien hacerlo
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

        //TODO me falta recoger el número de los destos para la búsqueda
        View view = inflater.inflate(R.layout.filtros, null);
        alertDialog.setView(view);
        TextView ciudad = view.findViewById(R.id.ciudad);
        TextView pueblo = view.findViewById(R.id.pueblo);
        btnCiudad(ciudad, pueblo);
        btnPueblo(pueblo, ciudad);
        TextView casa = view.findViewById(R.id.casa);
        TextView piso = view.findViewById(R.id.piso);
        TextView apartamento = view.findViewById(R.id.apartamento);
        btnCasa(casa, piso, apartamento);
        btnPiso(piso, apartamento, casa);
        btnApartamento(apartamento, casa, piso);
        Button aplicar = view.findViewById(R.id.aplicar);
        Button borrar = view.findViewById(R.id.borrar);
        EditText numH = view.findViewById(R.id.numHabitaciones);
        EditText m2 = view.findViewById(R.id.metrosCuadrados);
        alertDialog.show();
        btnAplicar(aplicar, alertDialog, numH, m2);
        btnBorrar(borrar, alertDialog);

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

            }
        });
    }

    private void btnAplicar(Button aplicar, AlertDialog alertDialog, EditText numH, EditText m2) {
        aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BusquedaActivity.this, casaPisoApartamento + " " + ciudadPueblo, Toast.LENGTH_SHORT).show();
                //No se si hacer dissmis también
                //TODO Tendré que hacer los filtros globales o mirar como pasar parámetros a una función sin que sean obligatorios, para no tener que meterlos en las otras llamadas del método
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
}