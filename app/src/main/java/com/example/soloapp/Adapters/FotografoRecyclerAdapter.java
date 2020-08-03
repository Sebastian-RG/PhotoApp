package com.example.soloapp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soloapp.Clases.Fotografo;
import com.example.soloapp.Clases.Sesion;
import com.example.soloapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class FotografoRecyclerAdapter extends RecyclerView.Adapter<FotografoRecyclerAdapter.FotografoViewHolder> {
    private static final String TAG = "Sebastian";
    private Fotografo[] data;
    private Context context;
    private OnItemClickListener mListener;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private int fuente;

    public FotografoRecyclerAdapter(Fotografo[] data, Context context, int fuente){
        this.data = data;
        this.context = context;
        this.fuente = fuente;
    }

    public interface OnItemClickListener{
        void onPortfolioClick(int position);
        void onElegirClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public FotografoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_fotografoencontrado, parent, false);
        FotografoViewHolder fotografoViewHolder = new FotografoViewHolder(itemView, mListener);
//        if(fuente == 1){
//            sesionViewHolder.boton.setVisibility(View.GONE);
//        }
        return fotografoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FotografoViewHolder holder, final int position) {
        holder.ratingBar.setRating( data[position].getRating());
        holder.nombre.setText(data[position].getNombre() + " " + data[position].getApellido());
        holder.ratingBar.setIsIndicator(true);


        StorageReference storageReference = storage.getReference();
        StorageReference spaceRef = storageReference.child("fotografos").child(data[position].getId()).child("profile.jpg");
        spaceRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri)
                        .transition(withCrossFade())
                        .into(holder.imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: no se pudo mostrar la imagen de "+ data[position].getNombre() + " " + data[position].getApellido(), e.getCause() );
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.length;
    }



    public static class FotografoViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre;
        public ImageView imageView;
        public RatingBar ratingBar;
        public Button verPortfolio;
        public Button elegir;

        public FotografoViewHolder(@NonNull View itemView, final OnItemClickListener listener){
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView_FotografoEncontradoRV);
            this.nombre = itemView.findViewById(R.id.textView_FotografoEncontradoRV_nombre);
            this.verPortfolio = itemView.findViewById(R.id.button_FotografoEncontradoRV_VerPortfolio);
            this.ratingBar = itemView.findViewById(R.id.ratingBar_FotografoEncontradoRV_Estrellas);
            this.elegir = itemView.findViewById(R.id.button_FotografoEncontradoRV_Elegir);

            verPortfolio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo hacer ver portfolio
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onPortfolioClick(position);
                        }
                    }
                }
            });
            elegir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo hacer elegir
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onElegirClick(position);
                        }
                    }
                }
            });

        }
    }
}
