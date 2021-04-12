package com.example.ctis210finalproj;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.GridLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

public class MemoryButton extends AppCompatButton {

    protected int row;
    protected int col;
    protected int frontImageID;

    protected boolean isFlipped = false;
    protected boolean isMatched = false;

    protected Drawable front;
    protected Drawable back;
    //declaring variables for the card matching games, such as rows, columns, the id of the images, if the card is flipped or not
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MemoryButton(Context context, int row, int col, int frontImageID ){
        super(context);

        this.row = row;
        this.col = col;
        this.frontImageID = frontImageID;

        front = context.getDrawable(frontImageID); //the front of the card is set as the image from drawable
        back = context.getDrawable(R.drawable.button_background); //teh back of the card is set as whatever "button_question" is

        setBackground(back);

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col));

        tempParams.width = (int) getResources().getDisplayMetrics().density * 80;
        tempParams.height = (int) getResources().getDisplayMetrics().density * 80;

        setLayoutParams(tempParams);
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public int getFrontImageID() {
        return frontImageID;
    }

    public void flip(){
        if(isMatched){
            return;
        }

        if(isFlipped){
            setBackground(back);
            isFlipped = false; //if a card is flipped, it changes the cards appearance to "back"
        }else{
            setBackground(front);
            isFlipped = true; //if a card is un-flipped, it changes the cards appearance to "front"
        }
    }
}
