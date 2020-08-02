package com.example.soloapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginRegisterActivity extends AppCompatActivity {

    FirebaseUser user;
    int AUTHUI_REQUEST_CODE = 10001;
    private static final String TAG = "Sebastian";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Intent intent = new Intent(this, MainCustomerActivity.class);
            startActivity(intent);
            this.finish();
        }

    }

    public void handleLoginRegister(View view){
        List<AuthUI.IdpConfig> provider = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(provider)
                .setAlwaysShowSignInMethodScreen(true)
                .build();

        startActivityForResult(intent, AUTHUI_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTHUI_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                // We have signed in the user or created a new user
                FirebaseUser user0 = FirebaseAuth.getInstance().getCurrentUser();
                Log.i(TAG, "onActivityResult: create "+ user0.getMetadata().getCreationTimestamp());
                Log.i(TAG, "onActivityResult: last sign in "+ user0.getMetadata().getLastSignInTimestamp());
                if( (user0.getMetadata().getLastSignInTimestamp() - user0.getMetadata().getCreationTimestamp()) < 20){
                    // this is a new User

                    //startforresult getdatos activity
                    Map<String,Object> map = new HashMap<>();
                    map.put("correo", user0.getEmail());

                    FirebaseFirestore.getInstance().collection("users").document(user0.getUid()).set(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.i(TAG, "onSuccess: se creo un nuevo usuario con id " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e(TAG, "onFailure: ocurrio un error con la creacion del usuario en database", e.getCause() );

                                }
                            });
                    Log.d("Sebastian", "nuevo usuario");
                    Toast.makeText(this, "Bienvenido nuevo usuario", Toast.LENGTH_SHORT).show();

                }else{
                    Log.d("Sebastian", "usuario antiguo logeado");
                    Toast.makeText(this, "Bienvenido de nuevo", Toast.LENGTH_SHORT).show();
                }
                //Empezar la actividad Main
                Intent intent = new Intent(this, MainCustomerActivity.class);
                startActivity(intent);
                this.finish();

            }else{
                //sign in failed
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if(response == null){
                    //the user cancelled the sign in request
                    Log.d("Sebastian", "onActivityResult: the user cancelled the sign in request");
                }else{
                    Log.e("Sebastian", "onActivityResult: ", response.getError());
                }
            }
        }
    }
}