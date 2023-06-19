package com.cyrille.worddetective;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InscriptionActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputEditText email, name, pass;
    Button register;
    FirebaseDatabase database;
    DatabaseReference playersRef;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        playersRef = database.getReference("players");
        setContentView(R.layout.activity_inscription);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.password);
        register = findViewById(R.id.btn_Register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String nameText = name.getText().toString();
                String passText = pass.getText().toString();
                int score = 0;
                if (emailText.equals("") || passText.equals(""))
                    Toast.makeText(InscriptionActivity.this, "Tous les champs sont requis", Toast.LENGTH_SHORT).show();
                else {
                    mAuth.createUserWithEmailAndPassword(emailText, passText)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    if (firebaseUser != null) {
                                        String userId = firebaseUser.getUid();
                                        Player player = new Player(nameText, emailText, score);
                                        playersRef.child(userId).setValue(player)
                                                .addOnSuccessListener(aVoid -> {
                                                    // Enregistrement réussi dans Firebase Realtime Database
                                                    // Enregistrement de l'indicateur d'inscription dans les SharedPreferences
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putBoolean("IS_REGISTERED", true);
                                                    editor.apply();
                                                    // Effectuez les actions supplémentaires si nécessaire
                                                    startActivity(new Intent(InscriptionActivity.this, HomeActivity.class));
                                                    finish();
                                                })
                                                .addOnFailureListener(e -> {
                                                    // Erreur lors de l'enregistrement dans Firebase Realtime Database
                                                    // Gérez l'erreur de manière appropriée
                                                });
                                    }
                                } else {
                                    // Une erreur s'est produite lors de l'inscription, affichez un message d'erreur approprié
                                    Toast.makeText(InscriptionActivity.this, "Echec de l'inscription", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
