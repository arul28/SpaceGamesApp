package com.example.ctis210finalproj;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AlienAsteroidSpaceship extends AppCompatActivity {

//    In this part of the code we declare and instantiate most of our variables

    public int playerScore = 0;
    public int compScore = 0;

    final String LENGTH = "LENGTH"; // This line is here to get the length of the game, either best of 3, 5, or 7 from the IntroRules class

    public int moveUp = -400; // These lines specify how much the animation moves each Image
    public int moveDown = 400;
    final public int duration = 350; // duration refers to the duration of the animation movements

    //  Adding the images to an array for easier manipulation
    public ImageView choices[] = new ImageView[5];
    public ImageView compChoices[] = new ImageView[5];

    public int gameLength; // Creating a variable that we can set the game length to in the onCreate method.
//                            We declare it here so that it can be used in other parts of the class.

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alienasteroid); // Sets the view as specified by the alienasteroid.xml file.

        gameLength = getIntent().getIntExtra(LENGTH, 0); // Gets the value for LENGTH and instantiates gameLength with it

        TextView PlayerScore = (TextView) findViewById(R.id.playerscore); // These lines update the GUI score textviews with the actual scores
        TextView CompScore = (TextView) findViewById(R.id.compscore);

        PlayerScore.setText(String.valueOf(playerScore));
        CompScore.setText(String.valueOf(compScore));

        // Adding all the Images to the imageview lists
        choices[0] = (ImageView) findViewById(R.id.alien);
        choices[1] = (ImageView) findViewById(R.id.asteroid);
        choices[2] = (ImageView) findViewById(R.id.spaceship);
        choices[3] = (ImageView) findViewById(R.id.planet);
        choices[4] = (ImageView) findViewById(R.id.sun);

        compChoices[0] = (ImageView) findViewById(R.id.compalien);
        compChoices[1] = (ImageView) findViewById(R.id.compasteroid);
        compChoices[2] = (ImageView) findViewById(R.id.compspaceship);
        compChoices[3] = (ImageView) findViewById(R.id.compplanet);
        compChoices[4] = (ImageView) findViewById(R.id.compsun);

    }


    //In the xml each object is tied to a onClick event that is one of the following methods
    // In general each of these method takes the chosen image animates it up, then proceeds to pick a random choice
    // for the computer selection and calculates the score based on the computer choice versus the player choice
    // using the scoreCalculation method.

    public void choseAlien(View v) {
        choices[0].animate().translationY(moveUp).setDuration(duration);
        scoreCalculation(compMoveCalc(), 0);
    }

    public void choseAsteroid(View v) {
        choices[1].animate().translationY(moveUp).setDuration(duration);
        scoreCalculation(compMoveCalc(), 1);
    }

    public void choseSpaceship(View v) {
        choices[2].animate().translationY(moveUp).setDuration(duration);
        scoreCalculation(compMoveCalc(), 2);
    }

    public void chosePlanet(View v) {
        choices[3].animate().translationY(moveUp).setDuration(duration);
        scoreCalculation(compMoveCalc(), 3);
    }

    public void choseSun(View v) {
        choices[4].animate().translationY(moveUp).setDuration(duration);
        scoreCalculation(compMoveCalc(), 4);
    }


    // The scoreCalculation method takes input of the computer's choice as well as the player's choice and decides who wins and
    // Gives the winner one point, unless it is a tie, in that case no-one is given a point
    // It also animates the computer's choice and at the end of the method, it animates the old choices back to their starting locations
    // And finally it checks to see if the end condition is met with an if statement, and if it is, it runs the ending method.

    private void scoreCalculation(int compMove, int playerMove) {
        if (compMove == 0) compChoices[0].animate().translationY(moveDown).setDuration(duration);
        else if (compMove == 1)
            compChoices[1].animate().translationY(moveDown).setDuration(duration);
        else if (compMove == 2)
            compChoices[2].animate().translationY(moveDown).setDuration(duration);
        else if (compMove == 3)
            compChoices[3].animate().translationY(moveDown).setDuration(duration);
        else if (compMove == 4)
            compChoices[4].animate().translationY(moveDown).setDuration(duration);

        if (compMove != playerMove) {
            if (compMove == 0 && playerMove == 1) playerScore++;
            else if (compMove == 0 && playerMove == 2) compScore++;
            else if (compMove == 0 && playerMove == 3) compScore++;
            else if (compMove == 0 && playerMove == 4) playerScore++;

            else if (compMove == 1 && playerMove == 0) compScore++;
            else if (compMove == 1 && playerMove == 2) compScore++;
            else if (compMove == 1 && playerMove == 3) compScore++;
            else if (compMove == 1 && playerMove == 4) playerScore++;


            else if (compMove == 2 && playerMove == 0) playerScore++;
            else if (compMove == 2 && playerMove == 1) playerScore++;
            else if (compMove == 2 && playerMove == 3) compScore++;
            else if (compMove == 2 && playerMove == 4) compScore++;

            else if (compMove == 3 && playerMove == 0) playerScore++;
            else if (compMove == 3 && playerMove == 1) playerScore++;
            else if (compMove == 3 && playerMove == 2) playerScore++;
            else if (compMove == 3 && playerMove == 4) compScore++;

            else if (compMove == 4 && playerMove == 0) compScore++;
            else if (compMove == 4 && playerMove == 1) compScore++;
            else if (compMove == 4 && playerMove == 2) playerScore++;
            else if (compMove == 4 && playerMove == 3) playerScore++;

            // Score updates after each calculation on the GUI
            TextView PlayerScore = (TextView) findViewById(R.id.playerscore);//assigns the current score to FinalScoreValue text element in activity_end
            TextView CompScore = (TextView) findViewById(R.id.compscore);//assigns the string reason for the game ending to ReasonString text element in activity_end


            PlayerScore.setText(String.valueOf(playerScore));
            CompScore.setText(String.valueOf(compScore));

            if (playerScore == gameLength || compScore == gameLength) {
                ending(playerScore, compScore);
            }
        }

        for (int i = 0; i < choices.length; i++) {
            if (choices[i].getTranslationY() < 0 || compChoices[i].getTranslationY() > 0) {
                choices[i].animate().translationY(0).setDuration(duration);
                compChoices[i].animate().translationY(0).setDuration(duration);

            }
        }
    }

    // This method simply set's the view to two end screens based off of the result of the matches.
    // If the player won than a winners endscreen is shown and opposite for when the player loses.
    public void ending(int playerScore, int compScore) {
        if (playerScore > compScore) {
            setContentView(R.layout.activity_endv1);
        } else {
            setContentView(R.layout.activity_endv2);
        }

    }

    public int compMoveCalc() {
        if ((playerScore - compScore) > 2) {
            return 1;
        } else if ((compScore - playerScore) > 2) {
            return 3;
        } else {
            Random r = new Random();
            int compMove = r.nextInt(5);
            return compMove;
        }
    }
}
