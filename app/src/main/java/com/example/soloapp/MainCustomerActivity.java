package com.example.soloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.soloapp.Adapters.SesionRecyclerAdapter;
import com.example.soloapp.Clases.Sesion;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;

public class MainCustomerActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    private SesionRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Bitmap[] mImages;
    private Button buscarFotografo;
    private static final String TAG = "Sebastian";
    private FirebaseFirestore DBfirebase = FirebaseFirestore.getInstance();
    private ArrayList<Sesion> collectionSesiones = new ArrayList<Sesion>();
    private Sesion[] mLista;
    private int FUENTE_MAIN = 1;
//    SesionRecyclerAdapter sesionRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // ON CREATE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customer2);
        buscarFotografo = findViewById(R.id.button_buscarFotografo);
        setTitle("Sesiones pasadas");

    }

    public void getSesiones(){
        collectionSesiones.clear();
        Log.i(TAG, "getSesiones: "+ FirebaseAuth.getInstance().getUid().toString());
        DBfirebase.collection("sesiones")
                .whereEqualTo("idCliente", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("terminada", true)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        setCollectionSesiones(queryDocumentSnapshots);
                        Log.i(TAG, "onSuccess: se logro el query a db para sesiones");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: fail DBfirebase get sesiones", e.getCause() );
            }
        });
        
    }
    
    
    public void setCollectionSesiones(QuerySnapshot result){
        Sesion aux;
        for(QueryDocumentSnapshot document : result){
            aux = document.toObject(Sesion.class);
            aux.setId(document.getId());
            collectionSesiones.add(aux);
            Log.i(TAG, "setCollectionSesiones: se entro a el for dentro de set Collection, foto: "+ aux.getIdFotografo() +" ses id: "+ aux.getId());

        }
        mLista = collectionSesiones.toArray(new Sesion[collectionSesiones.size()]);
        OnDataReadingFinishedListener();
    }

    public void OnDataReadingFinishedListener(){
        buildRecyclerView();
    }

    public void buildRecyclerView(){
        Log.i(TAG, "buildRecyclerView: Se entro a build recycler view");
        mRecyclerView = findViewById(R.id.main_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainCustomerActivity.this));
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new SesionRecyclerAdapter(mLista, mImages, MainCustomerActivity.this, FUENTE_MAIN);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        mAdapter.setOnClickListener(new SesionRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Sesion selectedSesion = mLista[position];

                Intent intent;
                intent = new Intent(MainCustomerActivity.this, VerSesionActivity.class);
                intent.putExtra("item", selectedSesion);
                startActivity(intent);
                Log.i(TAG, "onItemClick: Se empezo la actividad VerSesionActivity");

            }
        });

    }

    public void buscarFotografo(View view){ // BOTON BUSCAR FOTOGRAFO
       // Intent intent = new Intent(this, ProfileActivity.class);
       // startActivity(intent);
    }


    private void StartLoginActivity(){ // FUNCION IR A LOGIN
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_logOut_main:
                AuthUI.getInstance().signOut(this);
                return true;

            case R.id.menu_verPerfil_Main:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            StartLoginActivity();
            return;
        }
//        initRecyclerView(firebaseAuth.getCurrentUser());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
        getSesiones();
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
//        if(sesionRecyclerAdapter != null){
//            sesionRecyclerAdapter.stopListening();
//        }
    }





//    private void initRecyclerView(FirebaseUser user){
//        DocumentReference ref = DBfirebase.collection("users").document(user.getUid());
//
//        Query query = FirebaseFirestore.getInstance().collection("sesiones")
//                .whereEqualTo("cliente", ref)
//                .whereEqualTo("terminada", false)
//                .orderBy("fecha");
//
//        FirestoreRecyclerOptions<Sesion> options = new FirestoreRecyclerOptions.Builder<Sesion>()
//                .setQuery(query,Sesion.class)
//                .build();
//
//        sesionRecyclerAdapter = new SesionRecyclerAdapter(options);
//        recyclerView.setAdapter(sesionRecyclerAdapter);
//        sesionRecyclerAdapter.startListening();
//
//
//    }
}