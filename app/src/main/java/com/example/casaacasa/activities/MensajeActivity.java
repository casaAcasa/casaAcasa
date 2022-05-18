package com.example.casaacasa.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.casaacasa.R;

import java.util.Calendar;

public class MensajeActivity extends AppCompatActivity {
    private ImageView calendario;
    private TextView inicio, finali, hInicio, hFinal;
    private Button fechaInicio, fechaFinal, horaInicio, horaFinal;
    private int dia, mes, ano, day, mon, ye, hora, minutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajeria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
}
