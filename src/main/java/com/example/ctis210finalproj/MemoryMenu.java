package com.example.ctis210finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MemoryMenu extends AppCompatActivity {
    // declaring variables
    final String LEVEL = "LEVEL";
    final int EASY = 0;
    final int HARD = 1;
    //declaring easy and hard game modes
    final String FOR_TIMER = "forTimer";

    private Button button4x4;
    private Button button6x6;
    //declaring the buttons for easy and hard game mode




    @Override
    protected void onCreate(Bundle savedInstanceState) { //calls saved InstanceState
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        button4x4 = (Button)findViewById(R.id.button_4x4_game);
        button6x6 = (Button)findViewById(R.id.button_6x6_game);
        //looks in the xml file for the buttons for easy and hard game settings


        button4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoryMenu.this,GameActivity.class);
                intent.putExtra(LEVEL,4);
                intent.putExtra(FOR_TIMER,EASY);
                startActivity(intent);
            }
        });
        //links the listener method to the easy game button

        button6x6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoryMenu.this,GameActivity.class);
                intent.putExtra(LEVEL,6);
                intent.putExtra(FOR_TIMER,HARD);
                startActivity(intent);
            }
        });
        //links the listener method to the hard game button

    }


}
