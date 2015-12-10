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

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class notupdatingvalues extends OpMode {

    public LightSensor leftLight;
    public LightSensor rightLight;

    public Boolean leftWhite;
    public Boolean rightWhite;
    public Boolean enRoute;
    public Boolean waitLeft;
    public Boolean waitRight;

    public double leftVal, rightVal;

    public DcMotor leftMotor, rightMotor;

    @Override
    public void init() {
        leftLight = hardwareMap.lightSensor.get("leftLight");
        rightLight = hardwareMap.lightSensor.get("rightLight");

        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");

        leftLight.enableLed(true);
        rightLight.enableLed(true);

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        // rightMotor.setDirection(DcMotor.Direction.REVERSE);

        enRoute = true;
        waitLeft = true;
        waitRight = true;
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
        leftVal = leftLight.getLightDetected();
        rightVal = rightLight.getLightDetected();

        if (leftVal < 0.25) {
            leftWhite = true;
        } else if (leftVal >= 0.25) {
            leftWhite = false;
        }

        if (rightVal < 0.25) {
            rightWhite = true;
        } else if (rightVal > 0.25) {
            rightWhite = false;
        }

        if (!leftWhite && !rightWhite) {
            leftMotor.setPower(0.09);
            rightMotor.setPower(0.09);
        } else if (!leftWhite && rightWhite) {
            if (enRoute == true) {
                leftMotor.setPower(0.09);
                rightMotor.setPower(0.09);
            } else {
                if (waitRight == true) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        telemetry.addData("error", "error");
                    }
                    waitRight = false;
                } else {
                    leftMotor.setPower(0.4);
                    rightMotor.setPower(-0.7);
                }
            }
        } else if (leftWhite && !rightWhite) {
            enRoute = false;
            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);
            if (waitLeft == true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    telemetry.addData("error", "error");
                }
                waitLeft = false;
            } else {
                leftMotor.setPower(-1.0);
                rightMotor.setPower(0.3);
            }
        } else if (leftWhite && rightWhite) {
            leftMotor.setPower(0.09);
            rightMotor.setPower(0.09);
        }

        telemetry.addData("lw?", leftWhite);
        telemetry.addData("rw?", rightWhite);
        telemetry.addData("ll", leftLight.getLightDetected());
        telemetry.addData("rl", rightLight.getLightDetected());
        telemetry.addData("enroute?", enRoute);
    }
}
