package com.example.soloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soloapp.Clases.Cliente;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "Sebastian";
    FirebaseFirestore DBfirebase ;
    FirebaseUser user;
    EditText editTextNombre ;
    EditText editTextApellido ;
    EditText editTextPhone ;
    EditText editTextSesiones ;
    TextView textViewSesiones;
    EditText editTextCorreo ;
    Button button_Perfil_GuardarCambios ;
    Cliente cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
         DBfirebase = FirebaseFirestore.getInstance();
         user = FirebaseAuth.getInstance().getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.setTitle("Mi Perfil");
        if(user == null){
            Intent intent = new Intent();
            setResult(RESULT_CANCELED,intent);
            this.finish();
        }

         editTextNombre = findViewById(R.id.editTextNombre);
         editTextApellido = findViewById(R.id.editTextApellido);
         editTextPhone = findViewById(R.id.editTextPhone);
         editTextSesiones = findViewById(R.id.editTextSesiones);
         textViewSesiones = findViewById(R.id.textViewSesiones);
         editTextCorreo = findViewById(R.id.editTextCorreo);
         button_Perfil_GuardarCambios = findViewById(R.id.button_Perfil_GuardarCambios);



        //set texts
        DBfirebase.collection("users").document(user.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                cliente = documentSnapshot.toObject(Cliente.class);
                cliente.setId(documentSnapshot.getId());

                Log.d(TAG, "onSuccess: "+ cliente.toString());

                if(documentSnapshot.get("correo") != null){
                    editTextCorreo.setText(cliente.getCorreo());
                }
                if(documentSnapshot.get("numero") != null){
                    editTextPhone.setText(cliente.getNumero());
                }
                if(documentSnapshot.get("nombre") != null){
                    editTextNombre.setText(cliente.getNombre());
                }
                if(documentSnapshot.get("apellido") != null){
                    editTextApellido.setText(cliente.getApellido());
                }
            }})
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: Error en conseguir perfil", e.getCause());

                    }
                });
        //end set texts
    }

    public void guardarCambios(View view){
        if(!editTextApellido.getText().toString().isEmpty() && !editTextNombre.getText().toString().isEmpty() && !editTextPhone.getText().toString().isEmpty()){
            button_Perfil_GuardarCambios.setEnabled(false);
            //Map<String,Object> map = new HashMap<>();
            //map.put("nombre", editTextNombre.getText().toString());
            //map.put("apellido", editTextApellido.getText().toString());
            //map.put("numero", Integer.valueOf(editTextPhone.getText().toString()));
            cliente.setApellido(editTextApellido.getText().toString());
            cliente.setNombre(editTextNombre.getText().toString());
            cliente.setNumero(editTextPhone.getText().toString());
            cliente.setCorreo(editTextCorreo.getText().toString());


            DBfirebase.collection("users").document(user.getUid()).set(cliente)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i(TAG, "onSuccess: update profile");
                            button_Perfil_GuardarCambios.setEnabled(true);
                            Toast.makeText(ProfileActivity.this, "Datos Guardados Correctamente", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            button_Perfil_GuardarCambios.setEnabled(true);
                            Log.e(TAG, "onFailure: update profile", e.getCause() );
                        }
                    });
        }else{
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
        return;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}