package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;

public class autoRed extends OpModeCamera
{

    DcMotor leftFront;
    DcMotor leftBack;
    DcMotor rightFront;
    DcMotor rightBack;

    @Override
    public void init () {

    }

    @Override
    public void loop () {

    }
    @Override
    public void stop() {
        super.stop(); // stops camera functions
    }

}

