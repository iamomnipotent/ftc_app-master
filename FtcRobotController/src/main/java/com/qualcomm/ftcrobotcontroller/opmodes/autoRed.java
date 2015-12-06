package com.qualcomm.ftcrobotcontroller.opmodes;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.hardware.DcMotor;

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
    double rfpower;
    double rbpower;
    double lfpower;
    double lbpower;

    float heading;

    @Override
    public void init (){
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        //eSensorManager = (SensorManager) mainActivity.getSystemService(Context.SENSOR_SERVICE);
        //eAccelerometer = eSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //eMagnetometer = eSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    public void init_loop (){
        //eSensorManager.registerListener(eSensorEventListener,eAccelerometer,SensorManager.SENSOR_DELAY_GAME);
        //eSensorManager.registerListener(eSensorEventListener,eMagnetometer,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void loop () {
        //DRIVE FORWARD
        if(this.time <= 4) //do for the first 4 seconds
        {
        lfpower = 0.75;
        lbpower = 0.75;
        rfpower = 0.75;
        rbpower = 0.75;
        }
        else if(this.time <=5) { //4-5 seconds
        }
        //TURN 90 DEGREES COUNTERCLOCKWISE
        else if(this.time <=8) {//5-8 seconds
            lfpower = -0.5;
            lbpower = -0.5;
            rfpower = 0.5;
            rbpower = 0.5;
        }
        leftFront.setPower(lfpower);
        leftBack.setPower(lbpower);
        rightFront.setPower(rfpower);
        rightBack.setPower(rbpower);
        /*if(event.sensor == eAccelerometer) {
            telemetry.addData("accelerometer values", event.values);
            lastAccelerometerSet = true;
        }
        if(event.sensor == eMagnetometer) {
            telemetry.addData("magnetometor values", event.values);
            lastMagnetometerSet = true;
        }*/
    }

    @Override
    public void stop() {
        super.stop(); // stops camera functions
    }

}

