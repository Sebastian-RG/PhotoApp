package com.example.soloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soloapp.Adapters.PerfilRecyclerAdapter;
import com.example.soloapp.Adapters.VerSesionRecyclerAdapter;
import com.example.soloapp.Clases.Fotografo;
import com.google.firebase.firestore.FirebaseFirestore;

public class PerfilFotografoActivity extends AppCompatActivity {

    Fotografo fotografo;
    private PerfilRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private static final String TAG = "Sebastian";
    private FirebaseFirestore DBfirebase = FirebaseFirestore.getInstance();
    TextView descripcion;
    RatingBar rating;
    TextView contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Portfolio");
        setContentView(R.layout.activity_perfil_fotografo);
        fotografo = (Fotografo) getIntent().getSerializableExtra("fotografo");
        contacto = findViewById(R.id.textView_PerfilFotografo_numero);
        rating = findViewById(R.id.ratingBar_PerfilFotografo);
        descripcion = findViewById(R.id.TextView_PerfilFotografo_Descripcion);
        descripcion.setText(fotografo.getDescripcion());
        rating.setIsIndicator(true);
        rating.setRating(fotografo.getRating());
        contacto.setText(fotografo.getNumero());

    }


    @Override
    protected void onStart() {
        super.onStart();
        buildRecyclerView();
    }

    public void buildRecyclerView(){
        Log.i(TAG, "buildRecyclerView: Se entro a build recycler view");
        mRecyclerView = findViewById(R.id.recyclerView_PerfilFotografo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(PerfilFotografoActivity.this));
        mAdapter = new PerfilRecyclerAdapter(PerfilFotografoActivity.this, fotografo);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

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
