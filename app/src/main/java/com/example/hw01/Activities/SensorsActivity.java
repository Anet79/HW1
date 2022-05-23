package com.example.hw01.Activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw01.CallBacks.CallBackSensor;

public class SensorsActivity {
    private SensorManager sensorManager;
    private Sensor accSensor;
    private CallBackSensor callBackSensor;
    private AppCompatActivity activity;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallBackSensor(CallBackSensor callBackSensor) {
        this.callBackSensor = callBackSensor;
    }

    public SensorsActivity(GameActivity gameActivity) {
        setActivity(gameActivity);
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    private SensorEventListener accSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            callBackSensor.moveSensorMode(event.values[0],event.values[1]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void resumed(){
        sensorManager.registerListener(accSensorEventListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void paused() {
        sensorManager.unregisterListener(accSensorEventListener);
    }

}
