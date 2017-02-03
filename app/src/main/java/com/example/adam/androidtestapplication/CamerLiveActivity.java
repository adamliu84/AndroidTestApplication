package com.example.adam.androidtestapplication;

//Reference: http://www.androidcodec.com/android-studio-surfaceview-example/

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceHolder;
import android.view.View;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CamerLiveActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    @SuppressWarnings("deprecation")
    Camera camera; // camera class variable
    SurfaceView camView; // drawing camera preview using this variable
    SurfaceHolder surfaceHolder; // variable to hold surface for surfaceView which means display
    boolean camCondition = false;  // conditional variable for camera preview checking and set to false
    Button cap;    // image capturing button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camer_live);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // getWindow() to get window and set it's pixel format which is UNKNOWN
        getWindow().setFormat(PixelFormat.UNKNOWN);
        // refering the id of surfaceView
        camView = (SurfaceView) findViewById(R.id.surfaceView);
        // getting access to the surface of surfaceView and return it to surfaceHolder
        surfaceHolder = camView.getHolder();
        // adding call back to this context means MainActivity
        surfaceHolder.addCallback(this);
        // to set surface type
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        // stop the camera
        if(camCondition){
            camera.stopPreview(); // stop preview using stopPreview() method
            camCondition = false; // setting camera condition to false means stop
        }
        // condition to check whether your device have camera or not
        if (camera != null){
            try {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setColorEffect(Camera.Parameters.EFFECT_SEPIA); //applying effect on camera
                camera.setParameters(parameters); // setting camera parameters
                camera.setPreviewDisplay(surfaceHolder); // setting preview of camera
                camera.startPreview();  // starting camera preview

                camCondition = true; // setting camera to true which means having camera
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        camera = Camera.open();   // opening camera
        camera.setDisplayOrientation(90);   // setting camera preview orientation
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        camera.stopPreview();  // stopping camera preview
        camera.release();       // releasing camera
        camera = null;          // setting camera to null when left
        camCondition = false;   // setting camera condition to false also when exit from application
    }

}
