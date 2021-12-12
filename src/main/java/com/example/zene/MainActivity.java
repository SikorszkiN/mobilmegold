package com.example.zene;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SensorEventListener  {

    ImageView imageView;
    Sensor sensor;
    SensorManager sensorManager;
    boolean isrunning1 = false;
    LocalDateTime lastStartTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }

    @Override
    public  void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    public  void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    MediaPlayer mp;
    @Override
    public void onSensorChanged(SensorEvent event) {
        float value = event.values[0];
        if(value>1500) {
            if (!isrunning1) {
                if ((lastStartTime == null || Duration.between(lastStartTime, LocalDateTime.now()).getSeconds()>=4)) {
                    isrunning1 = true;
                    imageView = (ImageView) findViewById(R.id.imageView);
                    imageView.setImageResource(R.drawable.background);
                    mp = new MediaPlayer();
                    try {
                        mp.setDataSource("/storage/emulated/0/Hangok/1.aac");
                        mp.prepare();
                        mp.start();
                        lastStartTime = LocalDateTime.now();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            else {
                if ((Duration.between(lastStartTime, LocalDateTime.now()).getSeconds()>=2)) {
                    isrunning1 = false;
                    imageView = (ImageView) findViewById(R.id.imageView);
                    imageView.setImageResource(R.drawable.background);
                    mp = new MediaPlayer();
                    try {
                        mp.setDataSource("/storage/emulated/0/Hangok/2.aac");
                        mp.prepare();
                        mp.start();
                        lastStartTime = LocalDateTime.now();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.kep);
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
