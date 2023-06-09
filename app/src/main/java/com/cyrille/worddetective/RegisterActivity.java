package com.cyrille.worddetective;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    FirebaseFirestore db;
    TextInputEditText email,name;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         db = FirebaseFirestore.getInstance();
        email=findViewById(R.id.email);
        name=findViewById(R.id.name);
        register=findViewById(R.id.btn_Register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String nameText = name.getText().toString();
                if (email.equals("") || name.equals(""))
                    Toast.makeText(RegisterActivity.this, "Tous les champs sont requis", Toast.LENGTH_SHORT).show();
                else {
                    Map<String, Object> user = new HashMap<>();
                    user.put("email", emailText);
                    user.put("name", nameText);
                    user.put("score", 0);
                    // Add a new document with a generated ID
                    db.collection("user")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(RegisterActivity.this, "Inscription Reussie", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                    Toast.makeText(RegisterActivity.this, "Echec d'Inscription", Toast.LENGTH_SHORT).show();
                                }
                            });
                    Intent myIntent = new Intent(RegisterActivity.this, HomeActivity.class);
                    RegisterActivity.this.startActivity(myIntent);
                }
            }
        });
    }
}