package com.jk.significantmotiontest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TriggerEventListener mTriggerEventListener;

    private final static String TAG = "MainActivity";

    public String getTime(){
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }

    public void playSound(){
        AudioPlayer ap = new AudioPlayer();
        ap.play(getApplicationContext(), R.raw.bonfirearrow);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Using the Significant Motion Sensor
        The significant motion sensor triggers an event each time significant motion is detected and then it disables itself.
        A significant motion is a motion that might lead to a change in the user's location; for example walking, biking, or sitting in a moving car.
        The following code shows you how to get an instance of the default significant motion sensor and how to register an event listener:*/


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION) != null){
            // Success! There's a accelerometer.
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);

            boolean isWakeUpSensor = mSensor.isWakeUpSensor();
            Log.d(TAG, "IswakeUpSensor: "+isWakeUpSensor);

        }
        else {
            // Failure! No accelerometer.
            Log.d(TAG, "No Accelerometer found");
        }

        mTriggerEventListener = new TriggerEventListener() {
            @Override
            public void onTrigger(TriggerEvent event) {
                Log.d("MainActivity", "Significant Motion Detected Time: "+getTime());

                //Send the data to asynctask to be updated on UI or saved to file/DB/shared_pref
                String input = "Significant Motion at time:"+getTime();
                new UpdateUITask().execute(input);
                playSound();
            }
        };

        mSensorManager.requestTriggerSensor(mTriggerEventListener, mSensor);

    }
}
