package com.example.ctis210finalproj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;


@RequiresApi(api = Build.VERSION_CODES.R)
public class AsteroidHunterView extends SurfaceView implements Runnable {

    Context myContext;

    private Thread myThread = null;
    private SurfaceHolder myHolder;

    private volatile boolean playing;
    private boolean paused = true;

    private Canvas myCanvas;
    private Paint paint;

    private long fps;
    private long timeOfFrame;

    private int screenX;
    private int screenY;

    private HumanShip humanShip;
    private Bullet bullet;

    Asteroid[] asteroids = new Asteroid[11];
    int numAsteroids = 0;

    private boolean lost = false;
    private boolean won = false;

    int score = 0;
    int winScore;
    int loseScore;
    private int lives = 3;


    private boolean projectileSpeed = false;

    public AsteroidHunterView(Context myContext, int x, int y){
        super(myContext);
        this.myContext = myContext;

        myHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        startLevel();
    }

    private void startLevel(){
        humanShip = new HumanShip(myContext, screenX, screenY - 180);
        bullet = new Bullet(screenY);


        numAsteroids = 0;
        for(int column = 0; column < 11; column++){
                asteroids[numAsteroids] = new Asteroid(myContext, column, screenX, screenY);
                numAsteroids++;

        }
    }

    @Override
    public void run(){
        while(playing){
            long startFrameTime = System.currentTimeMillis();

            if(!paused){
                update();
            }

            draw();

            timeOfFrame = System.currentTimeMillis() - startFrameTime;
            if(timeOfFrame >= 1){
                fps = 1000/timeOfFrame;
            }

        }

    }

    int clicksleft = 0;

    private void update(){
        lost = false;
        won = false;

        humanShip.update(fps);

        // update asteroids if invisible
        for(int i = 0; i< numAsteroids; i++){
            if(asteroids[i].getVisibility()){
                asteroids[i].update(fps);
            }
        }


        for(int i = 0; i<numAsteroids; i++){
            if(asteroids[i].getY() > screenY - screenY / 10){
                startLevel();
            }
        }


        // update the players bullet
        if(bullet.getStatus()){
            bullet.update(fps);
        }

        if(bullet.getImpactPointY() < 0){
            bullet.setInactive();
        }


        if(bullet.getStatus()) {
            for (int i = 0; i < numAsteroids; i++) {
                if (asteroids[i].getVisibility()) {
                    if (RectF.intersects(bullet.getRect(), asteroids[i].getRect())) {
                        asteroids[i].setInvisible();
                        bullet.setInactive();
                        score = score + 10;

                        if(score == numAsteroids * 10){
                            winScore = score;
                            won = true;
                            paused = true;
                            score = 0;
                            lives = 3;
                            startLevel();
                        }
                    }
                }
            }
        }



        for(int i = 0; i < asteroids.length; i++) {
            if (asteroids[i].getVisibility()) {
                if (RectF.intersects(humanShip.getRect(), asteroids[i].getRect())) {
                    asteroids[i].setInvisible();
                    lives--;

                    if (lives <= 0) {
                        loseScore = score;
                        lost = true;
                        paused = true;
                        lives = 3;
                        score = 0;
                        startLevel();

                    }
                }
            }
        }

        if(clicksleft == 0){
            bullet.resetSpeed();
        }else if(clicksleft == 1){
            projectileSpeed = false;
        }
    }

    Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.backgroundgameone);
    private void draw(){
        if(myHolder.getSurface().isValid()){
            myCanvas = myHolder.lockCanvas();

            paint.setColor(Color.argb(255, 255, 255, 255));
            myCanvas.drawBitmap(background, 0, 0, paint);

            // draw ships
            myCanvas.drawBitmap(humanShip.getBitmap(), humanShip.getX(), screenY - 120, paint);

            // draw asteroids
            for(int i = 0; i < numAsteroids; i++){
                if(asteroids[i].getVisibility()) {
                    myCanvas.drawBitmap(asteroids[i].getBitmap(), asteroids[i].getX(), asteroids[i].getY(), paint);
                }
            }

            paint.setColor(Color.argb(255, 0, 255, 0));
            // draw player bullets
            if(bullet.getStatus()){
                myCanvas.drawRect(bullet.getRect(), paint);
            }

            // draw score and remaining lives
            paint.setColor(Color.argb(255, 249, 129, 0));
            paint.setTextSize(80);
            myCanvas.drawText("Score: " + score + "   Lives: " + lives, 50, 80, paint);

            if (lost) {
                paint.setColor(Color.argb(255, 255, 255, 255));
                myCanvas.drawBitmap(background, 0, 0, paint);
                paint.setColor(Color.argb(255, 249, 129, 0));
                paint.setTextSize(90);
                myCanvas.drawText("Score: " + loseScore + "   GAME OVER ", 50, 1000, paint);

            }

            if (won) {
                paint.setColor(Color.argb(255, 255, 255, 255));
                myCanvas.drawBitmap(background, 0, 0, paint);
                paint.setColor(Color.argb(255, 0, 255, 125));
                paint.setTextSize(90);
                myCanvas.drawText("Score: " + winScore + "   YOU WON!! ", 50, 1000, paint);

            }

            myHolder.unlockCanvasAndPost(myCanvas);


        }
    }

    public void pause(){
        playing = false;
        try{
            myThread.join();
        }catch (InterruptedException e){
            Log.e("Error:", "joining thread");
        }
    }

    public void resume(){
        playing = true;
        myThread = new Thread(this);
        myThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:

                paused = false;

                if(motionEvent.getY() > screenY - screenY / 7){
                    if(motionEvent.getX() > screenX / 2){
                        humanShip.setMovementState(humanShip.RIGHT);
                    }else{
                        humanShip.setMovementState(humanShip.LEFT);
                    }
                }
                if(motionEvent.getY() < screenY - screenY / 6 ){
                    // shoot
                    if(bullet.shoot(humanShip.getX()+humanShip.getLength()/2, screenY, bullet.UP)){
                        if(clicksleft > 0)
                            clicksleft--;
                    }
                }

                break;
            case MotionEvent.ACTION_UP:

                if(motionEvent.getY() > screenY - screenY ){
                    humanShip.setMovementState(humanShip.STOPPED);
                }

                break;
        }
        return true;
    }

}
