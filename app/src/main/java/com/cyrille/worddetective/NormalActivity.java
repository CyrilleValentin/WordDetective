package com.cyrille.worddetective;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class NormalActivity extends AppCompatActivity {
    int score =0 ;
    int vie=3;
    LinearLayout linearLayout;
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    Button soumettre;
    TextInputEditText textInputEditText;
    private String motADeviner;
    private CountDownTimer timer;
    TextView textMotADeviner,scoreText,vieT;
    private TextView chronometreTextView;
    private CountDownTimer countdownTimer;
    String[] mots={"course", "vente", "salade", "soleil", "hôtel", "table", "nature", "orange", "village", "poulet", "hiver", "beauté", "passer", "souris", "musée", "cinéma", "argent", "amoureux", "ananas", "sieste", "pluie", "animal", "douche", "plante", "gâteau", "papier", "poisson", "jardin", "camping", "peintre", "voyage", "écrire", "calme", "farine", "fleur", "métro", "piano", "astéro", "bougie", "fraise", "légume", "randonn", "volcan", "rivière", "pétale", "citron", "banane", "famille", "fête", "groupe", "chante", "blague", "pièce", "course", "fraise", "couple", "fruits", "avion", "amour", "gloire", "héros", "île", "jungle", "kiosque", "larme", "mystère", "nuance", "ombre", "pouvoir", "quartier", "rêve", "soleil", "travail", "union", "vélo", "wagon", "xylophone", "yoga", "zoo", "amusant", "blanche", "cahier", "délice", "écho", "faucon", "globe", "hôpital", "image", "jupe", "kaki", "lampe", "machine", "noble", "orange", "papillon", "quitter", "rose", "simple", "tableau", "utile", "vague", "wagon", "xylophone", "yeux", "zebra", "acacia", "bambou", "cascade", "délicieux", "élégant", "flamme", "gorille", "hibou", "intelligent", "jouet", "kiwi", "lentille", "mango", "noix", "oasis", "pamplemousse", "quartz", "raisin", "salade", "tamis", "univers", "vanille", "walrus", "xylophagous", "yacht", "zeppelin", "abord", "brouillard", "cloche", "décor", "effort", "fille", "grille", "hache", "indice", "joker", "klaxon", "lance", "moule", "nuage", "oasis", "pamplemousse", "quart", "rouleau", "sable", "toile", "usage", "vase", "wagon", "xylem", "yoga", "zèbre", "arcade", "bonnet", "cercle", "délice", "effet", "farine", "grappe", "hache", "idée", "joie", "kiwi", "lampe", "moule", "nouveau", "orage", "paix", "quille", "rouleau", "sable", "tiroir", "utile", "vase", "wagon", "xylophone", "yoga", "zèbre"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        soumettre=findViewById(R.id.btn_submit);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
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

                    Toast.makeText(NormalActivity.this, "Bravo ! Vous avez deviné le mot.", Toast.LENGTH_SHORT).show();
                    incrementScore();
                    // Générer un nouveau mot
                    genererMotAleatoire();

                    // Relancer le compte à rebours
                    lancerCompteARebours();
                } else {
                    Toast.makeText(NormalActivity.this, "Ce n'est pas le bon mot. Essayez encore.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(NormalActivity.this, "Le mot à deviner était : "+motADeviner, Toast.LENGTH_LONG).show();
               Toast.makeText(NormalActivity.this,  "Nouveau mot Normal", Toast.LENGTH_SHORT).show();
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
        finish();
        timer.cancel();
        countdownTimer.cancel();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //timer.cancel(); // Arrêter le timer
        countdownTimer.cancel();
    }

    private void showGameOverDialog() {

        // Vibrer le téléphone
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            // Jouer le son de "Game Over"
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.game_over);
            mediaPlayer.start();
            vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
            int nouveauScore = 100;


        }

        //Afficher AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over")
                .setMessage("Votre vie est inférieur ou égal à 0. Vous avez perdu !" +"Le mot qu'il fallait deviner est: "+motADeviner);
        String text = scoreText.getText().toString();
        String numericText = text.replaceAll("[^0-9]", "");
        int newScore =0;
        newScore = Integer.parseInt(numericText);
        updateScoreIfNeeded(newScore);
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

    private void updateScoreIfNeeded(int newScore) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            db.child("players").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Player player = dataSnapshot.getValue(Player.class);
                    if (player != null && newScore > player.getScore()) {
                        dataSnapshot.getRef().child("score").setValue(newScore);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Gérer les erreurs éventuelles
                }
            });



}
    }

}

