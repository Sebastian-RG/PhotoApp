package com.example.soloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.soloapp.Adapters.FotografoRecyclerAdapter;
import com.example.soloapp.Clases.Fotografo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.soloapp.Utilities.Util.getDistanceBetweenTwoPoints;

public class FotografosEncontradosActivity extends AppCompatActivity {

    TextView cantidad;
    Location lugar;
    private FusedLocationProviderClient fusedLocationClient;
    boolean locationObtained;
    GeoPoint geoPoint;
    double distancia;
    int duracion;
    float ratingMin;
    private ArrayList<Fotografo> collectionFotografos = new ArrayList<Fotografo>();
    private Fotografo[] mLista;
    private static final String TAG = "Sebastian";
    private FirebaseFirestore DBfirebase = FirebaseFirestore.getInstance();
    private FotografoRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private int FUENTE_FOUND = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationObtained = false;
        setContentView(R.layout.activity_fotografos_encontrados);
        cantidad = findViewById(R.id.textView_FotografosEncontrados_cantidad);
        cantidad.setText("Se han encontrado "+ 0 +" Fotografos");
        setTitle("Fotografos Cercanos");
        if (checkLocationPermission()){
            getLocation();
        }else{
            askLocationPermission();
            getLocation();
        }
        
        distancia = getIntent().getDoubleExtra("espera", 0);
        duracion = getIntent().getIntExtra("duracion", 0);
        ratingMin = getIntent().getFloatExtra("rating", 0);
        if(distancia == 0 || duracion==0){
            Log.e(TAG, "onCreate: Se pasaron mal las distancia "+ distancia + " o duracion "+duracion );
            finish();
        }
        Log.i(TAG, "onCreate: ratingMin: " + ratingMin);


    }

    public void getFotografos(){
        collectionFotografos.clear();
        Log.i(TAG, "getFotografos: entro a get fotografos" );
        DBfirebase.collection("fotografos")
                .whereEqualTo("activo", true)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        setCollectionFotografos(queryDocumentSnapshots);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: fail get Fotografos", e.getCause() );
                    }
                });

    }

    public void setCollectionFotografos(QuerySnapshot result){
        Fotografo aux;
        for(QueryDocumentSnapshot document: result){
            aux=new Fotografo();
            aux = document.toObject(Fotografo.class);
            aux.setId(document.getId());
            aux.setUbicacion(document.getGeoPoint("geopoint"));
            Log.i(TAG, "setCollectionFotografos: se encontro: "+aux.toString());
            if(isNearLocation(document.getGeoPoint("geopoint")) && (aux.getRating() >= ratingMin)){
                Log.i(TAG, "setCollectionFotografos: Se guardo: " + aux.toString());
                collectionFotografos.add(aux);
            }
        }
        cantidad.setText("Se han encontrado "+ collectionFotografos.size() +" Fotografos");
        mLista = collectionFotografos.toArray(new Fotografo[collectionFotografos.size()]);
        buildRecyclerView();
    }

    public void botonCancelar(View view){
        finish();
    }

    private void buildRecyclerView() {
        Log.i(TAG, "buildRecyclerView: Se entro");
        mRecyclerView = findViewById(R.id.recyclerView_FotografosEncontrados);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(FotografosEncontradosActivity.this));
        mAdapter = new FotografoRecyclerAdapter(mLista, FotografosEncontradosActivity.this, FUENTE_FOUND);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.setOnClickListener(new FotografoRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onPortfolioClick(int position) {
                //logica porftolio
                Intent intent = new Intent(FotografosEncontradosActivity.this, PerfilFotografoActivity.class);
                intent.putExtra("fotografo", mLista[position]);
                startActivity(intent);
            }

            @Override
            public void onElegirClick(int position) {
                //logica choose
                Intent intent = new Intent(FotografosEncontradosActivity.this, ConfirmaFotografoActivity.class); //todo Confirmar.class
                intent.putExtra("fotografo", mLista[position]);
                intent.putExtra("duracion", duracion);
                startActivity(intent);
            }
        });
    }


    private void getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(FotografosEncontradosActivity.this);
        if (checkLocationPermission()){
            fusedLocationClient.getLastLocation().addOnSuccessListener(FotografosEncontradosActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    String mensaje = "";
                    if (location != null) {
                        mensaje = "Ubicación capturada";
                        lugar = location;
                        locationObtained = true;
                        getFotografos();
                    }
                    else {
                        mensaje = "No se pudo obtener la ubicación";
                    }
                    //Toast.makeText(FotografosEncontradosActivity.this, mensaje,Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void askLocationPermission() {
        ActivityCompat.requestPermissions(FotografosEncontradosActivity.this
                , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
    }

    private boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(FotografosEncontradosActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
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

    private boolean isNearLocation(GeoPoint geoPoint) {
        return(getDistanceBetweenTwoPoints(lugar.getLatitude(), lugar.getLongitude(),
                geoPoint.getLatitude(), geoPoint.getLongitude()) < distancia);
    }

}