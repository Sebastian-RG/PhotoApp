package com.example.soloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.soloapp.Clases.Fotografo;
import com.example.soloapp.Clases.Sesion;
import com.example.soloapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ContactoActivity extends AppCompatActivity {

    private static final String TAG = "Sebastian";
    Fotografo fotografo;
    ImageView imageView_Contacto;
    TextView textView_Contacto_nombre;
    TextView textView_contacto_numero;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        fotografo = (Fotografo) getIntent().getSerializableExtra("fotografo");
        imageView_Contacto =findViewById(R.id.imageView_Contacto);
        textView_contacto_numero =findViewById(R.id.textView_contacto_numero);
        textView_Contacto_nombre =findViewById(R.id.textView_Contacto_nombre);
        textView_Contacto_nombre.setText(fotografo.getNombre() +" "+ fotografo.getApellido());
        textView_contacto_numero.setText("telefono: " +fotografo.getNumero());


        StorageReference storageRef = storage.getReference();
        StorageReference spaceRef = storageRef.child("fotografos").child(fotografo.getId()).child("profile.jpg");
        spaceRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(ContactoActivity.this).load(uri)
                                .transition(withCrossFade())
                                .into(imageView_Contacto);
                    }
                });

        Sesion sesion = new Sesion();
        sesion.setFecha(new Date());
        sesion.setIdCliente(firebaseAuth.getUid());
        sesion.setIdFotografo(fotografo.getId());
        sesion.setNombreFotografo(fotografo.getNombre() + " " + fotografo.getApellido());
        sesion.setTerminada(false);
        firestore.collection("sesiones").add(sesion)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ContactoActivity.this, "Sesion Creada correctamente", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: Error en crear sesion", e.getCause() );
                        Toast.makeText(ContactoActivity.this, "Error Creando sesion", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void volverButton(View view){
        Intent intent;
        intent = new Intent(ContactoActivity.this, MainCustomerActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Por favor espere a que sea contactado", Toast.LENGTH_LONG).show();
        finish();
    }
}