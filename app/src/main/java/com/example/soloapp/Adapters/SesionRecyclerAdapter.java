package com.example.soloapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soloapp.Clases.Sesion;
import com.example.soloapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SesionRecyclerAdapter extends RecyclerView.Adapter<SesionRecyclerAdapter.SesionViewHolder> {

    private static final String TAG = "Sebastian";
    private Sesion[] data;
    private Context context;
    private OnItemClickListener mListener;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private int fuente;

    public SesionRecyclerAdapter(Sesion[] data, Context context, int fuente) {
        this.data = data;
        this.context = context;
        this.fuente = fuente;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onButtonClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public SesionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_sesion_main, parent, false);
        SesionViewHolder sesionViewHolder = new SesionViewHolder(itemView, mListener);
//        if(fuente == 1){
//            sesionViewHolder.boton.setVisibility(View.GONE);
//        }
        return sesionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SesionViewHolder holder, int position) {
        String nombre = data[position].getNombreFotografo();
        String fecha = DateFormat.format("d/MM/yyyy", data[position].getFecha()).toString();
        holder.nombre.setText(nombre);
        holder.fecha.setText(fecha);
        StorageReference storageRef = storage.getReference();
         StorageReference spaceRef = storageRef.child("fotografos").child(data[position].getIdFotografo()).child("profile.jpg");
        spaceRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri)
                        .transition(withCrossFade())
                        .into(holder.imageView);
            }
        });
        //holder.apellido.setText("apellido");
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class SesionViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView nombre;
        public TextView fecha;
        public Button boton;

        public SesionViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            this.fecha = itemView.findViewById(R.id.rv_fecha_main);
            this.nombre = itemView.findViewById(R.id.rv_nombre_main);
            this.boton = itemView.findViewById(R.id.rv_verPorfolio_main);
            this.imageView = itemView.findViewById(R.id.rv_foto_main);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onButtonClick(position);
                        }
                    }
                }
            });
        }
    }


//    public SesionRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Sesion> options) {
//        super(options);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull SesionViewHolder holder, int position, @NonNull Sesion model) {
//
//        holder.rv_nombre_main.setText(model.getFotografo().getNombre());
//        CharSequence dateCharSeq = DateFormat.format("EEEE, d MMM, yyyy", model.getFecha());
//        holder.rv_fecha_main.setText(dateCharSeq);
//          holder.rv_foto_main.setImageBitmap();
//    }
//
//    @NonNull
//    @Override
//    public SesionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.rv_sesion_main, parent, false);
//        return new SesionViewHolder(view);
//    }
//
//    class SesionViewHolder extends RecyclerView.ViewHolder{
//
//        TextView rv_nombre_main, rv_fecha_main;
//        Button rv_verPorfolio_main;
//        ImageView rv_foto_main;
//
//        public SesionViewHolder(@NonNull View itemView) {
//            super(itemView);
//            rv_nombre_main = itemView.findViewById(R.id.rv_nombre_main);
//            rv_fecha_main = itemView.findViewById(R.id.rv_fecha_main);
//            rv_verPorfolio_main = itemView.findViewById(R.id.rv_verPorfolio_main);
//            rv_foto_main = itemView.findViewById(R.id.rv_foto_main);
//
//
//
//        }
//    }

}
