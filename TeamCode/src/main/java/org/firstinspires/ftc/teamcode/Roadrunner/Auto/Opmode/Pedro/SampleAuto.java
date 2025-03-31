package org.firstinspires.ftc.teamcode.Roadrunner.Auto.Opmode.Pedro;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import org.firstinspires.ftc.teamcode.Roadrunner.Auto.Globals.constants.FConstants;
import org.firstinspires.ftc.teamcode.Roadrunner.Auto.Globals.constants.LConstants;
import org.firstinspires.ftc.teamcode.Roadrunner.Auto.References.Subsystems.Commands.KeirsParts;


@Autonomous(name = "Sample Auto")
public class SampleAuto extends PedroOpMode {
    public SampleAuto() {
        super(KeirsParts.Diffy.INSTANCE, KeirsParts.Lift.INSTANCE, KeirsParts.Extendo.INSTANCE, KeirsParts.Outtake.INSTANCE);
    }

    //didnt change anything, just turned on voltage compensation, and increased zpam from 5 to 6.
    Pose startingPose = new Pose(8.989595375722542, 112.70289017341041, Math.toRadians(0));
    private PathChain goDump1, getTwo, goDump2, getThree, goDump3, getFour, goDump4;

    public void buildPaths() {
        //line 3 to 4, line 4 to 4, line 6 to 3.5
        goDump1 = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierCurve(
                                new Point(8.990, 112.536, Point.CARTESIAN),
                                new Point(14.483, 133.346, Point.CARTESIAN),
                                new Point(20.976, 128.351, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(315))
                .addPath(
                        // Line 2
                        new BezierCurve(
                                new Point(20.976, 128.351, Point.CARTESIAN),
                                new Point(22.308, 119.528, Point.CARTESIAN),
                                new Point(28.900, 120.700, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(315), Math.toRadians(0))
                .addPath(
                        // Line 3
                        new BezierCurve(
                                new Point(28.900, 120.700, Point.CARTESIAN),
                                new Point(29.799, 126.687, Point.CARTESIAN),
                                new Point(21.975, 129.100, Point.CARTESIAN)
                        )
                )
                .setZeroPowerAccelerationMultiplier(4)
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(315))
                .addPath(
                        // Line 4
                        new BezierLine(
                                new Point(21.975, 129.100, Point.CARTESIAN),
                                new Point(31.129, 129.348, Point.CARTESIAN)
                        )
                )
                .setZeroPowerAccelerationMultiplier(4)
                .setLinearHeadingInterpolation(Math.toRadians(315), Math.toRadians(0))
                .addPath(
                        // Line 5
                        new BezierLine(
                                new Point(31.129, 129.348, Point.CARTESIAN),
                                new Point(21.975, 129.350, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(315))
                .addPath(
                        // Line 6
                        new BezierCurve(
                                new Point(21.975, 129.350, Point.CARTESIAN),
                                new Point(29.133, 105.877, Point.CARTESIAN),
                                new Point(41.300, 115.000, Point.CARTESIAN)
                        )
                )
                .setZeroPowerAccelerationMultiplier(3.5)
                .setLinearHeadingInterpolation(Math.toRadians(315), Math.toRadians(90))
                .addPath(
                        // Line 7
                        new BezierCurve(
                                new Point(41.300, 115.000, Point.CARTESIAN),
                                new Point(37.457, 101.383, Point.CARTESIAN),
                                new Point(22.474, 129.184, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(315))
                .build();


    }

    public Command secondRoutine() {
        return new SequentialGroup(
                //SAMPLE NUMBER 1
                new ParallelGroup(
                        new FollowPath(goDump1.getPath(0)),
                        KeirsParts.Lift.INSTANCE.toHigh(),
                        KeirsParts.Diffy.INSTANCE.toB()
                ),
                KeirsParts.Outtake.INSTANCE.dumpPiece(),
                new Delay(1),
                KeirsParts.Outtake.INSTANCE.openClaw(),
                new Delay(0.3),
                //lift is down and gets into pos to pickup sample
                //SAMPLE NUMBER 2
                new ParallelGroup(
                        KeirsParts.Outtake.INSTANCE.backDown(),
                        KeirsParts.Lift.INSTANCE.toLow(),
                        KeirsParts.Outtake.INSTANCE.wristUp(),
                        new FollowPath(goDump1.getPath(1))
                ),
                //gets extendo above sample
                new ParallelGroup(
                        KeirsParts.Diffy.INSTANCE.open()
                ),
                new Delay(0.2),
                new ParallelGroup(
                        KeirsParts.Outtake.INSTANCE.wristDown(),
                        KeirsParts.Diffy.INSTANCE.toPickup(),
                        KeirsParts.Diffy.INSTANCE.toPickupWrist()
                ),
                new Delay(0.5),
                KeirsParts.Diffy.INSTANCE.close(),
                //sample in claw, goes back up and retracts while moving to bucket
                new Delay(0.4),
                new ParallelGroup(
                        new FollowPath(goDump1.getPath(2)),
                        KeirsParts.Diffy.INSTANCE.toHandoff(),
                        KeirsParts.Diffy.INSTANCE.toHandoffWrist(),
                        new SequentialGroup(
                                new Delay(1.0),
                                KeirsParts.Diffy.INSTANCE.loosen(),
                                new Delay(0.5),
                                KeirsParts.Outtake.INSTANCE.wristUp(),
                                new Delay(0.3)
                        )
                ),
                //handoff complete?
                new ParallelGroup(
                        new SequentialGroup(
                                new Delay(0.5),
                                KeirsParts.Diffy.INSTANCE.open()
                        ),
                        KeirsParts.Outtake.INSTANCE.closeClaw()
                ),
                new Delay(0.2),
                new ParallelGroup(
                        KeirsParts.Diffy.INSTANCE.toB(),
                        KeirsParts.Lift.INSTANCE.toHigh()
                ),
                KeirsParts.Outtake.INSTANCE.dumpPiece(),
                new Delay(0.5),
                KeirsParts.Outtake.INSTANCE.wristUp(),
                new Delay(0.3),
                KeirsParts.Outtake.INSTANCE.openClaw(),
                new Delay(0.3),
                //SAMPLE NUMBER 3
                //lift is down and gets into pos to pickup sample number 3
                new ParallelGroup(
                        KeirsParts.Outtake.INSTANCE.backDown(),
                        KeirsParts.Lift.INSTANCE.toLow(),
                        KeirsParts.Outtake.INSTANCE.wristUp(),
                        new FollowPath(goDump1.getPath(3))
                ),
                //gets extendo above sample
                new ParallelGroup(
                        KeirsParts.Diffy.INSTANCE.open()
                ),
                new Delay(0.3),
                new ParallelGroup(
                        KeirsParts.Outtake.INSTANCE.wristDown(),
                        KeirsParts.Diffy.INSTANCE.toPickup(),
                        KeirsParts.Diffy.INSTANCE.toPickupWrist()
                ),
                new Delay(0.5),
                KeirsParts.Diffy.INSTANCE.close(),
                //sample in claw, goes back up and retracts while moving to bucket
                new Delay(0.5),
                new ParallelGroup(
                        new FollowPath(goDump1.getPath(4)),
                        KeirsParts.Diffy.INSTANCE.toHandoff(),
                        KeirsParts.Diffy.INSTANCE.toHandoffWrist(),
                        new SequentialGroup(
                                new Delay(1.0),
                                KeirsParts.Diffy.INSTANCE.loosen(),
                                new Delay(0.5),
                                KeirsParts.Outtake.INSTANCE.wristUp(),
                                new Delay(0.3)
                        )
                ),
                //handoff complete?
                new ParallelGroup(
                        new SequentialGroup(
                                new Delay(0.4),
                                KeirsParts.Diffy.INSTANCE.open()
                        ),
                        KeirsParts.Outtake.INSTANCE.closeClaw()
                ),
                new Delay(0.2),
                new ParallelGroup(
                        KeirsParts.Diffy.INSTANCE.toB(),
                        KeirsParts.Lift.INSTANCE.toHigh()
                ),
                KeirsParts.Outtake.INSTANCE.dumpPiece(),
                new Delay(0.5),
                KeirsParts.Outtake.INSTANCE.wristUp(),
                new Delay(0.5),
                KeirsParts.Outtake.INSTANCE.openClaw(),
                new Delay(0.5),
                //SAMPLE NUMBER 4
                new ParallelGroup(
                        KeirsParts.Outtake.INSTANCE.backDown(),
                        KeirsParts.Lift.INSTANCE.toLow(),
                        KeirsParts.Outtake.INSTANCE.wristUp(),
                        new FollowPath(goDump1.getPath(5))
                ),
                new Delay(0.5),
                //picking up the 4th
                new ParallelGroup(
                        KeirsParts.Outtake.INSTANCE.wristDown(),
                        KeirsParts.Outtake.INSTANCE.openClaw(),
                        KeirsParts.Diffy.INSTANCE.toPickupWrist()
                ),
                new ParallelGroup(
                        KeirsParts.Diffy.INSTANCE.forward(),
                        KeirsParts.Diffy.INSTANCE.turn90R(),
                        KeirsParts.Diffy.INSTANCE.turn90L()
                ),
                new Delay(0.2),
                KeirsParts.Diffy.INSTANCE.reverse(),
                KeirsParts.Diffy.INSTANCE.toPickup(),
                new Delay(0.7),
                KeirsParts.Diffy.INSTANCE.close(),
                //sample in claw, goes back up and retracts while moving to bucket
                new Delay(0.4),
                new ParallelGroup(
                        new FollowPath(goDump1.getPath(6)),
                        KeirsParts.Diffy.INSTANCE.toHandoff(),
                        KeirsParts.Diffy.INSTANCE.toHandoffWrist(),
                        new SequentialGroup(
                                new Delay(1.0),
                                KeirsParts.Diffy.INSTANCE.loosen(),
                                new Delay(0.5),
                                KeirsParts.Outtake.INSTANCE.wristUp(),
                                new Delay(0.3)
                        )
                ),
                //handoff complete?
                new ParallelGroup(
                        new SequentialGroup(
                                new Delay(0.5),
                                KeirsParts.Diffy.INSTANCE.open()
                        ),
                        KeirsParts.Outtake.INSTANCE.closeClaw()
                ),
                new Delay(0.2),
                new ParallelGroup(
                        KeirsParts.Diffy.INSTANCE.toB(),
                        KeirsParts.Lift.INSTANCE.toHigh()
                ),
                KeirsParts.Outtake.INSTANCE.dumpPiece(),
                new Delay(0.5),
                KeirsParts.Outtake.INSTANCE.wristUp(),
                new Delay(0.5),
                KeirsParts.Outtake.INSTANCE.openClaw(),
                new Delay(0.5),
                KeirsParts.Outtake.INSTANCE.backDown(),
                new Delay(0.5),
                KeirsParts.Lift.INSTANCE.toLow()
                );
    }

    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startingPose);
        KeirsParts.Outtake.INSTANCE.closeClaw().invoke();
        KeirsParts.Outtake.INSTANCE.wristUp().invoke();
        KeirsParts.Diffy.INSTANCE.toHandoffWrist().invoke();
        KeirsParts.Diffy.INSTANCE.toHandoff().invoke();
        buildPaths();
    }

    @Override
    public void onStartButtonPressed() {
        secondRoutine().invoke();
    }

    @Override
    public void onUpdate(){
        telemetry.addData("liftpos", KeirsParts.Lift.INSTANCE.motor.getCurrentPosition());
        telemetry.addData("target", KeirsParts.Lift.INSTANCE.controller.getTarget());
        telemetry.addData("POSE", follower.getPose());
        telemetry.addData("HEADING", follower.getHeadingVector());
        telemetry.update();
    }

}
