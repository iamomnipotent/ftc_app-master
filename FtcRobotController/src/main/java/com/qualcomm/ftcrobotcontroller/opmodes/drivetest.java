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
    double modifier=1.0;

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
        if (gamepad1.a)
        {modifier=1.0;}
        if (gamepad1.b)
        {modifier=0.75;}
        if (gamepad1.x)
        {modifier=0.50;}
        if (gamepad1.y)
        {modifier=0.25;}
    left1.setPower(gamepad1.left_stick_y*modifier);
    left2.setPower(gamepad1.left_stick_y);
    right1.setPower(gamepad1.right_stick_y);
    right2.setPower(gamepad1.right_stick_y);
        telemetry.addData("Modifier: ", modifier);

    }

    @Override
    public void stop() {


    }
}
