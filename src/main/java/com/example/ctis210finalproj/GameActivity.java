package com.example.ctis210finalproj;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
//importing all necessary libraries

public class GameActivity extends AppCompatActivity implements  View.OnClickListener {


    private int numOfElements;
    private  MemoryButton [] allButtons;
    private int [] allButtonsGraphicLocation;
    private int [] allButtonsGraphics;
    //creating array for the cards
    private MemoryButton selectButton1;
    private MemoryButton selectButton2;
    private boolean isBusy = false;
    final String LEVEL = "LEVEL";
    final String FOR_TIMER = "forTimer";
    private int gridSize;
    private int numOfSeconds;
    private EditText timer_txt;
    private int[] timerSeconds = {30, 90};
    private Timer timer;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        update();
        initForTimer(numOfSeconds + 1, timer_txt);
        GridLayout theGridLayout = (GridLayout)findViewById(R.id.grid_layout_for_all);
        gridSize = getIntent().getIntExtra(LEVEL,0);
        int numCol =gridSize;
        int numRow = gridSize;
        //the number of rows and columns is decided from the data that is passed from getIntent()
        this.numOfElements = numCol * numRow; //total number of cards is decided by rows*columns
        this.allButtons = new MemoryButton[numOfElements];
        this.allButtonsGraphics = new int [numOfElements/2];
        //since it is a matching game, each card has to have its pair. So the code does the total number of cards divided by 2 to get the total number of pictures the program should pull
        if(numRow == 4){
            memGridEasy();
        }else{
            memGridHard();
        }
        //if the game is 4x4 then it is easy, the code gets 8 pictures
        //if the game is 6x6 then it is hard, the code gets 18 pictures
        this.allButtonsGraphicLocation = new int [numOfElements];
        randomize(); //randomly shuffling
        initMemGrid(numRow,numCol,theGridLayout);


    }

    private void initForTimer(long numOfSeconds, EditText timerText) {
        timer = new Timer(numOfSeconds * 1000, 1000, timerText, this);
        timer.start();
    }
    //starts the timer


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initMemGrid(int numRow, int numCol, GridLayout theGridLayout){
        for (int row = 0; row < numRow ; row++){
            for(int col = 0 ; col <numCol ; col++){
                MemoryButton tempButton = new MemoryButton(this,row,col,allButtonsGraphics[allButtonsGraphicLocation[row *numCol +col]]);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                allButtons[row * numCol +col] = tempButton;
                theGridLayout.addView(tempButton);
            }
        }
    }

    protected void randomize(){
        Random rand = new Random();

        for (int i = 0; i < numOfElements ; i++ ){
            this.allButtonsGraphicLocation[i] = i % (numOfElements/2);
        }
        for (int i = 0; i < numOfElements ; i++ ){
            int temp = this.allButtonsGraphicLocation[i];
            if(numOfElements == 4){
                int swapIndex = rand.nextInt(4);
                allButtonsGraphicLocation[i] = allButtonsGraphicLocation[swapIndex];
                allButtonsGraphicLocation[swapIndex] = temp;
            }else if(numOfElements == 16){
                int swapIndex = rand.nextInt(16);
                allButtonsGraphicLocation[i] = allButtonsGraphicLocation[swapIndex];
                allButtonsGraphicLocation[swapIndex] = temp;
            }else{
                int swapIndex = rand.nextInt(24);
                allButtonsGraphicLocation[i] = allButtonsGraphicLocation[swapIndex];
                allButtonsGraphicLocation[swapIndex] = temp;
            }

        }
    }
    //the code randomly shuffles the cards by assigning a new random index to each card

    public void onBackPressed(){
        timer.cancel();
        helperForMenu();
    }

    private void update() {
        timer_txt = (EditText)findViewById(R.id.timer_game);
        int level = getIntent().getIntExtra(FOR_TIMER, 0);
        numOfSeconds = timerSeconds[level];
    }


    public void memGridEasy(){
        this.allButtonsGraphics[0] = R.drawable.button_1;
        this.allButtonsGraphics[1] = R.drawable.button_2;
        this.allButtonsGraphics[2] = R.drawable.button_3;
        this.allButtonsGraphics[3] = R.drawable.button_4;
        this.allButtonsGraphics[4] = R.drawable.button_5;
        this.allButtonsGraphics[5] = R.drawable.button_6;
        this.allButtonsGraphics[6] = R.drawable.button_7;
        this.allButtonsGraphics[7] = R.drawable.button_8;
    }
    //takes the first 8 pictures for the easy game mode, 4x4
    public void memGridHard(){
        this.allButtonsGraphics[0] = R.drawable.button_1;
        this.allButtonsGraphics[1] = R.drawable.button_2;
        this.allButtonsGraphics[2] = R.drawable.button_3;
        this.allButtonsGraphics[3] = R.drawable.button_4;
        this.allButtonsGraphics[4] = R.drawable.button_5;
        this.allButtonsGraphics[5] = R.drawable.button_6;
        this.allButtonsGraphics[6] = R.drawable.button_7;
        this.allButtonsGraphics[7] = R.drawable.button_8;
        this.allButtonsGraphics[8] = R.drawable.button_9;
        this.allButtonsGraphics[9] = R.drawable.button_10;
        this.allButtonsGraphics[10] = R.drawable.button_11;
        this.allButtonsGraphics[11] = R.drawable.button_12;
        this.allButtonsGraphics[12] = R.drawable.button_13;
        this.allButtonsGraphics[13] = R.drawable.button_14;
        this.allButtonsGraphics[14] = R.drawable.button_15;
        this.allButtonsGraphics[15] = R.drawable.button_16;
        this.allButtonsGraphics[16] = R.drawable.button_17;
        this.allButtonsGraphics[17] = R.drawable.button_18;
    }
    //takes the first 8 pictures for the hard game mode, 6x6
    private boolean ifWon() {
        for (int i = 0; i < numOfElements; i++) {
            if (allButtons[i].isEnabled()) {
                return false;
            }
        }
        return true;
    }
    //checks to see if all the pairs have been "enabled"

    private void backToMenu() {
        Handler tempHandler = new Handler();
        tempHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                helperForMenu();
            }
        }, 3000);

    }

    public void loss() {
        Toast.makeText(GameActivity.this, "Out of time!", Toast.LENGTH_LONG).show();
        helperForMenu();
    }
    //a toast pops up on the screen saying "Out of time!" (its the little pop up in the bubble towards the bottom of the screen)

    private void helperForMenu(){
        Intent intent = new Intent(GameActivity.this, MemoryMenu.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (isBusy) {
            return;
        }
        MemoryButton button = (MemoryButton) view;
        if (button.isMatched) {
            return;
        }
        if (selectButton1 == null) {
            selectButton1 = button;
            selectButton1.flip();
            return;
        }
        if (selectButton1.getId() == button.getId()) {
            return;
        }
        if (selectButton1.getFrontImageID() == button.getFrontImageID()) {//if the 2nd flipped card is the same as the first one then the code sets the pair status to true and takes them off the screen
            button.flip();
            button.setMatched(true);
            selectButton1.setMatched(true);

            selectButton1.setEnabled(false);
            button.setEnabled(false);
            selectButton1 = null;
            if (ifWon()) { //checks to see if all the cards have been matched
                GameActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GameActivity.this, "Winner", Toast.LENGTH_LONG).show();
                    }
                }); //if all the cards have been flipped then the timer stops and we see the toast on the bottom of the screen 
                timer.cancel();
                backToMenu();
            }
            return;

        } else {// if the cards are not the same then the card flips over again and its status is set as busy
            selectButton2 = button;
            selectButton2.flip();
            isBusy = true;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectButton2.flip();
                    selectButton1.flip();
                    selectButton1 = null;
                    selectButton2 = null;
                    isBusy = false;
                }
            }, 500);
        }
    }
}