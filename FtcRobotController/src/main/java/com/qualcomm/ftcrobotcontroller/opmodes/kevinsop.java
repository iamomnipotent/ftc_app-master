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

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class kevinsop extends OpMode {

    private String startDate;
    private ElapsedTime runtime = new ElapsedTime();

    // TouchSensor e_switch;

    public Boolean isPressed;
    public Boolean dpadDown;
    public Boolean dpadUp;
    public Boolean abutton;
    public Boolean bbutton;
    public Boolean xbutton;

    public float leftMotorPower;
    public float rightMotorPower;

    public int[] red = new int[4];
    public int[] green = new int[4];
    public int[] blue = new int[4];

    public boolean discard;

    public DcMotor lf_motor;    // left front
    public DcMotor rf_motor;    // right front
    public DcMotor lr_motor;    // left rear
    public DcMotor rr_motor;    // right rear

    // public ServoController e_servocontroller;
    Servo e_servo;

    public double e_servo_delta = 0.01;
    public double e_servo_pos = 0.0;

    final static double MAX_ESERVO_POS = 0.95;
    final static double MIN_ESERVO_POS = 0.05;

    // public enum e_colorsensorDevice {ADAFRUIT, HITECHNIC_NXT, MODERN_ROBOTICS_I2C};
    // public e_colorsensorDevice device = e_colorsensorDevice.MODERN_ROBOTICS_I2C;

    ColorSensor e_colorsensor;
    // DeviceInterfaceModule cdim;
    TouchSensor e_touch;
    LED led;

    float hsvValues[] = {0F,0F,0F};
    final float values[] = hsvValues;

    // final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

    /*
    public void runOpMode() throws InterruptedException {
        hardwareMap.logDevices();

        cdim = hardwareMap.deviceInterfaceModule.get("dim");
        switch (device) {
            case HITECHNIC_NXT:
                e_colorsensor = hardwareMap.colorSensor.get("nxt");
                break;
            case ADAFRUIT:
                e_colorsensor = hardwareMap.colorSensor.get("lady");
                break;
            case MODERN_ROBOTICS_I2C:
                e_colorsensor = hardwareMap.colorSensor.get("mr");
                break;
        }
        led = hardwareMap.led.get("led");
        e_touch = hardwareMap.touchSensor.get("e_touch");

    }

    public void enableLed(boolean value) {
        switch (device) {
            case HITECHNIC_NXT:
                e_colorsensor.enableLed(value);
                break;
            case ADAFRUIT:
                led.enable(value);
                break;
            case MODERN_ROBOTICS_I2C:
                e_colorsensor.enableLed(value);
                break;
        }
    }
    */

    /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
        e_touch = hardwareMap.touchSensor.get("e_touch");
        e_servo = hardwareMap.servo.get("e_servo");
        e_colorsensor = hardwareMap.colorSensor.get("e_colorsensor");

        /*
        enableLed(e_touch.isPressed());

        switch (device) {
            case HITECHNIC_NXT:
                Color.RGBToHSV(e_colorsensor.red(), e_colorsensor.green(), e_colorsensor.blue(), hsvValues);
                break;
            case ADAFRUIT:
                Color.RGBToHSV((e_colorsensor.red() * 255) / 800, (e_colorsensor.green() * 255) / 800, (e_colorsensor.blue() * 255) / 800, hsvValues);
                break;
            case MODERN_ROBOTICS_I2C:
                Color.RGBToHSV(e_colorsensor.red()*8, e_colorsensor.green()*8, e_colorsensor.blue()*8, hsvValues);
                break;
        }
        telemetry.addData("Clear", e_colorsensor.alpha());
        telemetry.addData("Red  ", e_colorsensor.red());
        telemetry.addData("Green", e_colorsensor.green());
        telemetry.addData("Blue ", e_colorsensor.blue());
        telemetry.addData("Hue", hsvValues[0]);

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });
        */

        // startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        // runtime.reset();
        // e_servocontroller = hardwareMap.servoController.get("servoctrl");
        // e_servocontroller.pwmEnable();

        // e_colorsensor.enableLed(false);
        /*
        lf_motor = hardwareMap.dcMotor.get("leftfront");
        rf_motor = hardwareMap.dcMotor.get("rightfront");
        lr_motor = hardwareMap.dcMotor.get("leftrear");
        rr_motor = hardwareMap.dcMotor.get("rightrear");
        */
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void loop() {
        if(e_colorsensor.red() < 240) {
            red[0] = e_colorsensor.red();
            red[1] = red[0];
            red[2] = red[1];
            red[3] = (red[0] + red[1] + red[2]) / 3;
        }

        if(e_colorsensor.green() < 240) {
            green[0] = e_colorsensor.green();
            green[1] = green[0];
            green[2] = green[1];
            green[3] = (green[0] + green[1] + green[2]) / 3;
        }

        if(e_colorsensor.blue() < 240) {
            blue[0] = e_colorsensor.blue();
            blue[1] = blue[0];
            blue[2] = blue[1];
            blue[3] = (blue[0] + blue[1] + blue[2]) / 3;
        }

        Color.RGBToHSV(red[3], green[3], blue[3], hsvValues);

        if(e_touch.isPressed()) {
            e_colorsensor.enableLed(false);
        }
        else {
            e_colorsensor.enableLed(true);
        }

        // e_servo.setPosition(e_servo_pos);
        // if(e_servocontroller.getPwmStatus() == ServoController.PwmStatus.DISABLED) {
        //     e_servocontroller.pwmEnable();
        // }

        // isPressed = e_switch.isPressed();
        dpadDown = gamepad1.dpad_down;
        dpadUp = gamepad1.dpad_up;
        abutton = gamepad1.a;
        bbutton = gamepad1.b;
        xbutton = gamepad1.x;

        // leftMotorPower = gamepad1.left_stick_y;
        // rightMotorPower = gamepad1.right_stick_y;

        if(dpadDown) {
            e_servo_pos = e_servo_pos - e_servo_delta;
        }
        if(dpadUp) {
            e_servo_pos = e_servo_pos + e_servo_delta;
        }
        else {
            e_servo_pos = e_servo_pos;
        }

        if(abutton) {
            e_servo_pos = 0.25;
        }
        if(bbutton) {
            e_servo_pos = 0.75;
        }
        if(xbutton) {
            e_servo_pos = 0.05;
        }

        e_servo_pos = Range.clip(e_servo_pos, MIN_ESERVO_POS, MAX_ESERVO_POS);

        e_servo.setPosition(e_servo_pos);
        // e_servo.setPosition(0.9);

        /*
        lf_motor.setPower(leftMotorPower);
        lr_motor.setPower(leftMotorPower);
        rf_motor.setPower(rightMotorPower);
        rr_motor.setPower(rightMotorPower);
        */

        /*
        if(gamepad1.a) {
            isDPadDown = true;
        }
        else {
            isDPadDown = false;
        }
        */

        // telemetry.addData("1 Start", "NullOp started at " + startDate);
        // telemetry.addData("2 Status", "running for " + runtime.toString());
        // telemetry.addData("Is the switch pressed?", isPressed);
        // telemetry.addData("Is dpad up pressed?", dpadUp);
        // telemetry.addData("Is dpad down pressed?", dpadDown);
        // telemetry.addData("Servo position", e_servo.getPosition());
        // telemetry.addData("PWM status", e_servocontroller.getPwmStatus());
        // telemetry.addData("servo pos from servoctrl function", e_servocontroller.getServoPosition(1));
        telemetry.addData("current servo position", e_servo_pos);
        telemetry.addData("current acceleration", e_servo_delta);
        telemetry.addData("hue", hsvValues[0]);
        telemetry.addData("alpha", e_colorsensor.alpha());
        telemetry.addData("blue", blue[3]);
        telemetry.addData("green", green[3]);
        telemetry.addData("red", red[3]);

        if (red[3] > blue[3]) {
            telemetry.addData("color", "red");
        }
        if (blue[3] > red[3]) {
            telemetry.addData("color", "blue");
        }

        /*
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });
        */
    }
}
