package com.qualcomm.ftcrobotcontroller.opmodes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;

import com.qualcomm.ftcrobotcontroller.CameraPreview;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.io.ByteArrayOutputStream;

/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */
public class LinearOpModeCamera extends LinearOpMode {

    public Camera camera;
    public CameraPreview preview;

    public int width;
    public int height;
    public YuvImage yuvImage = null;

    volatile private boolean imageReady = false;

    private int looped = 0;
    private String data;
    private int ds = 1; // downsampling parameter


    @Override
    // should be overwritten by extension class
    public void runOpMode() throws InterruptedException {

    }

    public Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            try {
                Camera.Parameters parameters = camera.getParameters();
                width = parameters.getPreviewSize().width;
                height = parameters.getPreviewSize().height;
                yuvImage = new YuvImage(data, ImageFormat.NV21, width, height, null);
                imageReady = true;
                looped += 1;
            } catch (Exception e) {

            }
        }
    };

    public void setCameraDownsampling(int downSampling) {
        ds = downSampling;
    }

    public boolean imageReady() {
        return imageReady;
    }

    public boolean isCameraAvailable() {
        int cameraId = -1;
        Camera cam = null;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) { // Camera.CameraInfo.CAMERA_FACING_FRONT or BACK
                cameraId = i;
                break;
            }
        }
        try {
            cam = Camera.open(cameraId);
        } catch (Exception e) {
            Log.e("Error", "Camera Not Available!");
            return false;
        }
        cam.release();
        cam = null;
        return true;
    }

    public Camera openCamera(int cameraInfoType) {
        int cameraId = -1;
        Camera cam = null;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == cameraInfoType) { // Camera.CameraInfo.CAMERA_FACING_FRONT or BACK
                cameraId = i;
                break;
            }
        }
        try {
            cam = Camera.open(cameraId);
        } catch (Exception e) {
            Log.e("Error", "Can't Open Camera");
        }
        return cam;
    }

    public int red(int pixel) {
        return (pixel >> 16) & 0xff;
    }

    public int green(int pixel) {
        return (pixel >> 8) & 0xff;
    }

    public int blue(int pixel) {
        return pixel & 0xff;
    }

    public int gray(int pixel) {
        return (red(pixel) + green(pixel) + blue(pixel));
    }

    public int highestColor(int red, int green, int blue) {
        int[] color = {red, green, blue};
        int value = 0;
        for (int i = 1; i < 3; i++) {
            if (color[value] < color[i]) {
                value = i;
            }
        }
        return value;
    }

    public Bitmap convertYuvImageToRgb(YuvImage yuvImage, int width, int height, int downSample) {
        Bitmap rgbImage;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, width, height), 0, out);
        byte[] imageBytes = out.toByteArray();

        BitmapFactory.Options opt;
        opt = new BitmapFactory.Options();
        opt.inSampleSize = downSample;

        rgbImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, opt);
        return rgbImage;
    }

    public void startCamera() {

        camera=openCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        camera.setPreviewCallback(previewCallback);

        Camera.Parameters parameters = camera.getParameters();

        width = parameters.getPreviewSize().width / ds;
        height = parameters.getPreviewSize().height / ds;
        parameters.setPreviewSize(width, height);

        camera.setParameters(parameters);

        data = parameters.flatten();

    }

    public void stopCameraInSecs(int duration) {
        Thread cameraKillThread = new Thread(new CameraKillThread(duration));

        cameraKillThread.start();
    }

    public class CameraKillThread implements Runnable {
        int dur;
        public CameraKillThread(int duration) {
            dur=duration;
        }
        public void run() {
            try {
                Thread.sleep(dur * 1000, 0);
            } catch(InterruptedException ex) {

            }

            stopCamera();
            imageReady=false;
        }
    }

    public void stopCamera() {
        if (camera != null) {
            if (preview != null) {
                preview = null;
            }
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

}
