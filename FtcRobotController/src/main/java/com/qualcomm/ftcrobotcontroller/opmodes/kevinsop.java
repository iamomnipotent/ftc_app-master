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
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

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

    public Boolean isViolated;
    public Boolean dpadDown;
    public Boolean dpadUp;

    public float leftMotorPower;
    public float rightMotorPower;

    public DcMotor lf_motor;    // left front
    public DcMotor rf_motor;    // right front
    public DcMotor lr_motor;    // left rear
    public DcMotor rr_motor;    // right rear

    public ServoController e_servocontroller;
    public Servo e_servo;

    public double e_servo_delta = 0.1;
    public double e_servo_pos = 0.1;

    final static double MAX_ESERVO_POS = 0.95;
    final static double MIN_ESERVO_POS = 0.05;

    /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
        startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        runtime.reset();
        // e_switch = hardwareMap.touchSensor.get("switch");
        e_servo = hardwareMap.servo.get("e_servo");
        e_servocontroller = hardwareMap.servoController.get("servoctrl");
        e_servocontroller.pwmEnable();

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
        // e_servo.setPosition(e_servo_pos);
        if(e_servocontroller.getPwmStatus() == ServoController.PwmStatus.DISABLED) {
            e_servocontroller.pwmEnable();
        }

        // isViolated = e_switch.isPressed();
        dpadDown = gamepad1.dpad_down;
        dpadUp = gamepad1.dpad_up;

        leftMotorPower = gamepad1.left_stick_y;
        rightMotorPower = gamepad1.right_stick_y;

        /*
        if(dpadDown) {
            e_servo_pos = e_servo_pos - e_servo_delta;
        }
        if(dpadUp) {
            e_servo_pos = e_servo_pos + e_servo_delta;
        }
        */

        // e_servo_pos = Range.clip(e_servo_pos, MAX_ESERVO_POS, MIN_ESERVO_POS);

        // e_servo.setPosition(e_servo_pos);
        e_servo.setPosition(0.83);

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
        // telemetry.addData("Is the switch pressed?", isViolated);
        // telemetry.addData("Is dpad up pressed?", dpadUp);
        // telemetry.addData("Is dpad down pressed?", dpadDown);
        telemetry.addData("Servo position", e_servo.getPosition());
        telemetry.addData("PWM status", e_servocontroller.getPwmStatus());
        telemetry.addData("servo pos from servoctrl function", e_servocontroller.getServoPosition(1));

    }
}
