/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class autoline extends OpMode {

    public ColorSensor leftColor;
    public ColorSensor rightColor;
    public Boolean leftWhite;
    public Boolean rightWhite;

    public LightSensor leftLight;
    public LightSensor rightLight;

    public DcMotor left;
    public DcMotor right;

    public Servo mainServo;

    public double setServoPos;
    public double servoDelta;

    final static double MAX_ESERVO_POS = 0.95;
    final static double MIN_ESERVO_POS = 0.05;

    @Override
    public void init() {
        // leftColor = hardwareMap.colorSensor.get("leftColor");
        leftLight = hardwareMap.lightSensor.get("leftLight");
        rightLight = hardwareMap.lightSensor.get("rightLight");

        /*
        rightColor = hardwareMap.colorSensor.get("rightColor");

        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");


        mainServo = hardwareMap.servo.get("mainServo");
        */

        servoDelta = 0.005;
        // isWhite = false;
        // mainServo.setPosition(1.00);
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
        // TEST SERVO CODE START

        if(gamepad1.a){
            setServoPos = 0.05;
        }
        if(gamepad1.b){
            setServoPos = 0.20;
        }
        if(gamepad1.x){
            setServoPos = 0.65;
        }
        if(gamepad1.y){
            setServoPos = 0.95;
        }

        if(gamepad1.dpad_up){
            setServoPos = setServoPos + servoDelta;
        }
        else if(gamepad1.dpad_down){
            setServoPos = setServoPos - servoDelta;
        }

        setServoPos = Range.clip(setServoPos, MIN_ESERVO_POS, MAX_ESERVO_POS);

        // mainServo.setPosition(setServoPos);
        // TEST SERVO CODE END

        // COLOR SENSOR CODE START
        // leftColor.enableLed(true);
        // rightColor.enableLed(true);
        leftLight.enableLed(false);
        rightLight.enableLed(false);

        float hsvValues1[] = {0,0,0};
        // Color.RGBToHSV(leftColor.red(), leftColor.green(), leftColor.blue(), hsvValues1);

        /*
        float hsvValues2[] = {0,0,0};
        Color.RGBToHSV(rightColor.red(), rightColor.green(), rightColor.blue(), hsvValues2);
        */

        /*
        if(leftColor.red() > 20 && leftColor.blue() > 20 && leftColor.green() > 20){
            leftWhite = true;
            // telemetry.addData("is white detected?", "YES");
        }
        else{
            leftWhite = false;
            // telemetry.addData("is white detected?", "NO");
        }
        */

        /*
        if(rightColor.red() > 70 && rightColor.blue() > 70 && rightColor.green() > 70){
            rightWhite = true;
        }
        else{
            rightWhite = false;
        }
        */

        /*
        if(leftWhite && !rightWhite){
            left.setPower(0.8);
            right.setPower(1.0);
        }
        else if(!leftWhite && rightWhite){
            left.setPower(1.0);
            right.setPower(0.8);
        }
        else if(!leftWhite && !rightWhite){
            left.setPower(1.0);
            right.setPower(1.0);
        }
        else if(leftWhite && rightWhite){
            left.setPower(0.0);
            right.setPower(0.0);
            telemetry.addData("ERROR","ERROR");
        }
        */

        /*
        telemetry.addData("color sensor red", leftColor.red());
        telemetry.addData("color sensor blue", leftColor.blue());
        telemetry.addData("color sensor green", leftColor.green());
        telemetry.addData("Hue", hsvValues1[0]);
        telemetry.addData("left white?", leftWhite);
        */

        telemetry.addData("left light value", leftLight.getLightDetectedRaw());
        telemetry.addData("right light value", rightLight.getLightDetectedRaw());
        // COLOR SENSOR CODE END
    }
}
