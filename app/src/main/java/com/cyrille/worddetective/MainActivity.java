package com.cyrille.worddetective;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
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

public class MainActivity extends AppCompatActivity {
     int score =9 ;
     LinearLayout linearLayout;
    Button soumettre;
    TextInputEditText textInputEditText;
    private String motADeviner;
    private CountDownTimer timer;
    TextView textMotADeviner,scoreText;
    private TextView chronometreTextView;
    private CountDownTimer countdownTimer;
    String[] mots ={"abandon","abeille","accord","addition","agent","aimer","aider","air","album","aller","ami","amour","animal","année","appeler","apprendre","arbre","argent","arriver","art","article","as","ascenseur","attention","avancer","avion","avoir","bagage","baguette","bain","balade","balle","banane","bande","barbe","bateau","beau","bébé","beurre","bijou","blanc","bleu","boire","boîte","bon","bonbon","bord","bouteille","bras","bruit","brûler","cadeau","café","cage","caisse","calculer","calme","caméra","campagne","canard","capable","car","carotte","carte","casser","cause","ce","célébrer","centre","cerise","chambre","chance","changer","chanson","chat","chaud","chemin","cheval","chien","chiffre","chocolat","choisir","ciel","cinq","citron","clair","classe","clé","coeur","coin","colle","colorier","comme","comment","compter","copain","copine","corde","corps","côté","cou","couleur","courir","course","court","couteau","couvrir","crayon","croire","cuisine","cuisiner","cuivre","danger","dans","de","début","découvrir","dedans","déjeuner","demain","dent","départ","dernier","détester","deux","devant","devoir","différent","dimanche","dire","doigt","donner","dormir","douche","doux","droit","durer","école","écouter","écrire","effacer","éléphant","émotion","en","encore","endroit","enfant","enlever","entrer","équipe","espace","essayer","été","être","étudier","exemple","exister","expliquer","façon","facile","faire","famille","farine","fatigué","femme","fenêtre","ferme","fermer","feu","feuille","fille","film","fin","fleur","fois","folie","fond","force","forêt","fou","froid","fruit","garçon","garder","gauche","gâteau","geler","gens","girafe","glace","goutte","grain","grand","gras","gris","gros","groupe","habiller","halte","haut","heure","hier","histoire","hiver","homme","honte","hôpital","hôtel","idée","igloo","image","impossible","insecte","jardin","jaune","je","jeu","joie","jour","jouer","jus","juste","là","laine","langue","larme","laver","léger","lettre","levé","ligne","lit","livre","loin","long","longtemps","lourd","lumière","magasin","magie","maison","mal","maman","manger","manteau","marche","mardi","mariage","matin","mauvais","meilleur","mélodie","membre","même","mer","merci","métier","mettre","midi","miel","mieux","milieu","mille","minute","moins","moment","monde","monsieur","montrer","montre","montrer","mort","mot","mourir","mouvement","moyen","muet","murmure","musique","nature","neige","nez","niveau","noir","nombre","nom","nord","nourriture","nouveau","nuage","numéro","océan","oeil","oiseau","ombre","on","ou","oublier","oui","ouvrir","papa","par","parapluie","pareil","parfait","parler","partie","passé","patin","patte","pays","pêche","peindre","peluche","pendant","penser","perdre","permettre","personne","petit","peur","peut-être","pièce","pied","pierre","pilote","piscine","plage","plan","plante","plein","pleurer","pluie","plume","plus","poisson","point","poivre","police","pomme","pont","porte","poser","position","possible","poulet","pousser","pratiquer","premier","prendre","près","prêt","prix","problème","professeur","promener","prononcer","propre","protéger","prouver","puis","punir","quand","quartier","quatre","question","queue","qui","quitter","quoi","race","raconter","radio","raison","ramasser","rapide","rappeler","recevoir","reconnaître","rejeter","remercier","rencontrer","rendre","reprendre","respecter","retour","retrouver","réveiller","rêver","revivre","rire","robe","roi","roman","rouge","route","rouvrir","royaume","sable","sac","salle","salut","samedi","savoir","se","semaine","sembler","sentir","sept","serpent","serrer","service","seul","si","sifflet","signe","silence","simple","sirop","situation","soir","soit","sol","soldat","soleil","sommeil","son","sortir","souffle","souhaiter","sourire","sous","soutenir","souvenir","sujet","super","sûr","tandis","tant","taper","tard","tarte","téléphone","télévision","un","uniforme","unir","vacances","vache","valeur","vendredi","vent","verre","vers","vert","victoire","vider","vie","vieux","vivre","voici","voiture","voix","voler","vouloir","vue","wagon","zéro","zone"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soumettre=findViewById(R.id.btn_submit);
        chronometreTextView = findViewById(R.id.chronometreTextView);
        textMotADeviner=findViewById(R.id.textMotADeviner);
       textInputEditText = findViewById(R.id.reponse);
       scoreText=findViewById(R.id.scoreTextView);
      linearLayout = findViewById(R.id.layoutLettres);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.jeu);
        scoreText.setText("Score: " + score);


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

                    Toast.makeText(MainActivity.this, "Bravo ! Vous avez deviné le mot.", Toast.LENGTH_SHORT).show();
                   incrementScore();
                    // Générer un nouveau mot
                    genererMotAleatoire();

                    // Relancer le compte à rebours
                    lancerCompteARebours();
                } else {
                    Toast.makeText(MainActivity.this, "Ce n'est pas le bon mot. Essayez encore.", Toast.LENGTH_SHORT).show();
                    decrementScore();
               }

                textInputEditText.getText().clear();
            }
        });


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int selectedItemId = item.getItemId();
            if (selectedItemId == R.id.jeu) {
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
        score -= 3;
        scoreText.setText("Score: " + score);
        if (score <= 0) {
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
                Toast.makeText(MainActivity.this, "Nouveau mot", Toast.LENGTH_SHORT).show();
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
                .setMessage("Votre score est inférieur ou égal à 0. Vous avez perdu !");
        builder.setPositiveButton("Nouvelle Partie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // L'utilisateur a choisi de continuer, vous pouvez ajouter ici le code pour recommencer le jeu
                // Exemple : recommencerJeu();
                score = 9;
                scoreText.setText("Score: " + score);
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

