package com.cyrille.worddetective;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClassementActivity extends AppCompatActivity {

    private ListView leaderboardListView;
    private List<Player> playersList;
    private PlayerAdapter playerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classement);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.classe);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int selectedItemId = item.getItemId();
            if (selectedItemId == R.id.classe) {
                return true;
            } else if (selectedItemId == R.id.jeu) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
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



        leaderboardListView = findViewById(R.id.list);
        playersList = new ArrayList<>();

        DatabaseReference playersRef = FirebaseDatabase.getInstance().getReference("players");
        playersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                playersList.clear();
                for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                    Player player = playerSnapshot.getValue(Player.class);
                    if (player != null) {
                        playersList.add(player);
                    }
                }

                // Trier la liste des joueurs par score décroissant
                Collections.sort(playersList, new Comparator<Player>() {
                    @Override
                    public int compare(Player p1, Player p2) {
                        return Integer.compare(p2.getScore(), p1.getScore());
                    }
                });

                playerAdapter = new PlayerAdapter(ClassementActivity.this, R.layout.list_item_player, playersList);
                leaderboardListView.setAdapter(playerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gérer les erreurs éventuelles
            }
        });

    }
}