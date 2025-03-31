//package org.firstinspires.ftc.teamcode.Roadrunner.Auto.Opmode;
//
//
//import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.ParallelAction;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.SequentialAction;
//import com.acmerobotics.roadrunner.SleepAction;
//import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.Roadrunner.Auto.Globals.Drive.PinpointDrive;
//import org.firstinspires.ftc.teamcode.Roadrunner.Auto.References.RRActions.KeirsRRParts.*;
//
//@Config
//@Disabled
//@Autonomous(name = "Extendo Auton Left (SAMPLE)", group = "Autonomous")
//public class ExtendoAutonLeft extends LinearOpMode {
//
//    @Override
//    public void runOpMode() {
//        //all of the coordinates for splining
//        Pose2d initialPose = new Pose2d(-40, -62.5, Math.toRadians(90));
//        Pose2d bucketPos = new Pose2d(-57, -54, Math.toRadians(45));
//        //pos of alignment for first sample (rightmost one)
//        Pose2d poseDumpOne = new Pose2d(-50, -51, Math.toRadians(90));
//        //pos of alignment for second sample (middle one)
//        Pose2d poseDump2 = new Pose2d(-55, -51, Math.toRadians(90));
//        //pos of alignment for third sample (leftmost one and closest to the wall)
//        Pose2d poseDump3 = new Pose2d(-60, -50, Math.toRadians(110));
//
//        //hardware
//        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);
//        Extendo extendo = new Extendo(hardwareMap, telemetry);
//        Intake intake = new Intake(hardwareMap);
//        Lift lift = new Lift(hardwareMap, telemetry);
//        Outtake outtake = new Outtake(hardwareMap, telemetry);
//
//        //goes from the starting position and dumps the preloaded sample
//        TrajectoryActionBuilder GoDumpPreLoad = drive.actionBuilder(initialPose)
//                .splineToLinearHeading(bucketPos, Math.toRadians(135));
//
//        //aligns with first sample to use extendo to pickup
//        TrajectoryActionBuilder gopickup1 = drive.actionBuilder(bucketPos)
//                .splineToLinearHeading(poseDumpOne, Math.toRadians(90));
//
//        //goes back to the bucket and dumps the first sample
//        TrajectoryActionBuilder dump1 = drive.actionBuilder(poseDumpOne)
//                .splineToLinearHeading(bucketPos, Math.toRadians(135));
//
//        //aligns ith the second sample to use extendo to pickup
//        TrajectoryActionBuilder gopickup2 = drive.actionBuilder(bucketPos)
//                //this is the sedcond pickup
//                .splineToLinearHeading(poseDump2, Math.toRadians(90));
//
//        //goes back to the bucket and dumps the second sample
//        TrajectoryActionBuilder dump2 = drive.actionBuilder(poseDump2)
//                .splineToLinearHeading(bucketPos, Math.toRadians(135));
//
//        //aligns with the third sample to use extendo to pickup. 110 degree angle.
//        TrajectoryActionBuilder gopickup3 = drive.actionBuilder(bucketPos)
//                .splineToLinearHeading(poseDump3, Math.toRadians(90));
//
//        //goes back to the bucket to dump the third sample
//        TrajectoryActionBuilder dump3 = drive.actionBuilder(poseDump3)
//                .splineToLinearHeading(bucketPos, Math.toRadians(135));
//
//
//        // tightens the claw around the sample to start auton
//        Actions.runBlocking(outtake.closeOutClaw());
//        Actions.runBlocking(intake.prepIntake());
//        Actions.runBlocking(outtake.BoxDown());
//        //warm up the lift
//        Actions.runBlocking(lift.liftDown());
//
//        waitForStart();
//
//        if (isStopRequested()) return;
//
//
//        //these all happen sequentially as listed here:
//        Action goDumpPreLoad = GoDumpPreLoad.build();
//        Action goPickUp1st = gopickup1.build();
//        Action DumpFirstBlock = dump1.build();
//        Action GoPickUp2nd = gopickup2.build();
//        Action GoDump2nd = dump2.build();
//        Action GoPickUp3rd = gopickup3.build();
//        Action GoDump3rd = dump3.build();
//        //intakes
//        Action IntakeDown = intake.IntakeDown();
//        Action IntakeUp = intake.IntakeUp();
//        Action OpenIntakeClaw = intake.openClaw();
//        Action ICloseClaw = intake.closeClaw();
//
//        //lift
//        Action LiftUp = lift.liftUp();
//        Action LiftDown = lift.liftDown();
//
//        //outtake
//        Action IntakeB = intake.IntakeB();
//        Action OBoxDown = outtake.BoxDown();
//        Action ODumpBox = outtake.DumpBox();
//        Action CloseOutClaw = outtake.closeOutClaw();
//        Action OpenOutClaw = outtake.openOutClaw();
//        Action OutWristDown = outtake.OutWristDown();
//        Action OutWristUp = outtake.OutWristUp();
//        Action Loosen = intake.Loosen();
//        //extendo
//        Action ExtendoOut = extendo.ExtendoOut();
//        Action ExtendoIn = extendo.ExtendoIn();
//
//
//
//        Actions.runBlocking(
//                new SequentialAction(
//                        //by the bucket   keir is he best
//                        goDumpPreLoad,
//                        LiftUp,
//                        ODumpBox,
//                        new SleepAction(1),
//                        OpenOutClaw,
//                        new SleepAction(0.5),
//                        new ParallelAction(
//                                OBoxDown,
//                                LiftDown,
//                                goPickUp1st,
//                                new SequentialAction(
//                                        new SleepAction(1),
//                                        ExtendoOut
//                                )
//                        ),
//                        //pickup and handoff
//                        new ParallelAction(
//                                OpenIntakeClaw,
//                                OpenOutClaw,
//                                new SleepAction(0.5)
//                        ),
//                        new ParallelAction(
//                                IntakeDown,
//                                OutWristDown,
//                                new SleepAction(0.3)
//                        ),
//                        ICloseClaw,
//                        new SleepAction(0.5),
//                        IntakeUp,
//                        new SleepAction(0.2),
//                        ExtendoIn,
//                        new ParallelAction(
//                                new SequentialAction(
//                                        Loosen,
//                                        new SleepAction(0.3),
//                                        OutWristUp,
//                                        new SleepAction(0.3)
//                                ),
//                                DumpFirstBlock
//                        ),
//                        CloseOutClaw,
//                        new SleepAction(0.5),
//                        OpenIntakeClaw,
//                        new ParallelAction(
//                                IntakeB,
//                                LiftUp
//                        ),
//                        ODumpBox,
//                        new SleepAction(0.8),
//                        OpenOutClaw,
//                        new SleepAction(0.5),
//                        new ParallelAction(
//                                OBoxDown,
//                                LiftDown,
//                                GoPickUp2nd,
//                                new SequentialAction(
//                                        new SleepAction(1),
//                                        ExtendoOut
//                                )
//                        ),
//                        new ParallelAction(
//                                OpenIntakeClaw,
//                                OpenOutClaw,
//                                new SleepAction(0.5)
//                        ),
//                        new ParallelAction(
//                                IntakeDown,
//                                OutWristDown,
//                                new SleepAction(0.6)
//                        ),
//                        ICloseClaw,
//                        new SleepAction(0.5),
//                        IntakeUp,
//                        new SleepAction(0.4),
//                        ExtendoIn,
//                        new ParallelAction(
//                                new SequentialAction(
//                                        Loosen,
//                                        new SleepAction(0.3),
//                                        OutWristUp,
//                                        new SleepAction(0.45)
//                                ),
//                                GoDump2nd
//                        ),
//                        CloseOutClaw,
//                        new SleepAction(0.5),
//                        OpenIntakeClaw,
//                        new ParallelAction(
//                                IntakeB,
//                                LiftUp
//                        ),
//                        ODumpBox,
//                        new SleepAction(0.7),
//                        OpenOutClaw,
//                        new ParallelAction(
//                                OBoxDown,
//                                LiftDown
//                        )
//                )
//        );
//
//
//    }
//}
