package com.example.ctis210finalproj;

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;

import androidx.annotation.RequiresApi;

public class AsteroidHunterActivity extends Activity {

    AsteroidHunterView view;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        view = new AsteroidHunterView(this, size.x, size.y);
        setContentView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onResume(){
        super.onResume();
        view.resume();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onPause(){
        super.onPause();
        view.pause();
    }
}
