package com.example.soloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.soloapp.Clases.Fotografo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ConfirmaFotografoActivity extends AppCompatActivity {

    Fotografo fotografo;
    private static final String TAG = "Sebastian";
    TextView estimado;
    TextView nombre;
    int duracion;
    double precio;
    ImageView imageView;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Confirmar Seleccion");
        setContentView(R.layout.activity_confirma_fotografo);
        fotografo = (Fotografo) getIntent().getSerializableExtra("fotografo");
        duracion = getIntent().getIntExtra("duracion", 0);
        if(duracion == 0){
            Log.e(TAG, "onCreate: ha habido un error en conseguir la duracion en confirma fotografo activity" );
            finish();
        }
        switch (duracion){
            case 5:
                precio = fotografo.getPrecio5();
                break;
            case 15:
                precio = fotografo.getPrecio15();
                break;
            case 25:
                precio = fotografo.getPrecio25();
                break;
            default:
                Log.e(TAG, "onCreate: error en switch duracion" );
                finish();
            break;
        }

        estimado = findViewById(R.id.textView_Confirmar_costo);
        nombre = findViewById(R.id.textView_Confirmar_Nombre);
        imageView = findViewById(R.id.imageView_Confirmar);

        nombre.setText(fotografo.getNombre() + " "+ fotografo.getApellido());
        estimado.setText("costo estimado: "+ precio + " soles");

        StorageReference storageRef = storage.getReference();
        StorageReference spaceRef = storageRef.child("fotografos").child(fotografo.getId()).child("profile.jpg");
        spaceRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(ConfirmaFotografoActivity.this).load(uri)
                                .transition(withCrossFade())
                                .into(imageView);
                    }
                });

    }

    public void botonSi(View view){
//intent
        Intent intent;
        intent = new Intent(ConfirmaFotografoActivity.this, ContactoActivity.class);
        intent.putExtra("fotografo", fotografo);
        startActivity(intent);
        finish();
    }
    public void botonNo(View view){
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_secondary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_buttonBack_secondary:
                finish();
                return true;
            default:
                return true;
        }}
}