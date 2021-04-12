package com.example.ctis210finalproj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

// This is the class for asteroids in the asteroid hunter game
public class Asteroid {
// Declaring and instantiating all our variables

    RectF rect; // Creates a rectangle based on four coordinate points that you pass in
    private Bitmap bitmap1; // This is a bitmap for the asteroid so that we can scale it and display in our main method for the game

    private float length; // These are length and height of the rectangles that hold each asteroid image
    private float height;

    private float x; // These are x and y coordinates
    private float y;

    private float speed; // This declares the speed

    public final int DOWN = 0; // instantiates down so we can use it later to move
    int direction = -1; // Sets the direction

    boolean isVisible; // declares visibility boolean

    public Asteroid(Context context, int column, int screenX, int screenY){
        rect = new RectF(); // creates new coordinates

        length = screenX / 20; // sets the length and height to the x and y of the screen's dimensions divided by 20
        height = screenY / 20;

        isVisible = true; // sets visibility to true

        int padding = screenX / 25;

        x = column * (length + padding); // finds x coordinate

        bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.asteroid1); // sets the bitmap asteroid picture

        bitmap1 = Bitmap.createScaledBitmap(bitmap1, (int) length, (int)height, false);

        speed = 40; // sets the speed of movements

    }

    // update method which moves the rectangles down
    public void update(long fps){
        if(direction == DOWN){
            y = y - speed / fps;
        }else{
            y = y + speed / fps;
        }

//      changes coorindates
        rect.left = x;
        rect.right = x+length;
        rect.top = y;
        rect.bottom = y + height;

        // this part increases speed slowly as it gets lower and lower
        speed = speed * 1.005f;
    }

// method that sets visibility to false

    public void setInvisible(){
        isVisible = false;
    }

    // gets the visibility
    public boolean getVisibility(){
        return isVisible;
    }

    // gets the rectangle coordinates
    public RectF getRect(){
        return rect;
    }

    // gets
    public Bitmap getBitmap(){
        return bitmap1;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }


}
