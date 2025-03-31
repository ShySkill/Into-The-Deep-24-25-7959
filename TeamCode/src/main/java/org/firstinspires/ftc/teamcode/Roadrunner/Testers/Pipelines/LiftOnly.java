package org.firstinspires.ftc.teamcode.Roadrunner.Testers.Pipelines;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import org.firstinspires.ftc.teamcode.Roadrunner.Auto.Globals.constants.FConstants;
import org.firstinspires.ftc.teamcode.Roadrunner.Auto.Globals.constants.LConstants;
import org.firstinspires.ftc.teamcode.Roadrunner.Auto.References.Subsystems.Commands.KeirsParts;
@Autonomous(name = "Lift Only PDRO")
public class LiftOnly extends PedroOpMode {
    public LiftOnly() {
        super(KeirsParts.Diffy.INSTANCE, KeirsParts.Lift.INSTANCE, KeirsParts.Extendo.INSTANCE, KeirsParts.Outtake.INSTANCE);
    }

    public void buildPaths() {

    }

    public Command secondRoutine() {
        return new SequentialGroup(
                KeirsParts.Lift.INSTANCE.toMiddle(),
                KeirsParts.Lift.INSTANCE.toLow()


        );
    }

    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(new Pose(1.83121387283237, 107.70867052023121, 0));
        KeirsParts.Outtake.INSTANCE.closeClaw().invoke();
        KeirsParts.Outtake.INSTANCE.dumpPiece().invoke();
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
        telemetry.update();
    }

}
