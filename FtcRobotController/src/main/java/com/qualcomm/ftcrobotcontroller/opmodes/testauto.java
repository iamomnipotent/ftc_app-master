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

import android.graphics.Bitmap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class testauto extends OpModeCamera {

    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime firstInterval = new ElapsedTime();

    public double servo_delta = 0.01;
    public double servo_pos = 0.0;
    final static double MAX_SERVO_POS = 0.95;
    final static double MIN_SERVO_POS = 0.05;

    Servo servo;
    DcMotor motor;

    public int autoStage = 1;

    int ds2 = 2;  // additional downsampling of the image
    int redValue = 0;
    int blueValue = 0;
    int greenValue = 0;


    @Override
    public void init() {
        servo = hardwareMap.servo.get("servo");
        motor = hardwareMap.dcMotor.get("motor");
    }

    /*
       * Code to run when the op mode is first enabled goes here
       * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
       */
    @Override
    public void init_loop() {
        servo.setPosition(0.95);
        runtime.reset();
        firstInterval.reset();
        motor.setDirection(DcMotor.Direction.REVERSE);
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void loop() {
        //CAMERA CODE
        long startTime = System.currentTimeMillis();
        if (imageReady()) { // only do this if an image has been returned from the camera
            redValue = 0;
            blueValue = 0;
            greenValue = 0;

            Bitmap rgbImage;
            rgbImage = convertYuvImageToRgb(yuvImage, width, height, ds2);
            for (int x = 0; x < width / ds2; x++) {
                for (int y = 0; y < height / ds2; y++) {
                    int pixel = rgbImage.getPixel(x, y);
                    redValue += red(pixel);
                    blueValue += blue(pixel);
                    greenValue += green(pixel);
                }
            }
        }
        //END OF CAMERA CODE

        if(!gamepad1.a) {
            if (firstInterval.time() < 3.0) {
                if (autoStage == 1) {
                    servo.setPosition(0.05);
                    autoStage++;
                }
                if (autoStage == 2) {
                    servo.setPosition(0.95);
                }
            }
            if (firstInterval.time() >= 3.0) {
                servo.setPosition(0.50);
                motor.setPower(1.0);
            }
            if (firstInterval.time() >= 6.0) {
                motor.setPower(0.0);
                autoStage = 1;
                firstInterval.reset();
            }
        }
        else {
            if(redValue > blueValue)
            {
                telemetry.addData("RED RED RED RED", redValue);
                motor.setDirection(DcMotor.Direction.FORWARD);
                motor.setPower(1.0);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                motor.setDirection(DcMotor.Direction.REVERSE);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                motor.setDirection(DcMotor.Direction.FORWARD);
            }
            else
            {
                telemetry.addData("BLUE BLUE BLUE", blueValue);
            }
        }

        telemetry.addData("getRuntime()", getRuntime());
        telemetry.addData("Interval", firstInterval.toString());
        telemetry.addData("Auto stage", autoStage);
    }
}
