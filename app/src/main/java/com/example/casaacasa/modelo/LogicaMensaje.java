package com.example.casaacasa.modelo;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ServerValue;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

public class LogicaMensaje extends AppCompatActivity {
    private String key;
    private Mensaje mensaje;
    private LogicaUsuario lUsuario;

    public LogicaMensaje(String key, Mensaje mensaje) {
        this.key = key;
        this.mensaje = mensaje;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public long getCreatedTimestampLong(){
        return (long) mensaje.getCreatedTimestamp();
    }

    public LogicaUsuario getlUsuario() {
        return lUsuario;
    }

    public void setlUsuario(LogicaUsuario lUsuario) {
        this.lUsuario = lUsuario;
    }

    public String fechaDeCreacionDelMensaje(){
        Date date = new Date(getCreatedTimestampLong());
        PrettyTime prettyTime = new PrettyTime(new Date(),Locale.getDefault());
        return prettyTime.format(date);
        /*Date date = new Date(getCreatedTimestampLong());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());//a pm o am
        return sdf.format(date);*/
    }
}