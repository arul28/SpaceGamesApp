package com.example.ctis210finalproj;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button switchToFirstGame, switchToSecondGame, switchToThirdGame, switchToFourthGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        switchToFirstGame = findViewById(R.id.button_first);
        switchToFirstGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchGameOne();
            }
        });

        switchToSecondGame = findViewById(R.id.button_second);
        switchToSecondGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchGameTwo();
            }
        });

        switchToThirdGame = findViewById(R.id.button_third);
        switchToThirdGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchGameThree();
            }
        });

        switchToFourthGame = findViewById(R.id.button_fourth);
        switchToFourthGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchGameFour();
            }
        });
    }

    private void switchGameOne() {
        Intent switchActivityIntent = new Intent(this, AsteroidHunterActivity.class);
        startActivity(switchActivityIntent);
    }

    private void switchGameTwo() {
        Intent switchActivityIntent = new Intent(this, WhackaSpaceshipStartActivity.class);
        startActivity(switchActivityIntent);
    }

    private void switchGameThree() {
        Intent switchActivityIntent = new Intent(this, MemoryMenu.class);
        startActivity(switchActivityIntent);
    }

    private void switchGameFour() {
        Intent switchActivityIntent = new Intent(this, IntroRules.class);
        startActivity(switchActivityIntent);
    }

}