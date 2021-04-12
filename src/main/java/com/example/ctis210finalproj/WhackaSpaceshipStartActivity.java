package com.example.ctis210finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WhackaSpaceshipStartActivity extends AppCompatActivity {//This java class creates the start screen where the user selects difficulty, it is set to run first in the app > manifests > AndroidManifest.xml file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);//sets the activity_start layout as the view

        Button easyButton=(Button) findViewById(R.id.easyButton);//defines each button in the xml as a button object in this class
        Button mediumButton=(Button) findViewById(R.id.mediumButton);
        Button hardButton=(Button) findViewById(R.id.hardButton);

        easyButton.setOnClickListener(new View.OnClickListener() {// for each button, creates a listener
            @Override
            public void onClick(View v) {//onClick of the button create an intent to send to WhackaSpacechipActivity
                Intent intent = new Intent(getApplicationContext(), WhackaSpaceshipMainActivity.class);
                intent.putExtra("diffCounter",1);// for each button pressed, a different value is assigned to the diffCounter variable
                startActivity(intent);//starts the activity declared in the intent object
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WhackaSpaceshipMainActivity.class);
                intent.putExtra("diffCounter",2);
                startActivity(intent);
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WhackaSpaceshipMainActivity.class);
                intent.putExtra("diffCounter",3);
                startActivity(intent);
            }
        });



    }

}
