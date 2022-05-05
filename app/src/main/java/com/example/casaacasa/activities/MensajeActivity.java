package com.example.casaacasa.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.casaacasa.R;
import com.example.casaacasa.modelo.Mensaje;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.TreeSet;
import java.util.zip.Inflater;

import java.util.Calendar;

public class MensajeActivity extends AppCompatActivity {
    private ImageView calendario;
    private TextView inicio, finali, hInicio, hFinal;
    private Button fechaInicio, fechaFinal, horaInicio, horaFinal;
    private int dia, mes, ano, day, mon, ye, hora, minutos;

    private Intent startIntent;
    private TreeSet<Mensaje> mensajes;
    private LayoutInflater inflater;

    //TODO Comprobar que se ponen los mensajes como receptor                                                    HECHO
    // Añadir un boolenano al mensaje para saber si es de fecha o de intercambio                                HECHO
    // Hacer clase heredada de Mensaje para el mensaje de intercambio                                           HECHO
    // Hacer los cambios necesarios en la lógica y en la bbdd para el nuevo mensaje                             HECHO
    // Mirar si se puede meter una rama en master directamente, sin ecesidad de merge, un copiar y pegar

    //TODO Quitar nombre y poner bien el mensaje y la hora en los dos card views                                HECHO
    // Me falta poner el dia en el que se ha enviado el mensaje

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajeria);

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
                    public void onClick(DialogInterface dialog, int which) {comprobarfechas(); dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }

    private void comprobarfechas(){

    }


    public void revisarFecha(View v) {
        final Calendar c =Calendar.getInstance();
        final Calendar cal=Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH);
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
        //TODO Añadir un atributo que sea una combinatoria del id del emisor y elklk receptor, así solo cojeré los mensajes que de los dos y no sobrecargo la apluicacióin
        // tambien tendré que hacer dos búscaquedas diferentes, una receptor emisor y otra emisor receptor, para coger los deo tipos de mensaje
        Query q=Constantes.db.child("Mensaje").orderByChild("emisorYReceptor").equalTo("d5edaee4-9498-48c4-a4c4-baa3978adfeb "+startIntent.getStringExtra("UsuarioContrario"));
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Añadirlos a un arraylist
                // Buscar los mensajes en orden receptor emisor
                for(DataSnapshot mensaje: snapshot.getChildren()){
                    Mensaje m=mensaje.getValue(Mensaje.class);
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
        Query q=Constantes.db.child("Mensaje").orderByChild("emisorYReceptor").equalTo(startIntent.getStringExtra("UsuarioContrario")+" d5edaee4-9498-48c4-a4c4-baa3978adfeb");
        q.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NewApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mensaje: snapshot.getChildren()){
                    Mensaje m=mensaje.getValue(Mensaje.class);
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
            if(!m.isMensajeIntercambio()){ //Me va a petar poruqe no he hecho los cambios en BBDD. Tengo los mensajes viejos en la bbdd, sin el nuevo atributo
                if(m.getEmisor().equals("d5edaee4-9498-48c4-a4c4-baa3978adfeb")) v = inflater.inflate(R.layout.card_view_mensajes_emisor, linearLayout, false);
                else v = inflater.inflate(R.layout.card_view_mensajes_receptor, linearLayout, false);
                rellenarMensaje(linearLayout,v, m);
            } else {
                //v = a al xml que alla hecho el jorge
            }


        }

    }

    private void rellenarMensaje(LinearLayout linearLayout, View v, Mensaje m) {
        TextView mensaje=v.findViewById(R.id.mensajeMensaje);
        mensaje.setText(m.getMensaje());
        TextView hora=v.findViewById(R.id.horaMensaje);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        hora.setText(formatter.format(m.getFechaCreacion()));

        linearLayout.addView(v);
    }

    public void enviarMensaje(View view) {
        EditText contenidoMensaje= (EditText) findViewById(R.id.txtMensaje);
        if(contenidoMensaje.getText().equals("")){
            Toast.makeText(MensajeActivity.this,"Debes escribir un mensaje para enviar",Toast.LENGTH_SHORT);
        } else{
            Mensaje mensaje=new Mensaje(contenidoMensaje.getText().toString(), "d5edaee4-9498-48c4-a4c4-baa3978adfeb", startIntent.getStringExtra("UsuarioContrario"), false);
            Constantes.db.child("Mensaje").child(mensaje.getUid()).setValue(mensaje);
        }
    }

}
