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
        intakeVertical = hardwareMap.dcMotor.get("vertical");
        intakeBelt = hardwareMap.dcMotor.get("belt");

    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */

    @Override
    public void loop() {
        int dpadleft=0;
        int dpadright=0;
        int dpaddown=0;
        int dpadup=0;
        /*if (prevLeftButton==false && gamepad1.left_stick_button==true) {
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
        }*/

        if (gamepad2.a)
        {modifier=1.0;}
        if (gamepad2.b)
        {modifier=0.75;}
        if (gamepad2.x)
        {modifier=0.25;}
        if (gamepad2.y)
        {modifier=0.50;}

        
        if (gamepad1.dpad_left)
        {intakeTurntable.setPower(0.25);}
        else if (!gamepad1.dpad_right) {intakeTurntable.setPower(0.0);}

        if (gamepad1.dpad_right)
        {intakeTurntable.setPower(-0.25);}
        else if (!gamepad1.dpad_left){intakeTurntable.setPower(0.0);}

        if (gamepad1.dpad_up)
        {intakeVertical.setPower(-1.0);}
        else if (!gamepad1.dpad_down) {intakeVertical.setPower(0.0);}

        if (gamepad1.dpad_down)
        {intakeVertical.setPower(1.0);}
        else if (!gamepad1.dpad_up) {intakeVertical.setPower(0.0);}

        if (gamepad1.left_bumper)
        {intakeBelt.setPower(-1.0);}
        else if (!gamepad1.right_bumper){intakeBelt.setPower(0.0);}

        if (gamepad1.right_bumper)
        {intakeBelt.setPower(1.0);}
        else if (!gamepad1.left_bumper) {intakeBelt.setPower(0.0);}
        


        leftMotor.setPower(gamepad2.left_stick_y*-1*modifier);
        rightMotor.setPower(gamepad2.right_stick_y*modifier);

        telemetry.addData("Modifier: ", modifier);
    }

    @Override
    public void stop() {

    }
}