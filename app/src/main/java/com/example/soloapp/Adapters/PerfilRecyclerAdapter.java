package com.example.soloapp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soloapp.Clases.Fotografo;
import com.example.soloapp.Clases.Sesion;
import com.example.soloapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PerfilRecyclerAdapter extends RecyclerView.Adapter<PerfilRecyclerAdapter.PerfilViewHolder> {
    private static final String TAG = "Sebastian";
    private Context context;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Fotografo fotografo;

    public PerfilRecyclerAdapter(Context context, Fotografo fotografo){
        this.context = context;
        this.fotografo = fotografo;
    }


    @NonNull
    @Override
    public PerfilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_versesion, parent, false);
        PerfilViewHolder perfilViewHolder = new PerfilViewHolder(itemView);
        return perfilViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PerfilViewHolder holder, int position) {
        final int pos = position;
        Log.i(TAG, "onBindViewHolder: OnBind, pos: " + position);
        StorageReference storageRef = storage.getReference();
        Log.i(TAG, "onBindViewHolder: getting:"+ "P00" + pos + ".jpg");
        storageRef.child("fotografos").child(fotografo.getId()).child("portfolio").child("P00" + pos + ".jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context).load(uri)
                                .transition(withCrossFade())
                                .into(holder.imageView);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return fotografo.getPortfolioSize();
    }

    public static class PerfilViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public PerfilViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.rv_verSesion_image);


        }
    }

}
