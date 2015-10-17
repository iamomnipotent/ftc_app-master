package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 FOR USE WITH CONFIGURATION FILE "TWOSERVOS"
 */
public class frankservo extends OpModeCamera {


    Servo e_servo;
    Servo b_servo;
    double e_servo_pos;
    double b_servo_pos;
    public double servo_delta = 0.005;
    final static double MAX_SERVO_POS = 0.95;
    final static double MIN_SERVO_POS = 0.05;

    /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {

        e_servo = hardwareMap.servo.get("servo1");
        b_servo = hardwareMap.servo.get("servo2");

    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */

    @Override
    public void loop() {

        if (gamepad1.dpad_left) {
            e_servo_pos = e_servo_pos + servo_delta;
        }
        else if (gamepad1.dpad_right) {
            e_servo_pos = e_servo_pos - servo_delta;
        }
        else {
            e_servo_pos = 0.5;
        }

        if (gamepad1.dpad_down) {
            b_servo_pos = b_servo_pos - servo_delta;
        }
        else if (gamepad1.dpad_up) {
            b_servo_pos = b_servo_pos + servo_delta;
        }
        else {
            b_servo_pos = 0.5;
        }

        e_servo_pos = Range.clip(e_servo_pos, MIN_SERVO_POS, MAX_SERVO_POS);
        b_servo_pos = Range.clip(b_servo_pos, MIN_SERVO_POS, MAX_SERVO_POS);

        e_servo.setPosition(e_servo_pos);
        b_servo.setPosition(b_servo_pos);


    }

    @Override
    public void stop() {


    }
}
