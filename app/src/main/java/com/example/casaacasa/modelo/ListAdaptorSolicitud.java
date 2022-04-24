package com.example.casaacasa.modelo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.casaacasa.R;

import java.util.List;

public class ListAdaptorSolicitud extends RecyclerView.Adapter<ListAdaptorSolicitud.ViewHolder> {
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    private int position;
    private OnSolicitudListener mOnSolicitudListener;

    public ListAdaptorSolicitud(List<ListElement> itemList, Context context, int position, OnSolicitudListener onSolicitudListener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.position = position;
        this.mOnSolicitudListener=onSolicitudListener;
    }

    @Override
    public int getItemCount() {return mData.size();}

    @Override
    public ListAdaptorSolicitud.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.usuario_solicitud, null);
        return new ListAdaptorSolicitud.ViewHolder(view, mOnSolicitudListener);
    }

    @Override
    public void onBindViewHolder(final ListAdaptorSolicitud.ViewHolder holder, final int position){
        holder.binData(mData.get(position));
    }

    public void setItems(List<ListElement> items){ mData = items;}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView iconImage;
        TextView name, ciudad;
        OnSolicitudListener onSolicitudListener;

        ViewHolder(View itemView, OnSolicitudListener onSolicitudListener){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImagen);
            name = itemView.findViewById(R.id.nombreUsuario);
            ciudad = itemView.findViewById(R.id.nombrePoblacion);
            this.onSolicitudListener=onSolicitudListener;
        }

        void binData(final ListElement item) {
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            name.setText(item.getName());
            ciudad.setText(item.getCiudad());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSolicitudListener.onSolicitudClick(getAdapterPosition());
        }
    }

    public interface OnSolicitudListener{
        void onSolicitudClick(int position);
    }
}
