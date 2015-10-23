package com.qualcomm.ftcrobotcontroller.opmodes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */
public class ethansop extends OpModeCamera {

    DcMotor e_motor;
    public double e_motor_power;
    
    Boolean button = false;
    Boolean prevbutton = false;
    int count=0;
    String motorState;

    Servo e_servo;
    public double e_servo_delta = 0.01;
    public double e_servo_pos = 0.0;
    final static double MAX_ESERVO_POS = 0.95;
    final static double MIN_ESERVO_POS = 0.05;

    int ds2 = 2;  // additional downsampling of the image
    private int looped = 0;
    private long lastLoopTime = 0;
    /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {

        e_motor = hardwareMap.dcMotor.get("tetrixmotor");
        e_motor.setDirection(DcMotor.Direction.FORWARD);

        e_servo = hardwareMap.servo.get("hitecservo");

        setCameraDownsampling(8);
        super.init(); // inits camera functions, starts preview callback

    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */

    @Override
    public void loop() {

        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

        final float values[] = {300, 100, 100};


        //MOTORS
        e_motor_power = gamepad1.left_stick_y;
        e_motor.setPower(scaleInput(e_motor_power));    // utilizes scaling to accurately control motor


        //MOTOR DIRECTION SWITCH
        if (prevbutton==false && gamepad1.left_stick_button==true) {
            count++;
            count = count % 2;
        }
        prevbutton = gamepad1.left_stick_button;

        if (count == 0) {
            e_motor.setDirection(DcMotor.Direction.FORWARD);
            motorState = "forward";
        }
        else {
            e_motor.setDirection(DcMotor.Direction.REVERSE);
            motorState = "reverse";
        }


        //SERVOS
        if (gamepad1.a) {
            e_servo_pos = 0.05;
        }
        if (gamepad1.b) {
            e_servo_pos = 0.95;
        }
        if (gamepad1.x) {
            e_servo_pos = 0.50;
        }

        if (gamepad1.dpad_down) {
            e_servo_pos = e_servo_pos - e_servo_delta;
        }
        if (gamepad1.dpad_up) {
            e_servo_pos = e_servo_pos + e_servo_delta;
        }

        e_servo_pos = Range.clip(e_servo_pos, MIN_ESERVO_POS, MAX_ESERVO_POS);

        e_servo.setPosition(e_servo_pos);

        //CAMERA CODE
        long startTime = System.currentTimeMillis();
        if (imageReady()) { // only do this if an image has been returned from the camera
            int redValue = 0;
            int blueValue = 0;
            int greenValue = 0;

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

            String colorString = "";
            if (redValue > blueValue) {
                colorString = "RED";
            }
            else {
                colorString = "BLUE";
            }
            telemetry.addData("Color:", "Color detected is: " + colorString);

            int pixel = rgbImage.getPixel(20, 20);
            telemetry.addData("pixel: ", pixel);

        }
        long endTime = System.currentTimeMillis();
        telemetry.addData("Dims", Integer.toString(width / ds2) + " x " + Integer.toString(height / ds2));
        telemetry.addData("Loop Time", Long.toString(endTime - startTime));
        telemetry.addData("Loop to Loop Time", Long.toString(endTime - lastLoopTime));
        lastLoopTime = endTime;
        //END OF CAMERA CODE

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values)); //16711935 is FF00FF in hex, or pink
            }
        });
        telemetry.addData("Magenta:", Color.HSVToColor(0xff, values));


        // TELEMETRY START
        telemetry.addData("Motor direction", motorState);
        telemetry.addData("Scaled motor power", scaleInput(e_motor_power));
        // TELEMETRY END


    }

    @Override
    public void stop() {
        super.stop(); // stops camera functions
    }


    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal) {
        double[] scaleArray = {0.0,0.25,0.50,0.75,1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 4.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 4) {
            index = 4;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}

