package com.example.imagenes;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private NetworkImageView networkImageView;
        private TextView textView;
        private ImageLoader imageLoader;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            networkImageView = itemView.findViewById(R.id.imgCargarImagen);
            textView = itemView.findViewById(R.id.txtCargarTexto);

            /* TODO: utiliza Volley para cargar la imagen de Internet. */
            imageLoader = Singleton.getInstance(itemView.getContext()).getImageLoader();
        }
    }

    public List<imagenes> cardList;

    public Adapter(List<imagenes> cardList) {
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Crea la vista de cada Card, es el inflador del RecyclerView.
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.imagen,
                parent,
                false
        );
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //Método encargado de asignar el texto y la imagen a cada Card que se crea.
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // Asignamos el nombre al TextView en la posición en que se esta creando del RecyclerView.
        holder.textView.setText(cardList.get(position).getName());

        //Asignamos la imagen al NetworkImageManager que cargara la imagen de Internet.
        holder.networkImageView.setImageUrl(cardList.get(position).imageurl, holder.imageLoader);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
