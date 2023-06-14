package com.cyrille.worddetective;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashscreenActivit extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 2000; // Durée de l'écran de démarrage en millisecondes
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Vérification si l'utilisateur est déjà inscrit
                boolean isRegistered = sharedPreferences.getBoolean("IS_REGISTERED", false);
                if (isRegistered) {
                    // L'utilisateur est déjà inscrit, rediriger vers l'activité principale
                    startActivity(new Intent(SplashscreenActivit.this, HomeActivity.class));
                } else {
                    // L'utilisateur n'est pas encore inscrit, rediriger vers l'activité d'inscription
                    startActivity(new Intent(SplashscreenActivit.this, InscriptionActivity.class));
                }

                finish(); // Fermer l'activité de démarrage pour empêcher l'utilisateur de revenir en arrière
            }
        }, SPLASH_TIMEOUT);
    }
}