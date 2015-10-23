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
    DcMotorController e_motorController;
    public int motor_pos;

    @Override
    public void init() {

        e_motor = hardwareMap.dcMotor.get("tetrixmotor");
        e_motorController = hardwareMap.dcMotorController.get("hitecmotorctrl");

    }

    /*
       * Code to run when the op mode is first enabled goes here
       * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
       */
    @Override
    public void loop() {

        e_motor.setPower(gamepad1.left_stick_y);

        e_motor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        e_motorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
        try {
            wait (10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        telemetry.addData("Encoder:", e_motor.getCurrentPosition());
        motor_pos = encoder ();
        telemetry.addData("motor_pos:", motor_pos);
        e_motorController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void stop() {
    }

    int encoder () {
        int l_return = 0;

        if (e_motor != null)
        {
            l_return = e_motor.getCurrentPosition ();
        }

        return l_return;

    }
}
