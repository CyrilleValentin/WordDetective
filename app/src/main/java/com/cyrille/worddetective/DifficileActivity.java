package com.cyrille.worddetective;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DifficileActivity extends AppCompatActivity {
    int score =0 ;
    int vie=3;
    LinearLayout linearLayout;
    Button soumettre;
    TextInputEditText textInputEditText;
    private String motADeviner;
    private CountDownTimer timer;
    TextView textMotADeviner,scoreText,vieT;
    private TextView chronometreTextView;
    private CountDownTimer countdownTimer;
    String[] mots={"parapluie", "éléphant", "familial", "girafe", "machiner", "racinaire", "clavière", "faisceau", "boulevard", "japonais", "bibliothè", "cartable", "nuisible", "cahier", "tablette", "espérant", "diplôme", "bijouter", "montagne", "préparer", "activer", "endormir", "facteur", "guitare", "harmonie", "imprimer", "randonnée", "travailler", "climat", "question", "poussière", "fauteuil", "savonner", "déjeuner", "armoire", "fenêtre", "ordinate", "piscine", "football", "immeuble", "orchestre", "vacances", "valise", "lavabo", "chameau", "écureuil", "festival", "campagne", "portrait", "hiver", "orchidée", "pleurer", "sourire", "éclairage", "apprécier", "énergie", "toujours", "bienvenue", "aéroport", "décorer", "magasin", "santé", "batterie", "gâteau", "hamburger", "végétal", "aquarium", "parcours", "rêverie", "sculpture", "vétérinaire", "liberté", "abaisser", "affirmer", "baladeur", "calendrier", "dessiner", "tambour", "enchanteur", "festival", "galopant", "incroyable", "jalousie", "kilogramme", "librairie", "mammifère", "naviguer", "observateur", "parasol", "qualifié", "raccompagner", "salamandre", "télévision", "uranium", "vaisselier", "wagonnet", "xérophyte", "yogourt", "zodiaque", "aéroglisseur", "bénévole", "concombre", "dépenser", "élévation", "flamboyant", "gratuité", "héliport", "iconique", "jumelage", "kayakiste", "lévitation", "magnifique", "nauséeux", "optimiste", "provoquer", "quinconce", "recycler", "séduisant", "tablette", "utopique", "ventilateur", "wagon-lit", "xylographie", "yachtman", "zèbre", "abécédaire", "bénéfique", "congrès", "découverte", "éléphant", "fréquence", "géologie", "hélium", "iconoclaste", "jument", "kérosène", "logiciel", "magnétise", "négociant", "officier", "propre", "quartier", "reconnaître", "schéma", "tortue", "utopiste", "vacancier", "wagonnier", "xylophone", "yachting", "zirconium", "acoustique", "bibliothèque", "confiance", "déterminer", "espérance", "flamme", "graphique", "hydraulique", "intégrité", "joaillerie", "klaxonner", "logarithme", "mannequin", "nécessaire", "opportunité", "proverbe", "quintessence", "révolution", "symétrie", "transparence", "utilisation", "vagabonder", "wattmètre", "xénophobe", "yogiste", "zoologiste", "ambassadeur", "bénéficiaire", "concepteur", "démocratie", "éducation", "fidélité", "générosité", "hospitalier", "inspiration", "judiciaire", "kinésithérapie", "loyauté", "magnificence", "négociation", "opposition", "prévision", "qualification", "réalisation", "satisfaction", "technologie", "uniformité", "variabilité", "westphalien", "xérophtalmie", "yogini", "zootechnie"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficile);
        soumettre=findViewById(R.id.btn_submit);
        chronometreTextView = findViewById(R.id.chronometreTextView);
        textMotADeviner=findViewById(R.id.textMotADeviner);
        textInputEditText = findViewById(R.id.reponse);
        scoreText=findViewById(R.id.scoreTextView);
        vieT=findViewById(R.id.vieTextView);
        linearLayout = findViewById(R.id.layoutLettres);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.jeu);
        scoreText.setText("Score: " + score);
        vieT.setText("Vie :x" + vie);

        genererMotAleatoire();
        lancerCompteARebours();

        soumettre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reponse = textInputEditText.getText().toString().toLowerCase();
                Log.i("Reponse recherchee:", reponse);

                boolean motCorrect = true;
                for (int i = 0; i < motADeviner.length(); i++) {
                    if (i >= reponse.length() || motADeviner.charAt(i) != reponse.charAt(i)) {
                        motCorrect = false;
                        break;
                    }
                }

                if (motCorrect) {

                    Toast.makeText(DifficileActivity.this, "Bravo ! Vous avez deviné le mot.", Toast.LENGTH_SHORT).show();
                    incrementScore();
                    // Générer un nouveau mot
                    genererMotAleatoire();

                    // Relancer le compte à rebours
                    lancerCompteARebours();
                } else {
                    Toast.makeText(DifficileActivity.this, "Ce n'est pas le bon mot. Essayez encore.", Toast.LENGTH_SHORT).show();
                    decrementScore();
                }

                textInputEditText.getText().clear();
            }
        });


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int selectedItemId = item.getItemId();
            if (selectedItemId == R.id.jeu) {
                onPause();
                return true;
            } else if (selectedItemId == R.id.classe) {
                startActivity(new Intent(getApplicationContext(), ClassementActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (selectedItemId == R.id.parametre) {
                startActivity(new Intent(getApplicationContext(), ParametreActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (selectedItemId == R.id.compte) {
                startActivity(new Intent(getApplicationContext(), CompteActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else {
                return false;
            }

        });
    }
    // Méthode appelée lorsqu'une bonne réponse est trouvée
    private void incrementScore() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.audience);
        mediaPlayer.start();
        score =score+1;
        scoreText.setText("Score: " + score);
    }

    // Méthode appelée lorsqu'une mauvaise réponse est donnée
    private void decrementScore() {
        vie -= 1;
        vieT.setText("Vie:x" + vie);
        if (vie <= 0) {
            countdownTimer.cancel();
            showGameOverDialog();

        }
    }

    private void genererMotAleatoire() {
        linearLayout.removeAllViews();
        Random rand = new Random();
        int indexMot = rand.nextInt(mots.length);
        motADeviner = mots[indexMot];
        Log.i("mot:", motADeviner);
        StringBuilder motAffiche = new StringBuilder();
        for (int i = 0; i < motADeviner.length(); i++) {
            motAffiche.append("_ ");
        }

        textMotADeviner.setText(motAffiche.toString());

        List<Character> lettresMot = obtenirLettresMot(motADeviner);
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < lettresMot.size(); i++) {
            indices.add(i);
        }

// Mélangez la liste d'indices
        Collections.shuffle(indices);

// Parcourez la liste d'indices mélangée et ajoutez les TextView dans le LinearLayout
        for (int index : indices) {
            // Récupérer la lettre à l'index spécifié
            Character lettre = lettresMot.get(index);

            // Créer le TextView pour chaque lettre
            TextView textViewLettre = new TextView(this);
            textViewLettre.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textViewLettre.setTextSize(30);
            textViewLettre.setTextColor(Color.BLACK);
            textViewLettre.setPadding(8, 8, 8, 8);
            textViewLettre.setText(String.valueOf(lettre));

            // Ajouter le TextView dans le LinearLayout
            linearLayout.addView(textViewLettre);
        }

    }
    private List<Character> obtenirLettresMot(String mot) {
        List<Character> lettresMot = new ArrayList<>();

        for (int i = 0; i < mot.length(); i++) {
            char lettre = mot.charAt(i);
            lettresMot.add(lettre);
        }
        return lettresMot;
    }


    private void lancerCompteARebours() {
        if (countdownTimer != null) {
            countdownTimer.cancel();
        }

        long tempsTotal = 60000; // 60 secondes (ajustez selon vos besoins)

        countdownTimer = new CountDownTimer(tempsTotal, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondesRestantes = millisUntilFinished / 1000;
                String tempsRestant = String.format(Locale.getDefault(), "%02d:%02d", secondesRestantes / 60, secondesRestantes % 60);
                chronometreTextView.setText(tempsRestant);


            }

            @Override
            public void onFinish() {
                chronometreTextView.setText("Temps écoulé");

                // Arrêter le chronomètre
                cancel();

                // Actions à effectuer lorsque le temps est écoulé
                // Par exemple, réinitialiser le jeu ou afficher un message de fin de partie.
                Toast.makeText(DifficileActivity.this, "Nouveau mot Difficile", Toast.LENGTH_SHORT).show();
                genererMotAleatoire();
                lancerCompteARebours();

            }
        };

        countdownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Arrêter le compte à rebours lorsque l'activité est détruite
        if (timer != null) {
            timer.cancel();
            countdownTimer.cancel();
        }
    }
    @Override
    public void onBackPressed() {
        // Arrêter l'activité et quitter l'application
        timer.cancel();
        countdownTimer.cancel();
        finish();

    }
    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel(); // Arrêter le timer
    }

    private void showGameOverDialog() {

        // Vibrer le téléphone
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            // Jouer le son de "Game Over"
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.game_over);
            mediaPlayer.start();
            vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
        }

        //Afficher AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over")
                .setMessage("Votre score est inférieur ou égal à 0. Vous avez perdu !" +"Le mot qu'il fallait deviner est: "+motADeviner);
        builder.setPositiveButton("Nouvelle Partie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // L'utilisateur a choisi de continuer, vous pouvez ajouter ici le code pour recommencer le jeu
                // Exemple : recommencerJeu();
                vie = 3;
                vieT.setText("Vie:x" + vie);

                genererMotAleatoire();
                lancerCompteARebours();

            }
        });
        builder.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // L'utilisateur a choisi de ne pas continuer, vous pouvez ajouter ici le code pour terminer l'activité ou effectuer d'autres actions
                // Exemple : finish();
                countdownTimer.cancel();
                finish();
            }
        });
        builder.setCancelable(false); // Empêche l'utilisateur de fermer la boîte de dialogue en cliquant à l'extérieur

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



}

