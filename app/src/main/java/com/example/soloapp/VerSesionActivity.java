package com.example.soloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.soloapp.Adapters.SesionRecyclerAdapter;
import com.example.soloapp.Adapters.VerSesionRecyclerAdapter;
import com.example.soloapp.Clases.Sesion;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.firestore.FirebaseFirestore;

public class VerSesionActivity extends AppCompatActivity {

    private VerSesionRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private static final String TAG = "Sebastian";
    private FirebaseFirestore DBfirebase = FirebaseFirestore.getInstance();
    private Sesion sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_sesion);
        setTitle("Ver Sesion");
        sesion = (Sesion) getIntent().getSerializableExtra("item");
        Log.i(TAG, "onCreate: id sesion a ver: " + sesion.getId());


    }

    @Override
    protected void onStart() {
        super.onStart();
        buildRecyclerView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: Se termino el VerSesionAct");
    }

    public void buildRecyclerView(){
        Log.i(TAG, "buildRecyclerView: Se entro a build recycler view");
        mRecyclerView = findViewById(R.id.ver_sesion_rv);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(VerSesionActivity.this));
        mAdapter = new VerSesionRecyclerAdapter(VerSesionActivity.this, sesion);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAdapter.setOnClickListener(new VerSesionRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(VerSesionActivity.this, "Descargando imagen "+position + "...", Toast.LENGTH_SHORT).show();
                //todo que se descargue
            }
        });
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