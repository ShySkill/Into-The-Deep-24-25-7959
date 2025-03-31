package org.firstinspires.ftc.teamcode.Roadrunner.Auto.References;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.teamcode.Roadrunner.Auto.References.Pipelines.ItWasAllBlueAndYellow;
import org.firstinspires.ftc.teamcode.Roadrunner.Auto.References.Pipelines.ItWasAllRedAndYellow;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;


@Disabled
@Autonomous(name="Red Sample Tester")
public class RedSampleTester extends LinearOpMode{
    final int cameraWidth = 1280;
    final int cameraHeight = 720;

    //ItWasAllBlueAndYellow pipeline = new ItWasAllBlueAndYellow();
    ItWasAllRedAndYellow pipeline = new ItWasAllRedAndYellow();

    public void runOpMode() throws InterruptedException{
        telemetry.addData("Starting Auton now: check camera to init", "Ready");
        telemetry.update();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvWebcam webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.setPipeline(pipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(cameraWidth, cameraHeight, OpenCvCameraRotation.UPRIGHT, OpenCvWebcam.StreamFormat.MJPEG);
            }


            @Override
            public void onError(int errorCode) {


            }
        });






        //view the webcam now, you cant check camera stream once the game is initialized.
        waitForStart();


        telemetry.addData("Succesfully init", "Running");
        telemetry.update();

        while(opModeIsActive()){

            //pit i 45 inch and 23 by firt tile
            //align this with focal points of camera that you measured with the matrix intrisics
            telemetry.addData("Angle:", ItWasAllRedAndYellow.getAngle());
            telemetry.addData("CenterX:", ItWasAllRedAndYellow.getCoordsX());
            telemetry.addData("CenterY:", ItWasAllRedAndYellow.getCoordsY());
            telemetry.addData("Color:", ItWasAllRedAndYellow.getColor());

            telemetry.addData("Angle:", ItWasAllBlueAndYellow.getAngle());
            telemetry.addData("CenterX:", ItWasAllBlueAndYellow.getCoordsX());
            telemetry.addData("CenterY:", ItWasAllBlueAndYellow.getCoordsY());
            telemetry.addData("Color:", ItWasAllBlueAndYellow.getColor());

            telemetry.update();

        }



        telemetry.addData("Status", "GG");
        telemetry.update();
    }






}
