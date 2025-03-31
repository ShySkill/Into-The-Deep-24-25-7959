package org.firstinspires.ftc.teamcode.Roadrunner.Auto.References.Subsystems.Commands;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDCoefficients;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.Feedforward;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

import java.util.List;
import java.util.ArrayList;


public class KeirsParts {

    //ENUMS!!!
    public enum Partlist{
        Lift,
        Extendo,
        outtake,
        outakeClaw
    }


    public static class Lift extends Subsystem {
        // BOILERPLATE
        public static final Lift INSTANCE = new Lift();
        private Lift() { }

        // USER CODE
        public MotorEx motor;

        Feedforward kf  = new StaticFeedforward(0.0005);
        public PIDFController controller = new PIDFController(new PIDCoefficients(0.01, 0.0, 0.00001), kf, 530, 25);


        public String name = "liftL";

        public InstantCommand setToleranceSpec(){
            return new InstantCommand(() -> {
                controller.setSetPointTolerance(25);
                return null;
            });
        }

        public Command toLow() {
            return new RunToPosition(motor, 0, controller, this);
        }

        public Command toMiddle() {
            return new RunToPosition(motor, 570, controller, this);
        }

        public Command toHigh() {
            return new RunToPosition(motor, 2200.0, controller, this);
        }

        @NonNull
        @Override
        public Command getDefaultCommand(){
            return new HoldPosition(motor, controller, this);
        }

        @Override
        public void initialize() {
            motor = new MotorEx(name).reverse();
            controller.setSetPointTolerance(50);
        }

    }


    public static class Extendo extends Subsystem {
        // BOILERPLATE
        public static final Extendo INSTANCE = new Extendo();
        private Extendo() { }

        // USER CODE
        public MotorEx motor;

        Feedforward kf  = new StaticFeedforward(0.0045);
        public PIDFController controller = new PIDFController(new PIDCoefficients(0.01, 0.0, 0.00001), kf, 400, 20);

        public String name = "extendo";

        public Command toBack() {
            return new RunToPosition(motor, 0.0, controller, this);
        }

        public Command toSample(){
            return new RunToPosition(motor, 900, controller, this);
        }

        @Override
        public void initialize() {
            motor = new MotorEx(name);
            motor = new MotorEx(name).reverse();
        }

        @NonNull
        @Override
        public Command getDefaultCommand(){
            return new HoldPosition(motor, controller, this);
        }
    }


    public static class Diffy extends Subsystem {
        // BOILERPLATE
        public static final Diffy INSTANCE = new Diffy();
        private Diffy() { }

        // USER CODE
        public Servo wristR, wristL, elbowR, elbowL, claw;
        public String clawName = "claw";
        public String wristLName = "wristL";
        public String wristRName = "wristR";
        public String elbowRName = "elbowR";
        public String elbowLName = "elbowL";

        List<Servo> wristList = new ArrayList<>();
        List<Servo> elbowList = new ArrayList<>();

        public Command toHandoff() {
            return new MultipleServosToPosition(elbowList, 1-0.64, this);
        }

        public Command toHandoffWrist() {
            return new MultipleServosToPosition(wristList, 0, this);
        }

        double R = 70 / (255 * 1.6);
        public Command turn90(){
            //0.8980
            //wristR.setPosition(1 - ((135) / (255 * 1.6)) + R);
            //0.3216
            //wristL.setPosition(((135) / (255 * 1.6)) + R);
            return new MultipleServosToPosition(wristList, (1 - ((135) / (255 * 1.6)) + R), this);
        }
        public Command turnBack(){
            return new MultipleServosToPosition(wristList, (1-((135)/(255*1.6))), this);
        }

        public Command toPickup(){
            return new MultipleServosToPosition(elbowList, 0.02, this);
        }
        public Command toPickupWrist(){
            return new MultipleServosToPosition(wristList, ((135)/(255*(1.6))), this);
        }

        public Command toB(){
            return new MultipleServosToPosition(elbowList, 0.2, this);
        }

        public Command open() {
            return new ServoToPosition(claw, 0, this);
        }
        public Command loosen() {
            return new ServoToPosition(claw, 0.24, this);
        }

