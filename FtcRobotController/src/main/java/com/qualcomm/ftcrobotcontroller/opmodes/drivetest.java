package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 FOR USE WITH CONFIGURATION FILE "TWOSERVOS"
 */
public class drivetest extends OpModeCamera {


    DcMotor motor1;
    DcMotor motor2;
    double modifier=1.0;

    /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
    motor1 = hardwareMap.dcMotor.get("motor1");
    motor2 = hardwareMap.dcMotor.get("motor2");
    motor1.setDirection(DcMotor.Direction.REVERSE);

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
        {modifier=0.25;}
        if (gamepad1.y)
        {modifier=0.50;}
    motor1.setPower(gamepad1.left_stick_y*modifier);
    motor2.setPower(gamepad1.right_stick_y*modifier);
        telemetry.addData("Modifier: ", modifier);

    }

    @Override
    public void stop() {


    }
}
