package com.example.soloapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soloapp.Clases.Sesion;
import com.example.soloapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class VerSesionRecyclerAdapter extends RecyclerView.Adapter<VerSesionRecyclerAdapter.VerSesionViewHolder> {
    private static final String TAG = "Sebastian";
    private Context context;
    private OnItemClickListener mListener;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Sesion sesion;

    public VerSesionRecyclerAdapter(Context context, Sesion sesion) {
        this.context = context;
        this.sesion = sesion;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public VerSesionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_versesion, parent, false);
        VerSesionViewHolder verSesionViewHolder = new VerSesionViewHolder(itemView, mListener);
        return verSesionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final VerSesionViewHolder holder,  int position) {
        final int pos = position;
        Log.i(TAG, "onBindViewHolder: OnBind, pos: " + position);
        StorageReference storageRef = storage.getReference();
        Log.i(TAG, "onBindViewHolder: getting:"+ "P00" + pos + ".jpg");
        storageRef.child("sesiones").child(sesion.getId()).child("P00" + pos + ".jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context).load(uri)
                                .transition(withCrossFade())
                                .into(holder.imageView);
                    }
                });
    }


//        storageRef.child("sesiones").child(sesion.getId()).listAll().
//                addOnCompleteListener(new OnCompleteListener<ListResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<ListResult> task) {
//                        List<StorageReference> listStoRef =task.getResult().getPrefixes();
//                        if(!listStoRef.isEmpty()){
//                            int i = 0;
//                            Log.i(TAG, "onSuccess: "+ listStoRef.toString());
//                            for (StorageReference prefix : listStoRef) {
//                                Log.i(TAG, "Prefix "+ i +" es "+ prefix.toString());
//                                if(i == pos){
//                                    //execute
//                                    Log.i(TAG, "onSuccess: recycler, entro al if i = posision, pos= "+ pos);
//                                    prefix.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            Glide.with(context).load(uri)
//                                                    .transition(withCrossFade())
//                                                    .into(holder.imageView);
//                                        }
//                                    }); }
//                            }
//                        }
//
//
//
//                    }
//                });




//        .addOnSuccessListener(new OnSuccessListener<ListResult>() {
//            @Override
//            public void onSuccess(ListResult listResult) {
//                int i = 0;
//                for (StorageReference prefix : listResult.getPrefixes()) {
//                    Log.i(TAG, "onSuccess: "+ listResult.toString());
//                    if(i == pos){
//                        //execute
//                        Log.i(TAG, "onSuccess: 69recycler, entro al if i = posision, pos= "+ pos);
//                        prefix.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                Glide.with(context).load(uri)
//                                        .transition(withCrossFade())
//                                        .into(holder.imageView);
//                            }
//                        }); }
//                    i++;
//                } } }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG, "onFailure: error en listall", e.getCause() );
//            }
//        });


    @Override
    public int getItemCount() {
        return sesion.getCantidadFotos();
//        final int[] i = new int[1];
//        StorageReference storageRef = storage.getReference();
//        storageRef.child("sesiones").child(sesion.getId()).listAll()
//                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                    @Override
//                    public void onSuccess(ListResult listResult) {
//                        i[0] = listResult.getPrefixes().size();
//                    } })
//        .addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG, "onFailure: error en get item count",e.getCause() );
//                i[0] = 0;
//            }
//        });
//        return i[0];
    }


    public static class VerSesionViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public VerSesionViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.rv_verSesion_image);

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
        }
    }

}
