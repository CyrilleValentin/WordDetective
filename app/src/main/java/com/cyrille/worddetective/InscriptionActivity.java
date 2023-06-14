package com.cyrille.worddetective;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InscriptionActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputEditText email,name,pass;
    Button register;
    FirebaseFirestore db;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        CollectionReference usersRef = db.collection("users");
        email=findViewById(R.id.email);
        name=findViewById(R.id.name);
        pass=findViewById(R.id.password);
        register=findViewById(R.id.btn_Register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String nameText = name.getText().toString();
                String passText = pass.getText().toString();
                int score =0;
                if (email.equals("") || pass.equals(""))
                    Toast.makeText(InscriptionActivity.this, "Tous les champs sont requis", Toast.LENGTH_SHORT).show();
                else {
                    mAuth.createUserWithEmailAndPassword(emailText, passText)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {

                                    // L'inscription a réussi, vous pouvez rediriger l'utilisateur vers la prochaine étape
                                    Toast.makeText(InscriptionActivity.this, "Inscription Réussie", Toast.LENGTH_SHORT).show();

                                    // Enregistrement des informations
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("email", emailText);
                                    userData.put("username", nameText);
                                    userData.put("score", score);

                                    String userId = mAuth.getCurrentUser().getUid();
                                    usersRef.document(userId).set(userData)
                                            .addOnSuccessListener(documentReference -> {
                                                // Les données du joueur ont été enregistrées avec succès dans Firestore

                                                // Enregistrement de l'indicateur d'inscription dans les SharedPreferences
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putBoolean("IS_REGISTERED", true);
                                                editor.apply();

                                                // Redirection vers l'activité principale
                                                startActivity(new Intent(InscriptionActivity.this, HomeActivity.class));
                                                finish();
                                            })
                                            .addOnFailureListener(e -> {
                                                // Une erreur s'est produite lors de l'enregistrement des données du joueur
                                                Log.w(TAG, "Error adding document", e);
                                            });
                                    finish();

                                } else {
                                    // Une erreur s'est produite lors de l'inscription, affichez un message d'erreur approprié
                                    Toast.makeText(InscriptionActivity.this, "Echec de l'inscription", Toast.LENGTH_SHORT).show();
                                }
                            });
                    // Vérification si l'utilisateur est déjà inscrit

                        boolean isRegistered = sharedPreferences.getBoolean("IS_REGISTERED", false);
                        if (isRegistered) {
                            // L'utilisateur est déjà inscrit, rediriger vers l'activité principale
                            startActivity(new Intent(InscriptionActivity.this, HomeActivity.class));
                            finish(); // Fermer l'activité d'inscription pour empêcher l'utilisateur de revenir en arrière
                        }
                    }

            }

        });


    }
}