package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;

// TANK DRIVE FOR FINAL THINGY

public class drivetest extends OpModeCamera {

    DcMotor leftMotor;
    DcMotor rightMotor;
    double modifier=1.0;
    
    DcMotor intakeTurntable;
    DcMotor intakeVertical;
    DcMotor intakeBelt;

    Boolean leftButton = false;
    Boolean prevLeftButton = false;
    int leftCount=0;
    
    Boolean rightButton = false;
    Boolean prevRightButton = false;
    int rightCount = 0;
    
    String leftBooleanState;
    String rightBooleanState;

    /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        intakeTurntable = hardwareMap.dcMotor.get("turntable");
        intakeVertical = hardwareMap.dcMotor.get("Vertical");
        intakeBelt = hardwareMap.dcMotor.get("belt");
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */

    @Override
    public void loop() {
        if (prevLeftButton==false && gamepad1.left_stick_button==true) {
            leftCount++;
            leftCount = leftCount % 2;
        }
        prevLeftButton = gamepad1.left_stick_button;

        if (leftCount == 0) {
            leftMotor.setDirection(DcMotor.Direction.FORWARD);
            leftBooleanState = "forward";
        } else {
            leftMotor.setDirection(DcMotor.Direction.REVERSE);
            leftBooleanState = "reverse";
        }
        
        if (prevRightButton==false && gamepad1.right_stick_button==true) {
            rightCount++;
            rightCount = rightCount % 2;
        }
        prevRightButton = gamepad1.right_stick_button;
        
        if (rightCount == 0) {
            rightMotor.setDirection(DcMotor.Direction.FORWARD);
            rightBooleanState = "forward";
        } else {
            rightMotor.setDirection(DcMotor.Direction.REVERSE);
            rightBooleanState = "reverse";
        }

        if (gamepad1.a)
        {modifier=1.0;}
        if (gamepad1.b)
        {modifier=0.75;}
        if (gamepad1.x)
        {modifier=0.25;}
        if (gamepad1.y)
        {modifier=0.50;}

        leftMotor.setPower(gamepad1.left_stick_y*modifier);
        rightMotor.setPower(gamepad1.right_stick_y*modifier);

        telemetry.addData("Modifier: ", modifier);
        
        if (gamepad1.dpad_left)
        {intakeTurntable.setPower(1.0);}
        else if (!gamepad1.dpad_right) {intakeTurntable.setPower(0.0);}

        if (gamepad1.dpad_right)
        {intakeTurntable.setPower(-1.0);}
        else if (!gamepad1.dpad_right){intakeTurntable.setPower(0.0);}

        if (gamepad1.dpad_up)
        {intakeVertical.setPower(1.0);}
        else if (!gamepad1.dpad_up) {intakeVertical.setPower(0.0);}

        if (gamepad1.dpad_down)
        {intakeVertical.setPower(-1.0);}
        else if (!gamepad1.dpad_down) {intakeVertical.setPower(0.0);}

        if (gamepad1.left_bumper)
        {intakeBelt.setPower(1.0);}
        else if (!gamepad1.left_bumper){intakeBelt.setPower(0.0);}

        if (gamepad1.right_bumper)
        {intakeBelt.setPower(-1.0);}
        else if (!gamepad1.right_bumper) {intakeBelt.setPower(0.0);}
        
        if (gamepad1.a)
        {modifier=1.0;}
        if (gamepad1.b)
        {modifier=0.75;}
        if (gamepad1.x)
        {modifier=0.25;}
        if (gamepad1.y)
        {modifier=0.50;}

        leftMotor.setPower(gamepad1.left_stick_y*modifier);
        rightMotor.setPower(gamepad1.right_stick_y*modifier);

        telemetry.addData("Modifier: ", modifier);
    }

    @Override
    public void stop() {

    }
}