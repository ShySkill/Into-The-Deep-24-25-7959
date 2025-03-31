//package org.firstinspires.ftc.teamcode.Roadrunner.Testers.Pipelines;
//
//
//import static org.firstinspires.ftc.teamcode.Roadrunner.Robot.Opmode.DriverMotors.Motors.*;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//
//import org.firstinspires.ftc.teamcode.Roadrunner.Auto.References.Pipelines.ItWasAllRedAndYellow;
//import org.firstinspires.ftc.teamcode.Roadrunner.Robot.Opmode.DriverMotors.Motors;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvWebcam;
//
//
//@Disabled
//@Autonomous(name="Pipeline test")
//public class PipelineTester extends LinearOpMode{
//    final int cameraWidth = 1280;
//    final int cameraHeight = 720;
//
//    double R;
//
//    ItWasAllRedAndYellow pipeline = new ItWasAllRedAndYellow();
//
//    public void runOpMode() throws InterruptedException{
//        telemetry.addData("Starting Auton now: check camera to init", "Ready");
//        telemetry.update();
//
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        OpenCvWebcam webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
//        webcam.setPipeline(pipeline);
//
//        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
//            @Override
//            public void onOpened() {
//                webcam.startStreaming(cameraWidth, cameraHeight, OpenCvCameraRotation.UPRIGHT, OpenCvWebcam.StreamFormat.MJPEG);
//            }
//
//
//            @Override
//            public void onError(int errorCode) {
//
//
//            }
//        });
//
//
//        Motors.init(hardwareMap);
//        Motors.runWithoutEncoder();
//
//        //view the webcam now, you cant check camera stream once the game is initialized.
//        waitForStart();
//
//
//        telemetry.addData("Succesfully init", "Running");
//        telemetry.update();
//        ElapsedTime timer = new ElapsedTime();
//
//        while(opModeIsActive()){
//            if (timer.milliseconds() >= 100) {
//                //pit i 45 inch and 23 by firt tile
//                telemetry.addData("Angle:", ItWasAllRedAndYellow.getAngle());
//                telemetry.addData("CenterX:", ItWasAllRedAndYellow.getCoordsX());
//                telemetry.addData("CenterY:", ItWasAllRedAndYellow.getCoordsY());
//                telemetry.addData("Color:", ItWasAllRedAndYellow.getColor());
//                telemetry.update();
//                double desiredAngle = (ItWasAllRedAndYellow.getAngle() * 0.777778);
//                if (ItWasAllRedAndYellow.getAngle() > 90) {
//                    desiredAngle = ((ItWasAllRedAndYellow.getAngle() - 90) * 0.777778);
//                }
//                R = desiredAngle / (255 * 2.5);
//                wristR.setPosition(1 - ((135) / (255 * 2.5)) + R);
//                wristL.setPosition(((135) / (255 * 2.5)) + R);
//
//            }
//        }
//    }
//
//
//
//
//
//
//}
