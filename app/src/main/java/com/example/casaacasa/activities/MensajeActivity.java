package com.example.casaacasa.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Intercambio;
import com.example.casaacasa.modelo.Mensaje;
import com.example.casaacasa.modelo.MensajeDeInterambio;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

import java.util.Calendar;

public class MensajeActivity extends AppCompatActivity {
    private ImageView calendario;
    private TextView inicio, finali, hInicio, hFinal;
    private Button fechaInicio, fechaFinal, horaInicio, horaFinal;
    private int dia, mes, ano, day, mon, ye, hora, minutos;

    private Intent startIntent;
    private TreeSet<Mensaje> mensajes;
    private LayoutInflater inflater;

    private String IDUsuarioLogueado;

    //TODO Falta mejorar la parte de los mensajes de intercambio porque va mal.
    // No se actualiza cuando se pulsa a un boton
    // No se prohibw enviar otro destos si ya se ha enviado uno con respuesta afirmativa o sin respuesta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajeria);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        IDUsuarioLogueado=Constantes.getIdUsuarioLogueado();

        mensajes=new TreeSet<Mensaje>();
        inflater=LayoutInflater.from(MensajeActivity.this);
        startIntent=getIntent();

        rellenarDatosUsuario();
        obtenerMensajesUsuarioLogueado();

