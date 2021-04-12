package com.example.ctis210finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class WhackaSpaceshipMainActivity extends AppCompatActivity { //The MainActivity Java Class contains all the code for the game logic, for the GUI elements see activity_main.xml, activity_start.xml and activity_end.xml for how the GUI was constructed

    int randAlien; //here we declare all of our static variables and objetcs
    private TextView realTimeValue; //these TextView objects are updated with the current values in the game and then displayed in the xml file
    private TextView realScoreValue;
    public int varScore = 0; // sets the initial score
    private int varLives = 5;// sets the initial number of lives
    final Handler handler = new Handler(); // defines a handler to run the alienLoop
    private int duration = 60000; // sets duration of the game to 60 seconds
    private long stepTime = 1000; // sets the time between values as 1 second
    public int  timeInterval= 1000;// sets the time between alienLoop runs as 1 second
    public int alienPopUpTime = 450;// sets the duration for the aliens visible on the screen
    public CountDownTimer minuteClock = new gameClock(duration, stepTime);
    public ImageView alienImagesArray[] = new ImageView[9];
    public int yValue;
    public int diffCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whackanalien_main);//takes the layout from activity_main and sets it as the view on the screen

        realTimeValue = (TextView) findViewById(R.id.timevalue);//assigns the realTimeValue to the timevalue text element in activity_main
        realScoreValue = (TextView) findViewById(R.id.scorevalue);// assigns the realScoreValue to the scorevalue text element in acitvity_main

        minuteClock.start();//starts the timer
        handler.post(alienLoop);//our handler runs the alienLoop

        alienImagesArray[0] = (ImageView) findViewById(R.id.alienImage1);//sets each of the alienImages in activity_main as a value in the alienImagesArray
        alienImagesArray[1] = (ImageView) findViewById(R.id.alienImage2);
        alienImagesArray[2] = (ImageView) findViewById(R.id.alienImage3);
        alienImagesArray[3] = (ImageView) findViewById(R.id.alienImage4);
        alienImagesArray[4] = (ImageView) findViewById(R.id.alienImage5);
        alienImagesArray[5] = (ImageView) findViewById(R.id.alienImage6);
        alienImagesArray[6] = (ImageView) findViewById(R.id.alienImage7);
        alienImagesArray[7] = (ImageView) findViewById(R.id.alienImage8);
        alienImagesArray[8] = (ImageView) findViewById(R.id.alienImage9);

        Intent intent = getIntent();
        diffCounter = intent.getExtras().getInt("diffCounter");

        yValue = -300;//sets the translation value for the aliens to move up on the screen

    }

    public void EndGame(int EndScore, String Reason) { //this method is called on when either the timer ends or the user's lives run out

        setContentView(R.layout.activity_end);//sets the new view as the activity_end layout

        minuteClock.cancel();//stops the timer

        TextView ScoreCheck = (TextView) findViewById(R.id.FinalScoreValue);//assigns the current score to FinalScoreValue text element in activity_end
        TextView ReasonCheck = (TextView) findViewById(R.id.ReasonString);//assigns the string reason for the game ending to ReasonString text element in activity_end


        ScoreCheck.setText(String.valueOf(EndScore));//sets ScoreCheck as the final score


        ReasonCheck.setText(Reason);//sets ReasonCheck as the reason for the game ending


    }

    public class gameClock extends CountDownTimer {//creates gameClock class that extends android studio classCountDownTimer
        public gameClock(int duration, long stepTime) {//creates gameClock object
            super(duration, stepTime);

        }


        public void onTick(long millisUntilFinished) {

            realTimeValue.setText(String.valueOf(millisUntilFinished / 1000));//after each second has passed update the realTImeValue

            if (((millisUntilFinished/1000)%5 == 0) && (millisUntilFinished/1000) != 60){//decreases the time interval and speed of the aliens after every 5 seconds
               setDifficulty();
            }

        }

        public void onFinish() { //when the timer finishes, the endgame method is ran which brings up the final screen
            this.cancel();
            String messageTime = getString(R.string.outoftime);//sets the reason string to the relevant message, the string is set from the strings.xml file
            EndGame(varScore, messageTime);

        }
    }

    public void setDifficulty(){

        if(diffCounter == 1) {
            timeInterval *= .99;
            alienPopUpTime *= .99;
        }

        if(diffCounter == 2) {
            timeInterval *= .95;
            alienPopUpTime *= .95;
        }

        if(diffCounter == 3) {
            timeInterval *= .9;
            alienPopUpTime *= .9;
        }


    }
    public Runnable alienLoop = new Runnable() {//creates alienLoop as a runnable object

        int prevRandAlien = 10; //initializes variable used in the run method

        @Override
        public void run () {//runs and sets up the thread for the alienLoop runnable

            randAlien = new Random().nextInt(8);// sets a random value as the next alien that pops up and ensures the same alien does not pop up
            while (randAlien == prevRandAlien) {
                randAlien = new Random().nextInt(8);
            }
            prevRandAlien = randAlien;// sets the current alien that popped up as the new prevRandAlien

            alienImagesArray[randAlien].animate().translationY(yValue).setDuration(alienPopUpTime);//takes an alien out of the array and animates it by moving it up the GUI

            new Timer().schedule(new TimerTask() {// creates a runnable task that is repeated every time interval
                public void run() {//runs and sets up a new thread for the TimerTask

                    for (int i = 0; i < 9; i++) {// creates a for loop that goes through each alien
                        if (alienImagesArray[i].getTranslationY() == yValue) {//checks if the alien is still up, if an alien has already been clicked then the TimerTask should not move on

                            final int j = i;

                            runOnUiThread(new Runnable() {// creates a runnable that runsOnUi thread in order to update and animate the gui with other processes in the background
                                @Override// overrides the previous runnable to avoid runtime errors
                                public void run() {
                                    alienImagesArray[j].animate().translationY(0).setDuration(5);//translates the alien back down
                                }
                            });

                            varLives -= 1;//if the TimerTask ran, that meant the player did not click the alien before the end of the task so 1 life is subtracted
                            updateLives(varLives);//runs method to update lives on the GUI

                        }
                    }

                }//end of the definition of the TimerTask
            }, timeInterval); // parameter for the schedule

            handler.postDelayed(alienLoop, timeInterval);//creates alienLoop as a recursive loop that calls on its self again after the timeInterval (one second)
        }
    };


    public void updateLives(int Lives){//this method updates the GUI for the blasters in the top left of the screen

        final ImageView live1= (ImageView) findViewById(R.id.blasterImage1);//sets the blasters as imageview objects
        final ImageView live2= (ImageView) findViewById(R.id.blasterImage2);
        final ImageView live3= (ImageView) findViewById(R.id.blasterImage3);
        final ImageView live4= (ImageView) findViewById(R.id.blasterImage4);
        final ImageView live5= (ImageView) findViewById(R.id.blasterImage5);


        if (Lives == 4){ //if lives are equal to a certain value
            runOnUiThread(new Runnable() {//a new runnable is made so the GUI is updated
                @Override
                public void run() {
                    if (live5 != null){//if anything is in the spot for live5 then set live5 to invisible, every other conditional repeats this process
                        live5.setVisibility(View.INVISIBLE);
                    }
                }
            });
        } else if (Lives == 3){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (live4 != null) {
                        live4.setVisibility(View.INVISIBLE);
                    }
                }
            });
        } else if (Lives == 2) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (live3 != null){
                        live3.setVisibility(View.INVISIBLE);
                    }
                }
            });
        } else if (Lives == 1){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (live2 != null) {
                        live2.setVisibility(View.INVISIBLE);
                    }
                }
            });
        } else if (Lives == 0){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (live1 != null) {
                        live1.setVisibility(View.INVISIBLE);
                    }
                }
            });
            String messageLives = getString(R.string.outoflives); //sets the string to the appropriate message// if lives == 0 then a string is assigned to the endgame methods and is ran within a runnable
                runOnUiThread(new Runnable(){//these runnables are necessary as without them the code would run the function or method given but not update the gui, in this case the game would close
                    @Override
                    public void run (){
                        EndGame(varScore, messageLives);
                    }
                });
        }
    }




    public void onClick(View v) {//defines the onClick method that is assigned to the alienImages in activity_main file

        switch(v.getId()) {//uses a switch case and defines multiple cases for each alienImage
            case R.id.alienImage1://for alienImage1, if it has been moved up, move the image back down to the original postion
                if (alienImagesArray[0].getTranslationY() < 0) {
                    alienImagesArray[0].animate().translationY(0).setDuration(20);
                    directHit();// runs the direct hit method
                }
                break;
            case R.id.alienImage2:// the same logic is repeated for each case
                if (alienImagesArray[1].getTranslationY() < 0) {
                    alienImagesArray[1].animate().translationY(0).setDuration(20);
                    directHit();
                }
                break;
            case R.id.alienImage3:
                if (alienImagesArray[2].getTranslationY() < 0) {
                    alienImagesArray[2].animate().translationY(0).setDuration(20);
                    directHit();
                }
                break;
            case R.id.alienImage4:
                if (alienImagesArray[3].getTranslationY() < 0) {
                    alienImagesArray[3].animate().translationY(0).setDuration(20);
                    directHit();
                }
                break;
            case R.id.alienImage5:
                if (alienImagesArray[4].getTranslationY() < 0) {
                    alienImagesArray[4].animate().translationY(0).setDuration(20);
                    directHit();
                }
                break;
            case R.id.alienImage6:
                if (alienImagesArray[5].getTranslationY() < 0) {
                    alienImagesArray[5].animate().translationY(0).setDuration(20);
                    directHit();
                }
                break;
            case R.id.alienImage7:
                if (alienImagesArray[6].getTranslationY() < 0) {
                    alienImagesArray[6].animate().translationY(0).setDuration(20);
                    directHit();
                }
                break;
            case R.id.alienImage8:
                if (alienImagesArray[7].getTranslationY() < 0) {
                    alienImagesArray[7].animate().translationY(0).setDuration(20);
                    directHit();
                }
                break;
            case R.id.alienImage9:
                if (alienImagesArray[8].getTranslationY() < 0) {
                    alienImagesArray[8].animate().translationY(0).setDuration(20);
                    directHit();
                }
                break;
        }
    }

    public void directHit(){//defines the directhit method, adds to the varScore and updates the realScoreValue
        varScore += 250;
        realScoreValue.setText(String.valueOf(varScore));
    }

}