        public Command close() {
            return new ServoToPosition(claw, 0.4, this);
        }

        public Command turn90R() {
            return new ServoToPosition(wristR, (1 - ((135) / (255 * 1.6)) + R), this);
        }
        public Command turn90L() {
            return new ServoToPosition(wristL, (((135) / (255 * 1.6)) + R), this);
        }

        public Command turn0R() {
            return new ServoToPosition(wristR, (1 - ((135) / (255 * 1.6)) + R), this);
        }
        public Command turn0L() {
            return new ServoToPosition(wristL, (((135) / (255 * 1.6)) + R), this);
        }



        public InstantCommand reverse(){
            return new InstantCommand(() -> {
                wristR.setDirection(Servo.Direction.REVERSE);
                return null;
            });
        }
        public InstantCommand forward(){
            return new InstantCommand(() -> {
                wristR.setDirection(Servo.Direction.FORWARD);
                return null;
            });
        }


        public Command tuckInElbow(){
            return new MultipleServosToPosition(elbowList, 1-0.35, this);
        }

        @Override
        public void initialize() {
            claw = OpModeData.INSTANCE.getHardwareMap().servo.get(clawName);
            wristL = OpModeData.INSTANCE.getHardwareMap().servo.get(wristLName);
            wristR = OpModeData.INSTANCE.getHardwareMap().servo.get(wristRName);
            elbowR = OpModeData.INSTANCE.getHardwareMap().servo.get(elbowRName);
            elbowL = OpModeData.INSTANCE.getHardwareMap().servo.get(elbowLName);

            wristR.setDirection(Servo.Direction.REVERSE);

            elbowR.setDirection(Servo.Direction.REVERSE);

            wristList.add(wristL);
            wristList.add(wristR);
            elbowList.add(elbowL);
            elbowList.add(elbowR);
        }
    }
    public static class Outtake extends Subsystem {
        public static final Outtake INSTANCE = new Outtake();
        public Outtake() {

        }

        public Servo outL, outR, outClaw, outWrist;
        public String nameL = "outL";
        public String nameR = "outR";

        List<Servo> servoList = new ArrayList<>();

        public Command dumpPiece() {
            return new MultipleServosToPosition(servoList, 0.4, this);
        }

        public Command backDown() {
            return new MultipleServosToPosition(servoList, 1.0, this);
        }

        public Command tuckInArm(){
            return new MultipleServosToPosition(servoList, 0.83, this);
        }

        public Command startUp(){
            return new MultipleServosToPosition(servoList, 0.75, this);
        }
        public Command tuckWrist(){
            return new ServoToPosition(outWrist, 0.18, this);
        }

        public Command closeClaw() {
            return new ServoToPosition(outClaw, 0.35, this);
        }
        public Command openClaw() {
            return new ServoToPosition(outClaw, 0, this);
        }
        public Command wristDown() {
            return new ServoToPosition(outWrist, 0.18, this);
        }

        public Command autoWristDown() {
            return new ServoToPosition(outWrist, 0.35, this);
        }
        public Command wristUp() {
            return new ServoToPosition(outWrist, 0, this);
        }

        @Override
        public void initialize() {
            outR = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "outR");
            outL = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "outL");
            outClaw = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "outClaw");
            outWrist = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "outWrist");

            outL.setDirection(Servo.Direction.REVERSE);

            servoList.add(outL);
            servoList.add(outR);
        }
    }
/*
    public static class OutClaw extends Subsystem {
        public static final OutClaw INSTANCE = new OutClaw();
        public OutClaw() {

        }

        public Servo outClaw, outWrist;
        public String nameL = "outClaw";
        public String nameR = "outWrist";

        public Command closeClaw() {
            return new ServoToPosition(outClaw, 0.35, this);
        }
        public Command openClaw() {
            return new ServoToPosition(outClaw, 0, this);
        }
        public Command wristDown() {
            return new ServoToPosition(outWrist, 0.3, this);
        }
        public Command wristUp() {
            return new ServoToPosition(outWrist, 0, this);
        }

        @Override
        public void initialize() {
            outClaw = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "outClaw");
            outWrist = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "outWrist");

        }
    }*/





}
