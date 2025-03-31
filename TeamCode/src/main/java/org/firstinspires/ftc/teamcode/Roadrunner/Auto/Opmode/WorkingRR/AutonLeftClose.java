package org.firstinspires.ftc.teamcode.Roadrunner.Auto.Opmode.WorkingRR;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Roadrunner.Auto.Globals.Drive.PinpointDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.Auto.References.RRActions.KeirsRRParts.*;

@Config
@Autonomous(name = "3 Sample RR", group = "Autonomous")
public class AutonLeftClose extends LinearOpMode {

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-39, -62.5, Math.toRadians(180));
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);

        Extendo extendo = new Extendo(hardwareMap, telemetry);
        Intake intake = new Intake(hardwareMap);
        Lift lift = new Lift(hardwareMap, telemetry);
        Outtake outtake = new Outtake(hardwareMap, telemetry);

        Pose2d gopickup1 = new Pose2d(-60, -47, Math.toRadians(45));
        TrajectoryActionBuilder gopickup = drive.actionBuilder(gopickup1)
                .splineTo(new Vector2d(-56, -32), Math.toRadians(90));


        TrajectoryActionBuilder GoDumpPreLoad = drive.actionBuilder(initialPose)
                .splineToConstantHeading(new Vector2d(-37, -59), Math.PI)
                .splineTo(new Vector2d(-58, -45), Math.toRadians(45))
                //move backward readdy for lift
                .splineToConstantHeading(new Vector2d(-61, -48), Math.toRadians(45));

        //picks up the first specimen

        Pose2d pickup1 = new Pose2d(-57, -38, Math.toRadians(90));
        TrajectoryActionBuilder pickup11 = drive.actionBuilder(pickup1)
                //by bucket, then lift
                .splineToSplineHeading(new Pose2d(-61, -48, Math.toRadians(45)), Math.toRadians(135));


        Pose2d pre2 = new Pose2d(-61, -48, Math.toRadians(45));
        TrajectoryActionBuilder pre22 = drive.actionBuilder(pre2)
                //this is the sedcond pickup
                .splineToSplineHeading(new Pose2d(-66, -32, Math.toRadians(90)), Math.toRadians(135));

        Pose2d pickup2 = new Pose2d(-67, -34, Math.toRadians(90));
        TrajectoryActionBuilder pickup22 = drive.actionBuilder(pickup2)
                .splineToConstantHeading(new Vector2d(-60,-34), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-61, -48, Math.toRadians(45)), Math.toRadians(135));

        Pose2d pickup3 = new Pose2d(-67, -34, Math.toRadians(90));
        TrajectoryActionBuilder pickup33 = drive.actionBuilder(pickup3)
                .splineToConstantHeading(new Vector2d(-60,-30), Math.toRadians(45))
                .splineToSplineHeading(new Pose2d(-61, -35, Math.toRadians(135)), Math.toRadians(90));

        Pose2d poseMoveToBucket3 = new Pose2d(-60, -40, Math.toRadians(90));
        TrajectoryActionBuilder moveToBucket3 = drive.actionBuilder(poseMoveToBucket3)
                .splineToSplineHeading(new Pose2d(-61, -48, Math.toRadians(45)), Math.toRadians(90));


        // tightens the claw around the sample to start auton
        Actions.runBlocking(outtake.closeOutClaw());
        Actions.runBlocking(intake.prepIntake());
        Actions.runBlocking(outtake.BoxDown());
        Actions.runBlocking(extendo.ExtendoIn());

        waitForStart();

        if (isStopRequested()) return;



        Action goDumpPreLoad = GoDumpPreLoad.build();
        Action DumpFirstBlock = pickup11.build();
        Action GoDump2nd = pickup22.build();
        Action GoPickUp3rd = pickup33.build();
        Action gopickup11 = gopickup.build();
        Action GoPickUp2nd = pre22.build();
        Action MoveToBucket3 = moveToBucket3.build();
        //intakes
        Action IntakeDown = intake.IntakeDown();
        Action IntakeUp = intake.IntakeUp();
        Action OpenIntakeClaw = intake.openClaw();
        Action ICloseClaw = intake.closeClaw();

        //lift
        Action LiftUp = lift.liftUp();
        Action LiftDown = lift.liftDown();

        //outtake
        Action IntakeB = intake.IntakeB();
        Action OBoxDown = outtake.BoxDown();
        Action ODumpBox = outtake.DumpBox();
        Action CloseOutClaw = outtake.closeOutClaw();
        Action OpenOutClaw = outtake.openOutClaw();
        Action OutWristDown = outtake.OutWristDown();
        Action OutWristUp = outtake.OutWristUp();
        Action Loosen = intake.Loosen();
        //extendo
        Action ExtendoOut = extendo.ExtendoOut();
        Action ExtendoIn = extendo.ExtendoIn();



        Actions.runBlocking(
                new SequentialAction(
                        //by the bucket   keir is he best
                        new ParallelAction(
                                goDumpPreLoad,
                                ExtendoIn
                        ),
                        LiftUp,
                        ODumpBox,
                        new SleepAction(0.7),
                        OpenOutClaw,
                        new SleepAction(1),
                        OBoxDown,
                        new SleepAction(0.4),
                        LiftDown,
                        gopickup11,
                        //pickup and handoff
                        new ParallelAction(
                                OpenIntakeClaw,
                                OpenOutClaw,
                                new SleepAction(0.5)
                        ),
                        new ParallelAction(
                                IntakeDown,
                                OutWristDown,
                                new SleepAction(0.5)
                        ),
                        ICloseClaw,
                        new SleepAction(0.5),
                        IntakeUp,
                        new SleepAction(0.4),
                        new ParallelAction(
                                new SequentialAction(
                                        Loosen,
                                        new SleepAction(0.3),
                                        OutWristUp,
                                        new SleepAction(0.45)
                                ),
                                DumpFirstBlock
                        ),
                        CloseOutClaw,
                        new SleepAction(0.5),
                        OpenIntakeClaw,
                        new ParallelAction(
                                IntakeB,
                                LiftUp
                        ),
                        ODumpBox,
                        new SleepAction(0.8),
                        OpenOutClaw,
                        new SleepAction(0.7),
                        OBoxDown,
                        new SleepAction(0.8),
                        LiftDown,
                        GoPickUp2nd,
                        new ParallelAction(
                                OpenIntakeClaw,
                                OpenOutClaw,
                                new SleepAction(0.5)
                        ),
                        new ParallelAction(
                                IntakeDown,
                                OutWristDown,
                                new SleepAction(0.6)
                        ),
                        ICloseClaw,
                        new SleepAction(0.5),
                        IntakeUp,
                        new SleepAction(0.4),
                        new ParallelAction(
                                new SequentialAction(
                                        Loosen,
                                        new SleepAction(0.3),
                                        OutWristUp,
                                        new SleepAction(0.45)
                                ),
                                GoDump2nd
                        ),
                        CloseOutClaw,
                        new SleepAction(0.5),
                        OpenIntakeClaw,
                        new ParallelAction(
                                IntakeB,
                                LiftUp
                        ),
                        ODumpBox,
                        new SleepAction(0.7),
                        OpenOutClaw,
                        new SleepAction(0.7),
                        OBoxDown,
                        new SleepAction(0.7),
                        LiftDown
                )
        );


    }
}
