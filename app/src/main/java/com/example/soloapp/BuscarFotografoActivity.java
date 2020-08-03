package com.example.soloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.soloapp.Clases.Fotografo;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BuscarFotografoActivity extends AppCompatActivity {

    private Spinner spinnerTiempoEspera;
    private Spinner spinnerTiempoSesion;
    private RatingBar ratingBar;
    private Button buscar;
    private ArrayList<Fotografo> collectionFotografos = new ArrayList<Fotografo>();
    private Fotografo[] mLista;
    private int contador;
    private static final String TAG = "Sebastian";
    private FirebaseFirestore DBfirebase = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Buscar Fotografo");
        setContentView(R.layout.activity_buscar_fotografo);
        String[] lista1 = {"5+ minutos", "15+ minutos", "25+ minutos"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lista1);
        spinnerTiempoEspera = findViewById(R.id.spinner_BuscarFotografo_Espera);
        spinnerTiempoEspera.setAdapter(adapter1);
        String[] lista2 = {"5+ minutos", "15+ minutos", "25+ minutos"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lista2);
        spinnerTiempoSesion = findViewById(R.id.spinner_BuscarFotografo_Tiempo);
        spinnerTiempoSesion.setAdapter(adapter2);
        ratingBar = findViewById(R.id.ratingBar_BuscarFotografo_Rating);
        ratingBar.setRating( 3.0f);

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


    public void OnPressButtonBuscar(View view){

        double espera_distancia;
        int duracion;
        switch (spinnerTiempoSesion.getSelectedItem().toString()){
            case "5+ minutos":
                duracion = 5;
                break;
            case "15+ minutos":
                duracion = 15;
                break;
            case "25+ minutos":
                duracion = 25;
                break;
            default:
                duracion = 0;
                break;
        }
        switch (spinnerTiempoEspera.getSelectedItem().toString()){
            case "5+ minutos":
                espera_distancia = 0.5;
                break;
            case "15+ minutos":
                espera_distancia = 1;
                break;
            case "25+ minutos":
                espera_distancia = 1.8;
                break;
            default:
                espera_distancia = 0;
                break;
        }
        if(!(espera_distancia ==0) && !(duracion == 0)){
            Intent intent = new Intent(BuscarFotografoActivity.this, FotografosEncontradosActivity.class);
            intent.putExtra("rating", ratingBar.getRating());
            intent.putExtra("duracion", duracion);
            intent.putExtra("espera", espera_distancia);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Por favor llene los campos", Toast.LENGTH_SHORT).show();
        }


    }
}