        calendario = (ImageView) findViewById(R.id.iconCalendario);


        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(MensajeActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MensajeActivity.this);
                View view = layoutInflater.inflate(R.layout.popup_calendario, null);
                fechaInicio = (Button) view.findViewById(R.id.fechaInicio);
                fechaFinal = (Button) view.findViewById(R.id.fechaFinal);
                horaInicio = (Button) view.findViewById(R.id.horaInicio);
                horaFinal = (Button) view.findViewById(R.id.horaFinal);
                hInicio = (TextView) view.findViewById(R.id.salidaHoraInicio);
                hFinal = (TextView) view.findViewById(R.id.salidaHoraFinal);
                inicio = (TextView) view.findViewById(R.id.salidaFechaInicio);
                finali = (TextView) view.findViewById(R.id.salidaFechaFinal);
                dialog.setView(view);
                dialog.setPositiveButton("Listo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enviarMensajeDeIntercambio();
                        dialog.cancel();
                    }
                });
                dialog.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }

    private void enviarMensajeDeIntercambio(){
        if(inicio.getText().toString().equals("")||
                finali.getText().toString().equals("")||
                hInicio.getText().toString().equals("")||
                hFinal.getText().toString().equals("")){
            Toast.makeText(MensajeActivity.this, "Debes completar todos los campos para enviar el mensaje de intercambio", Toast.LENGTH_SHORT).show();
        } else{
            String mensaje="¿Deseas realizar el intercambio en este periodo de tiempo?";
            Date fechaDeIinicio= null;
            try {
                fechaDeIinicio = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(inicio.getText().toString()+" "+hInicio.getText().toString());
                Date fechaDeFinalizacion= new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(finali.getText().toString()+" "+hFinal.getText().toString());
                MensajeDeInterambio mensajeDeInterambio=new MensajeDeInterambio(mensaje, IDUsuarioLogueado, startIntent.getStringExtra("UsuarioContrario"), true, fechaDeIinicio, fechaDeFinalizacion);
                Constantes.db.child("Mensaje").child(mensajeDeInterambio.getUid()).setValue(mensajeDeInterambio);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    public void revisarFecha(View v) {
        final Calendar c =Calendar.getInstance();
        final Calendar cal=Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+1);
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        ano=c.get(Calendar.YEAR);

        if (v==fechaInicio){
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    inicio.setText(dayOfMonth+"/"+month+"/"+year);
                    day= dayOfMonth;
                    mon =month;
                    ye=year;
                }
            }
                    ,dia,mes,ano);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();
        }
        cal.set(Calendar.DAY_OF_MONTH,day+1);
        cal.set(Calendar.MONTH,mon);
        cal.set(Calendar.YEAR,ye);
        if(inicio.getText()!=""){
            if(v==fechaFinal){
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        finali.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                }
                        ,dia,mes,ano);
                datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                datePickerDialog.show();
            }
        }
    }

    public void revisarHora(View v) {
        final Calendar c = Calendar.getInstance();
        final Calendar cal = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);
        if (v==horaInicio){
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    hInicio.setText(hourOfDay+":"+minute);
                }
            },hora,minutos,false);
            timePickerDialog.show();
        }
        if (hInicio.getText()!=""){
            if (v==horaFinal){
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hFinal.setText(hourOfDay+":"+minute);
                    }
                },hora,minutos,false);
                timePickerDialog.show();
            }
        }
    }

    public void volverAtras(View v){
        Intent intent=new Intent(MensajeActivity.this, ChatActivity.class);
        startActivity(intent);
    }

    private void rellenarDatosUsuario() {
        rellenarNombre();
        rellenarImagen();
    }

    private void rellenarImagen() {
        ImageView imageView = findViewById(R.id.iconImagen);
        Constantes.db.child("Vivienda").orderByChild("user_id").equalTo(startIntent.getStringExtra("UsuarioContrario"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot v: snapshot.getChildren()){
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

    private void rellenarNombre() {
        TextView nombreUsuarioContrario= (TextView) findViewById(R.id.nombreReceptor);
        Constantes.db.child("Usuario").child(startIntent.getStringExtra("UsuarioContrario"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Usuario usuario=snapshot.getValue(Usuario.class);
                        nombreUsuarioContrario.setText(usuario.getNombreUsuario());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void obtenerMensajesUsuarioLogueado(){
        Query q=Constantes.db.child("Mensaje").orderByChild("emisorYReceptor").equalTo(IDUsuarioLogueado+" "+startIntent.getStringExtra("UsuarioContrario"));
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Añadirlos a un arraylist
                // Buscar los mensajes en orden receptor emisor
                for(DataSnapshot mensaje: snapshot.getChildren()){
                    MensajeDeInterambio m=mensaje.getValue(MensajeDeInterambio.class);
                    mensajes.add(m);
                }
                obtenerMensajesUsuarioContrario();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void obtenerMensajesUsuarioContrario() {
        Query q=Constantes.db.child("Mensaje").orderByChild("emisorYReceptor").equalTo(startIntent.getStringExtra("UsuarioContrario")+" "+IDUsuarioLogueado);
        q.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NewApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mensaje: snapshot.getChildren()){
                    MensajeDeInterambio m=mensaje.getValue(MensajeDeInterambio.class);
                    mensajes.add(m);
                }
                anadirMensajesAlLayout();

                ScrollView scrollView =(ScrollView) findViewById(R.id.scrollView);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void anadirMensajesAlLayout() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.listaMensajes);
        linearLayout.removeAllViewsInLayout();
        linearLayout.removeAllViews();

        for(Mensaje m: mensajes){
            View v;
            if(!m.isMensajeIntercambio()){
                if(m.getEmisor().equals(IDUsuarioLogueado)) v = inflater.inflate(R.layout.card_view_mensajes_emisor, linearLayout, false);
                else v = inflater.inflate(R.layout.card_view_mensajes_receptor, linearLayout, false);
                rellenarMensaje(linearLayout,v, m);
            } else {
                if(m.getEmisor().equals(IDUsuarioLogueado)) v = inflater.inflate(R.layout.card_view_mensajes_intercambio_emisor, linearLayout, false);
                else v = inflater.inflate(R.layout.card_view_mensajes_intercambio_receptor, linearLayout, false);
                rellenarMensajeDeIntercambio(linearLayout,v, m);
            }

        }

    }

    private void rellenarMensajeDeIntercambio(LinearLayout linearLayout, View v, Mensaje m) {
        MensajeDeInterambio  mi= (MensajeDeInterambio) m;
        TextView mensaje=v.findViewById(R.id.mensajeMensaje);
        mensaje.setText(m.getMensaje());
        TextView fechaInicio=v.findViewById(R.id.FechaInicio);
        fechaInicio.setText(new SimpleDateFormat("dd/MM/yyyy").format(mi.getFechaInicio()));
        TextView horaInicio=v.findViewById(R.id.HoraInicio);
        horaInicio.setText((new SimpleDateFormat("HH:mm").format(mi.getFechaInicio())));
        TextView fechaFinal=v.findViewById(R.id.FechaFin);
        fechaFinal.setText((new SimpleDateFormat("dd/MM/yyyy").format(mi.getFechaFinal())));
        TextView horaFinal=v.findViewById(R.id.HoraFin);
        horaFinal.setText((new SimpleDateFormat("HH:mm").format(mi.getFechaFinal())));


        if(mi.getEmisor().equals(startIntent.getStringExtra("UsuarioContrario"))){
            LinearLayout layout=v.findViewById(R.id.botonesMI);


            TextView respuesta=new TextView(getApplicationContext());

            if(!mi.isAceptado()&&!mi.isRechazado()){

                Button aceptar =v.findViewById(R.id.aceptar);
                aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!mi.isRechazado()){
                            Constantes.db.child("Mensaje").child(mi.getUid()).child("aceptado").setValue(true);
                            Intercambio intercambio=new Intercambio(IDUsuarioLogueado, startIntent.getStringExtra("UsuarioContrario"), mi.getFechaInicio(), mi.getFechaFinal());
                            Constantes.db.child("Intercambio").child(intercambio.getUid()).setValue(intercambio);
                            mi.setAceptado(true);
                            Toast.makeText(MensajeActivity.this, "Has aceptado el intercambio", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(MensajeActivity.this, "Ya has rechazado el intercambio", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                Button rechazar =v.findViewById(R.id.rechazar);
                rechazar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!mi.isAceptado()){
                            Constantes.db.child("Mensaje").child(mi.getUid()).child("rechazado").setValue(true);
                            mi.setRechazado(true);
                            Toast.makeText(MensajeActivity.this, "Has rechazado el intercambio", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(MensajeActivity.this, "Ya has aceptado el intercambio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if(mi.isAceptado()){
                layout.removeAllViews();
                respuesta.setText("Intercambio Aceptado");
                layout.addView(respuesta);
            } else if(mi.isRechazado()){
                layout.removeAllViews();
                respuesta.setText("Intercambio Rechazado");
                layout.addView(respuesta);
            }

        } else{
            TextView respuesta2=v.findViewById(R.id.Respuesta);
            if(mi.isAceptado()){
                respuesta2.setText("Intercambio Aceptado");
            } else if(mi.isRechazado()){
                respuesta2.setText("Intercambio Rechazado");
            }
        }
        linearLayout.addView(v);

    }

    private void rellenarMensaje(LinearLayout linearLayout, View v, Mensaje m) {
        TextView mensaje=v.findViewById(R.id.mensajeMensaje);
        mensaje.setText(m.getMensaje());
        TextView hora=v.findViewById(R.id.rechazar);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        hora.setText(formatter.format(m.getFechaCreacion()));

        linearLayout.addView(v);
    }

    public void enviarMensaje(View view) {
        EditText contenidoMensaje= (EditText) findViewById(R.id.txtMensaje);
        if(contenidoMensaje.getText().toString().trim().equals("")){
            Toast.makeText(MensajeActivity.this,"Debes escribir un mensaje para enviar",Toast.LENGTH_SHORT);
        } else{
            Mensaje mensaje=new Mensaje(contenidoMensaje.getText().toString(), IDUsuarioLogueado, startIntent.getStringExtra("UsuarioContrario"), false);
            Constantes.db.child("Mensaje").child(mensaje.getUid()).setValue(mensaje);
            contenidoMensaje.setText("");
        }
    }

}
