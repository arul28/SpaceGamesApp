package com.example.ctis210finalproj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
public class IntroRules extends Activity {
    // Declaring our buttons and a string which will be used to pass forward our choice of the length of the gsme to the game java class

    Button bestof3, bestof5, bestof7;

    final String LENGTH = "LENGTH";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        The class set's the view to the rules.xml file to display the rules of the game and to display 3 buttons at the bottom which
//        let you decide the game's length
        setContentView(R.layout.rules);


        // Each button gives you an option between best of 3, 5, and 7
        bestof3 = findViewById(R.id.bestof3);
        bestof3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchBestof3();
            }
        });

        bestof5 = findViewById(R.id.bestof5);
        bestof5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchBestof5();
            }
        });

        bestof7 = findViewById(R.id.bestof7);
        bestof7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchBestof7();
            }
        });

    }


    // These methods switch the activity from the rules to the game itself
    // They also put forward the values of the gameLength to the next classws5

    private void switchBestof3() {
        Intent switchActivityIntent = new Intent(this, AlienAsteroidSpaceship.class);
        switchActivityIntent.putExtra(LENGTH,2);
        startActivity(switchActivityIntent);
    }

    private void switchBestof5() {
        Intent switchActivityIntent = new Intent(this, AlienAsteroidSpaceship.class);
        switchActivityIntent.putExtra(LENGTH, 3);
        startActivity(switchActivityIntent);

    }

    private void switchBestof7() {
        Intent switchActivityIntent = new Intent(this, AlienAsteroidSpaceship.class);
        switchActivityIntent.putExtra(LENGTH, 4);
        startActivity(switchActivityIntent);
    }

}