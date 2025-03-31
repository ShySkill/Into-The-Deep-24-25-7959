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


@Autonomous(name = "5 Specimen Auto PDRO")
public class SpecimenAuto extends PedroOpMode {
    public SpecimenAuto() {
        super(KeirsParts.Diffy.INSTANCE, KeirsParts.Lift.INSTANCE, KeirsParts.Extendo.INSTANCE, KeirsParts.Outtake.INSTANCE);
    }

    //added path velocity constraint and zpam increase
    Pose startingPose = new Pose(8.989595375722542, 64.59190751445087,  Math.toRadians(-180));
    Point wall = new Point(8, 28.134, Point.CARTESIAN);
    //splines to line up to cycle bricks into zone, aligns with wall for human and return is when it comes back from hitting a spec
    private PathChain goDump1, splineToBricks, cycle2, cycle3, cycle4, alignWithWall, spec2, return2, spec3, return3, spec4, return4, spec5;

    public void buildPaths() {
        goDump1 = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(8.990, 64.592, Point.CARTESIAN),
                                new Point(36.957, 61.763, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(-180))
                .addPath(
                        // Line 2
                        new BezierCurve(
                                new Point(36.957, 61.763, Point.CARTESIAN),
                                new Point(7.158, 10.488, Point.CARTESIAN),
                                new Point(97.554, 50.275, Point.CARTESIAN),
                                new Point(65.258, 24.805, Point.CARTESIAN),
                                new Point(16.314, 22.141, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(-180))
                .addPath(
                        // Line 3
                        new BezierCurve(
                                new Point(16.314, 22.141, Point.CARTESIAN),
                                new Point(95.889, 30.465, Point.CARTESIAN),
                                new Point(45.114, 8.490, Point.CARTESIAN),
                                new Point(16.481, 14.150, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(-180))
                .addPath(
                        // Line 4
                        new BezierCurve(
                                new Point(16.481, 14.150, Point.CARTESIAN),
                                new Point(68.920, 12.818, Point.CARTESIAN),
                                new Point(55.269, 8.157, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(-180))
                .addPath(
                        // Line 5
                        new BezierLine(
                                new Point(55.269, 8.157, Point.CARTESIAN),
                                new Point(16.314, 9.156, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(-180))
                .addPath(
                        // Line 6
                        new BezierCurve(
                                new Point(16.314, 9.156, Point.CARTESIAN),
                                new Point(45.281, 24.805, Point.CARTESIAN),
                                wall
                        )
                )
                .setZeroPowerAccelerationMultiplier(4.6)
                .setConstantHeadingInterpolation(Math.toRadians(-180))
                .addPath(
                        // Line 7
                        new BezierLine(
                                wall,
                                new Point(36.957, 66.423, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(-180))
                .addPath(
                        // Line 8
                        new BezierCurve(
                                new Point(36.957, 66.423, Point.CARTESIAN),
                                new Point(27.968, 27.135, Point.CARTESIAN),
                                wall
                        )
                )
                .setZeroPowerAccelerationMultiplier(4.6)
                .setConstantHeadingInterpolation(Math.toRadians(-180))
                .addPath(
                        // Line 9
                        new BezierLine(
                                wall,
                                new Point(36.957, 69.919, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(-180))
                .addPath(
                        // Line 10
                        new BezierCurve(
                                new Point(36.957, 69.919, Point.CARTESIAN),
                                new Point(29.632, 26.969, Point.CARTESIAN),
                                wall
                        )
                )
                .setZeroPowerAccelerationMultiplier(4.6)
                .setConstantHeadingInterpolation(Math.toRadians(-180))
                .addPath(
                        // Line 11
                        new BezierLine(
                                wall,
                                new Point(36.957, 74.247, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(-180))
                .addPath(
                        // Line 12
                        new BezierCurve(
                                new Point(36.957, 74.247, Point.CARTESIAN),
                                new Point(30.298, 27.801, Point.CARTESIAN),
                                wall
                        )
                )
                .setZeroPowerAccelerationMultiplier(4.6)
                .setConstantHeadingInterpolation(Math.toRadians(-180))
                .addPath(
                        // Line 13
                        new BezierLine(
                                wall,
                                new Point(37.956, 77.244, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(-180))
                .build();
    }

    public Command secondRoutine() {
        return new SequentialGroup(
                //SAMPLE NUMBER 1
                new ParallelGroup(
                        new FollowPath(goDump1.getPath(0)),
                        new SequentialGroup(
                                KeirsParts.Outtake.INSTANCE.dumpPiece(),
                                KeirsParts.Outtake.INSTANCE.wristUp()
                        )
                ),
                KeirsParts.Lift.INSTANCE.toMiddle(),
                new ParallelGroup(
                        KeirsParts.Outtake.INSTANCE.openClaw(),
                        KeirsParts.Lift.INSTANCE.toLow(),
                        new FollowPath(goDump1.getPath(1)),
                        KeirsParts.Outtake.INSTANCE.tuckInArm(),
                        KeirsParts.Outtake.INSTANCE.tuckWrist()
                ),
                new FollowPath(goDump1.getPath(2)),
                new FollowPath(goDump1.getPath(3)),
                new FollowPath(goDump1.getPath(4)),
                new FollowPath(goDump1.getPath(5)),
                KeirsParts.Outtake.INSTANCE.closeClaw(),
                new Delay(0.4),
                new ParallelGroup(
                        //goes to dump spec #2
                        new FollowPath(goDump1.getPath(6)),
                        KeirsParts.Outtake.INSTANCE.dumpPiece(),
                        KeirsParts.Outtake.INSTANCE.wristUp()
                ),
                KeirsParts.Lift.INSTANCE.toMiddle(),
                new ParallelGroup(
                        KeirsParts.Outtake.INSTANCE.openClaw(),
                        KeirsParts.Lift.INSTANCE.toLow(),
                        new FollowPath(goDump1.getPath(7)),
                        KeirsParts.Outtake.INSTANCE.tuckInArm(),
                        KeirsParts.Outtake.INSTANCE.tuckWrist()
                ),
                //picks up the spec and goes to score it
                new Delay(0.2),
                KeirsParts.Outtake.INSTANCE.closeClaw(),
                new Delay(0.25),
                new ParallelGroup(
                        new FollowPath(goDump1.getPath(8)),
                        KeirsParts.Outtake.INSTANCE.dumpPiece(),
                        KeirsParts.Outtake.INSTANCE.wristUp()
                ),
                KeirsParts.Lift.INSTANCE.toMiddle(),
                new ParallelGroup(
                        KeirsParts.Outtake.INSTANCE.openClaw(),
                        KeirsParts.Lift.INSTANCE.toLow(),
                        //returns to wall from placing the third
                        new FollowPath(goDump1.getPath(9)),
                        KeirsParts.Outtake.INSTANCE.tuckInArm(),
                        KeirsParts.Outtake.INSTANCE.tuckWrist()
                ),
                //by the wall and ready to pickup the fourth spec
                new Delay(0.2),
                KeirsParts.Outtake.INSTANCE.closeClaw(),
                new Delay(0.25),
                new ParallelGroup(
                        new FollowPath(goDump1.getPath(10)),
                        KeirsParts.Outtake.INSTANCE.dumpPiece(),
                        KeirsParts.Outtake.INSTANCE.wristUp()
                ),
                KeirsParts.Lift.INSTANCE.toMiddle(),
                new ParallelGroup(
                        KeirsParts.Outtake.INSTANCE.openClaw(),
                        KeirsParts.Lift.INSTANCE.toLow(),
                        //returns to wall from placing the third
                        new FollowPath(goDump1.getPath(11)),
                        KeirsParts.Outtake.INSTANCE.tuckInArm(),
                        KeirsParts.Outtake.INSTANCE.tuckWrist()
                ),
                new Delay(0.2),
                KeirsParts.Outtake.INSTANCE.closeClaw(),
                new Delay(0.25),
                new ParallelGroup(
                        new FollowPath(goDump1.getPath(12)),
                        KeirsParts.Outtake.INSTANCE.dumpPiece(),
                        KeirsParts.Outtake.INSTANCE.wristUp()
                ),
                KeirsParts.Lift.INSTANCE.toMiddle(),
                KeirsParts.Outtake.INSTANCE.openClaw()

        );
    }

    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startingPose);
        KeirsParts.Lift.INSTANCE.setToleranceSpec().invoke();
        KeirsParts.Outtake.INSTANCE.closeClaw().invoke();
        KeirsParts.Outtake.INSTANCE.autoWristDown().invoke();
        KeirsParts.Outtake.INSTANCE.startUp().invoke();
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
