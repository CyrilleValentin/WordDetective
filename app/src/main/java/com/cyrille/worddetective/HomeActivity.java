package com.cyrille.worddetective;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.cyrille.worddetective.MainActivity;

public class HomeActivity extends AppCompatActivity {
   public Button btn_facile,btn_difficile,btn_normal,btn_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_facile=findViewById(R.id.facile);
        btn_difficile=findViewById(R.id.difficile);
        btn_normal=findViewById(R.id.normal);
        btn_exit=findViewById(R.id.exit);
        btn_facile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, MainActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });
        btn_difficile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, DifficileActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });
        btn_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, NormalActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}