package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 FOR USE WITH CONFIGURATION FILE "TWOSERVOS"
 */
public class drivetest extends OpModeCamera {


    DcMotor right1;
    DcMotor right2;
    DcMotor left1;
    DcMotor left2;

    /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
    right1 = hardwareMap.dcMotor.get("right1");
    right2 = hardwareMap.dcMotor.get("right2");
    left1 = hardwareMap.dcMotor.get("left1");
    left2 = hardwareMap.dcMotor.get("left2");
    left1.setDirection(DcMotor.Direction.REVERSE);
    left2.setDirection(DcMotor.Direction.REVERSE);

    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */

    @Override
    public void loop() {
    left1.setPower(gamepad1.left_stick_y);
    left2.setPower(gamepad1.left_stick_y);
    right1.setPower(gamepad1.right_stick_y);
    right2.setPower(gamepad1.right_stick_y);

    }

    @Override
    public void stop() {


    }
}
