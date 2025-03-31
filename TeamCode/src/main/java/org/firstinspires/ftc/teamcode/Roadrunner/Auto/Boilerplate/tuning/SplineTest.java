package org.firstinspires.ftc.teamcode.Roadrunner.Auto.Boilerplate.tuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Roadrunner.Auto.Globals.Drive.PinpointDrive;

public final class SplineTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-12, -60, 0);
        //this is the one that will be executed
        if (TuningOpModes.DRIVE_CLASS.equals(PinpointDrive.class)) {
            PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);

            waitForStart();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            //this is conventional spline test position
                            .splineTo(new Vector2d(49, 1), Math.PI / 2)
                            .splineTo(new Vector2d(0, 53), Math.PI)
                            .build());

        //super cool
        } else {
            throw new RuntimeException();
        }
    }
}
