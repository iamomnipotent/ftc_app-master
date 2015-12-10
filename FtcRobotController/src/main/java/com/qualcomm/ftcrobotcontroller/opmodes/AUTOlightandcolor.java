package com.qualcomm.ftcrobotcontroller.opmodes;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AUTOlightandcolor extends OpMode {

    public LightSensor frontLight;
    public LightSensor backLight;

    public Boolean frontWhite;
    public Boolean backWhite;
    public Boolean enRoute;
    public Boolean waitLeft;
    public Boolean waitRight;

    public double frontVal, backVal;

    public DcMotor leftMotor, rightMotor;

    @Override
    public void init() {
        frontLight = hardwareMap.lightSensor.get("frontLight");
        backLight = hardwareMap.lightSensor.get("backLight");

        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");

        frontLight.enableLed(true);
        backLight.enableLed(true);

        // leftMotor.setDirection(DcMotor.Direction.REVERSE);
        // rightMotor.setDirection(DcMotor.Direction.REVERSE);

        enRoute = true;
        waitLeft = true;
        waitRight = true;
    }

    /*
       * Code to run when the op mode is first enabled goes here
       * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
       */
    @Override
    public void init_loop() {

    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void loop() {
        frontVal = frontLight.getLightDetected();
        backVal = backLight.getLightDetected();

        if (frontVal < 0.25) {
            frontWhite = true;
        } else if (frontVal >= 0.25) {
            frontWhite = false;
        }

        if (backVal < 0.25) {
            backWhite = true;
        } else if (backVal > 0.25) {
            backWhite = false;
        }

        if (!frontWhite && !backWhite) {
            leftMotor.setPower(1.0);
            rightMotor.setPower(1.0);
        }

        if (enRoute == true && frontWhite) {
            leftMotor.setPower(1.0);
            rightMotor.setPower(1.0);
            enRoute = false;
        } else if (enRoute == false && backWhite) {
            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                telemetry.addData("error", "error");
            }
            if (!frontWhite) {
                leftMotor.setPower(-0.5);
                rightMotor.setPower(0.5);
            } else if (frontWhite) {
                leftMotor.setPower(0.0);
                rightMotor.setPower(0.0);
            }
        }

        telemetry.addData("lw?", frontWhite);
        telemetry.addData("rw?", backWhite);
        telemetry.addData("ll", frontLight.getLightDetected());
        telemetry.addData("rl", backLight.getLightDetected());
        telemetry.addData("enroute?", enRoute);
    }
}
