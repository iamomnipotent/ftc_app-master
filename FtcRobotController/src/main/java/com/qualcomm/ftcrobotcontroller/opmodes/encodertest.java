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
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class encodertest extends OpMode {

    DcMotor e_motor;
    DcMotorController.DeviceMode devMode;
    DcMotorController e_motorController;
    // public int motor_pos;

    int loopCount = 1;

    @Override
    public void init() {

        e_motor = hardwareMap.dcMotor.get("tetrixmotor");
        e_motorController = hardwareMap.dcMotorController.get("hitecmotorctrl");
        e_motor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        // e_motorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_WRITE);

    }

    @Override
    public void init_loop() {
        devMode = DcMotorController.DeviceMode.WRITE_ONLY;

        e_motor.setDirection(DcMotor.Direction.REVERSE);
        e_motor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    /*
       * Code to run when the op mode is first enabled goes here
       * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
       */


    @Override
    public void loop() {

        if(allowedToWrite()) {
            e_motor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            e_motor.setPower(gamepad1.left_stick_y);
        }
        if(loopCount % 17 == 0) {
            e_motorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
        }
        if(allowedToRead()) {
            // Update the reads after some loops, when the command has successfully propagated through.
            telemetry.addData("Text", "free flow text");
            telemetry.addData("motor power", e_motor.getPower());
            telemetry.addData("motor position", scaleEncoder(e_motor));
            telemetry.addData("mctrl state", e_motorController.getMotorControllerDeviceMode());

            e_motorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
            loopCount = 0;
        }

        devMode = e_motorController.getMotorControllerDeviceMode();
        loopCount++;

        /*
        do {
            e_motorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);}
        while (e_motorController.getMotorControllerDeviceMode() != DcMotorController.DeviceMode.READ_ONLY);
        */

        /*
        e_motorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);

        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        telemetry.addData("mctrlstatus", e_motorController.getMotorControllerDeviceMode());
        telemetry.addData("Encoder:", scaleEncoder(e_motor)); // e_motor.getCurrentPosition());
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        e_motorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);

        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }   // SIGNIFICANT LAG
        /*
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }   // SIGNIFICANT LAG
        */

        /*
        if(e_motorController.getMotorControllerDeviceMode() == DcMotorController.DeviceMode.SWITCHING_TO_WRITE_MODE || e_motorController.getMotorControllerDeviceMode() == DcMotorController.DeviceMode.WRITE_ONLY){
            e_motor.setPower(gamepad1.left_stick_y);
            e_motorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(e_motorController.getMotorControllerDeviceMode() == DcMotorController.DeviceMode.SWITCHING_TO_READ_MODE || e_motorController.getMotorControllerDeviceMode() == DcMotorController.DeviceMode.READ_ONLY){
            telemetry.addData("encoder", scaleEncoder(e_motor));
            e_motorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */

        // THIS DOESN'T WORK YET, BUT I THINK IT HAS POTENTIALS, WE JUST HAVE TO MAKE SURE THAT THE ENCODER VALUE STAYS THERE AFTER IT'S SWITCHED TO WRITE MODE
        // SO FAR IT ONLY SHOWS UP SPORADICALLY WHEN THE THING IS IN READ/SWITCHING TO READ MODES

        /*
        do {
            e_motorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);}
        while (e_motorController.getMotorControllerDeviceMode() != DcMotorController.DeviceMode.WRITE_ONLY);
        // DOES NOT WORK
        */

    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void stop() {
    }

    public double scaleEncoder (DcMotor motor) {
        double l_return = 0;

        if (e_motor != null) {
            l_return = (double) motor.getCurrentPosition()/100;        // convert result to double datatype
        }

        return l_return;
    }

    private boolean allowedToWrite(){
        return (devMode == DcMotorController.DeviceMode.WRITE_ONLY);
    }

    private boolean allowedToRead(){
        return (devMode == DcMotorController.DeviceMode.READ_ONLY);
    }
}
