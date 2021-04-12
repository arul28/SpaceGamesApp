package com.example.ctis210finalproj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class HumanShip {

    RectF rect;

    private Bitmap bitmap;

    private float length;
    private float height;

    private float x;
    private float y;
    private float speed;

    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    private int moving = STOPPED;

    private int screenX;

    public HumanShip(Context context, int screenX, int screenY){
        rect = new RectF();
        this.screenX = screenX;

        length = screenX / 12;
        height = screenY / 17;

        x = screenX / 2;
        y = screenY - 20;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int)length, (int)height, false);

        speed = 350;
    }

    public void update(long fps){
        if(moving == LEFT){
            x = x - speed / fps;
        }

        if(moving == RIGHT){
            x = x + speed / fps;
        }

        if(getX() > screenX - getLength() || getX() < 0){
            moving = STOPPED;
        }

        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;
    }

    public RectF getRect(){
        return rect;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public float getX(){
        return x;
    }

    public float getLength(){
        return length;
    }

    public void setMovementState(int state){
        moving = state;
    }


}
