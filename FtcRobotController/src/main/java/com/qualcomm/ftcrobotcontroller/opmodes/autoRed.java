package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.Range;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class autoRed extends OpModeCamera
{
    SensorEventListener eSensorEventListener;
    Activity mainActivity;
    SensorManager eSensorManager;

    Sensor eAccelerometer;
    Sensor eMagnetometer;
    SensorEvent event;
    Boolean lastAccelerometerSet;
    Boolean lastMagnetometerSet;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    private float mCurrentDegree = 0f;

    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor rightFront;
    DcMotor rightBack;

    float heading;

    @Override
    public void init (){
        eSensorManager = (SensorManager) mainActivity.getSystemService(Context.SENSOR_SERVICE);
        eAccelerometer = eSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        eMagnetometer = eSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    public void init_loop (){
        eSensorManager.registerListener(eSensorEventListener,eAccelerometer,SensorManager.SENSOR_DELAY_GAME);
        eSensorManager.registerListener(eSensorEventListener,eMagnetometer,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void loop () {
        if(event.sensor == eAccelerometer) {
            telemetry.addData("accelerometer values", event.values);
            lastAccelerometerSet = true;
        }
        if(event.sensor == eMagnetometer) {
            telemetry.addData("magnetometor values", event.values);
            lastMagnetometerSet = true;
        }
    }

    @Override
    public void stop() {
        super.stop(); // stops camera functions
    }

}

