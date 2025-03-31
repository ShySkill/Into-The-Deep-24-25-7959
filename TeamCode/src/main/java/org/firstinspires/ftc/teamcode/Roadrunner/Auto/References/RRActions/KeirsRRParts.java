package org.firstinspires.ftc.teamcode.Roadrunner.Auto.References.RRActions;


import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class KeirsRRParts {

    //ENUMS!!!
    public enum Partlist{
        Lift,
        Extendo,
        intake,
        intakeClaw,
        outtake,
        outakeClaw
    }


    public static class Lift {
        private final DcMotorEx liftL;
        private final Telemetry telemetry;

        public Lift(HardwareMap hardwareMap, Telemetry telemetry) {
            liftL = hardwareMap.get(DcMotorEx.class, "liftL");
            liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            liftL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftL.setDirection(DcMotor.Direction.FORWARD); //going the other way now
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              liftL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            this.telemetry = telemetry;
        }


        public class ResetLift implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                liftL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                liftL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                return false;
            }
        }
        public Action resetLift(){
            return new ResetLift();
        }

        /* OG LIFT CODE
        public class LiftUp implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                int pos = 1000;
                liftL.setTargetPosition(pos);
                liftL.setPower(0.6);
                while(liftL.getCurrentPosition() < pos || liftL.isBusy()){
                    liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("Position" ,liftL.getCurrentPosition());
                    telemetry.update();
                }

                if(liftL.getCurrentPosition() > pos-10 && liftL.getCurrentPosition() < pos+10 ){
                    liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    liftL.setPower(0.1);
                }
                liftL.setPower(0.1);
                return false;
            }
        }
         */



        public class LiftUp implements Action {
            double tolerance = 10;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                int pos = -2200;
                liftL.setTargetPosition(pos);
                liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                liftL.setPower(1);
                //1000 - 991 = 9 -> condition is false
                //1000 - 0 = 1000 > 10 condition is true
                while(Math.abs(pos) - Math.abs(liftL.getCurrentPosition()) > tolerance){
                    liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("Position" ,liftL.getCurrentPosition());
                    telemetry.addData("Difference:", Math.abs(pos) - Math.abs(liftL.getCurrentPosition()));
                    telemetry.update();
                }
                liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                liftL.setPower(0);
                return false;
            }
        }

        public Action liftUp() {
            return new LiftUp();
        }


        //changing the lift direction
        public class LiftDown implements Action {
            double tolerance = 1;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                liftL.setTargetPosition(0);
                liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                liftL.setPower(1);
                //1000 is decreasing to like 10, if its greater then 1 then keep running, otherwise sop
                while(Math.abs(liftL.getCurrentPosition()) > tolerance){
                    liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("Position" ,liftL.getCurrentPosition());
                    telemetry.update();
                }

                liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                liftL.setPower(0);
                return false;
            }
        }

        public Action liftDown() {
            return new LiftDown();
        }

        public class SpecLiftUp implements Action {
            double tolerance = 10;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                int pos = -570;
                liftL.setTargetPosition(pos);
                liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                liftL.setPower(1);
                //1000 - 991 = 9 -> condition is false
                //1000 - 0 = 1000 > 10 condition is true
                while(Math.abs(pos) - Math.abs(liftL.getCurrentPosition()) > tolerance){
                    liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("Position" ,liftL.getCurrentPosition());
                    telemetry.addData("Difference:", Math.abs(pos) - Math.abs(liftL.getCurrentPosition()));
                    telemetry.update();
                }
                liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                liftL.setPower(0);
                return false;
            }
        }

        public Action specLiftUp1() {
            return new SpecLiftUp1();
        }

        public class SpecLiftUp1 implements Action {
            double tolerance = 10;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                int pos = -515;
                liftL.setTargetPosition(pos);
                liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                liftL.setPower(1);
                //1000 - 991 = 9 -> condition is false
                //1000 - 0 = 1000 > 10 condition is true
                while(Math.abs(pos) - Math.abs(liftL.getCurrentPosition()) > tolerance){
                    liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("Position" ,liftL.getCurrentPosition());
                    telemetry.addData("Difference:", Math.abs(pos) - Math.abs(liftL.getCurrentPosition()));
                    telemetry.update();
                }
                liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                liftL.setPower(0);
                return false;
            }
        }

        public Action specLiftUp() {
            return new SpecLiftUp();
        }






    }

    public static class Extendo {
        private final DcMotor extendo;
        private Telemetry telemetry;

        public class ResetExtendo implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                extendo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                extendo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                return false;
            }
        }
        public Action resetExtendo(){
            return new ResetExtendo();
        }

        public Extendo(HardwareMap hardwareMap, Telemetry telemetry) {
            extendo = hardwareMap.get(DcMotorEx.class, "extendo");
            extendo.setDirection(DcMotorEx.Direction.REVERSE);
            extendo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            resetExtendo();
            this.telemetry = telemetry;
        }

        public class ExtendoOut implements Action {
            int pos = 850;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                extendo.setTargetPosition(pos);
                extendo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extendo.setPower(1);

                while(Math.abs(extendo.getCurrentPosition()) < Math.abs(pos)){
                    extendo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("Position" , extendo.getCurrentPosition());
                    telemetry.update();
                }

                extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                extendo.setPower(0);
                return false;
            }
        }

        public class ExtendoIn implements Action {

            int pos = -50;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                extendo.setTargetPosition(pos);
                extendo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extendo.setPower(1);

                while(Math.abs(extendo.getCurrentPosition()) > Math.abs(pos)){
                    extendo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("Position" , extendo.getCurrentPosition());
                    telemetry.update();
                }

                extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                extendo.setPower(0);
                return false;
            }
        }

        public Action ExtendoIn() {
            return new ExtendoIn();
        }

        public Action ExtendoOut() {
            return new ExtendoOut();
        }
    }


    public static class Intake {
        private final Servo claw, elbowR, elbowL, wristL, wristR;
        private Telemetry telemetry;

        public Intake(HardwareMap hardwareMap) {
            claw = hardwareMap.get(Servo.class, "claw");
            wristL = hardwareMap.servo.get("wristL");
            wristR = hardwareMap.servo.get("wristR");
            elbowR = hardwareMap.servo.get("elbowR");
            elbowL = hardwareMap.servo.get("elbowL");
        }

        public class PrepIntake implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wristR.setPosition(1-((135)/(255*2.5)));
                wristL.setPosition(((135)/(255*2.5)));
                elbowR.setPosition(0.66);
                elbowL.setPosition(0.34);
                return false;
            }
        }

        public Action prepIntake() {
            return new PrepIntake();
        }
        public class Intake90 implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                double R = 70 / (255 * 2.5);
                //0.8980
                wristR.setPosition(1 - ((135) / (255 * 1.6)) + R);
                //0.3216
                wristL.setPosition(((135) / (255 * 1.6)) + R);
                return false;
            }
        }

        public Action intake90() {
            return new Intake90();
        }


        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0.375);
                return false;
            }
        }

        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0);
                return false;
            }
        }

        public Action openClaw() {
            return new OpenClaw();
        }

        public class IntakeDown implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wristL.setPosition(((135.0)/(255.0*(1.6))));
                wristR.setPosition(1.0-((135.0)/(255.0*(1.6))));
                elbowR.setPosition(0.98);
                elbowL.setPosition(0.02);
                claw.setPosition(0);
                return false;
            }

        }

        public Action IntakeDown() {
            return new IntakeDown();
        }

        public class IntakeUp implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0.75);
                elbowR.setPosition(0.35);
                elbowL.setPosition(1-0.35);
                wristL.setPosition(((135)/(255*1.6)));
                wristR.setPosition(1-((135)/(255*1.6)));
                return false;
            }
        }

        public Action  IntakeUp() {
            return new IntakeUp();
        }

        public class Loosen implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0.24);
                return false;
            }
        }

        public Action  Loosen() {
            return new Loosen();
        }

        public class IntakeB implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                elbowR.setPosition(0.8);
                elbowL.setPosition(0.2);
                return false;
            }
        }

        public Action  IntakeB() {
            return new IntakeB();
        }

        public class IntakeTuck implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                elbowR.setPosition(0.35);
                elbowL.setPosition(1-0.35);
                wristL.setPosition(((135)/(255*2.5)));
                wristR.setPosition(1-((135)/(255*2.5)));
                return false;
            }
        }


        public Action intakeTuck(){
            return new IntakeTuck();
        }


    }


    public static class Outtake {
        private final Servo outL, outClaw, outR, outWrist;
        private Telemetry telemetry;

        public Outtake(HardwareMap hardwareMap, Telemetry telemetry) {
            outL = hardwareMap.servo.get("outL");
            outClaw = hardwareMap.servo.get("outClaw");
            outWrist = hardwareMap.servo.get("outWrist");
            outR = hardwareMap.servo.get("outR");

            this.telemetry = telemetry;
        }

        public class BoxDown implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outL.setPosition((0));
                outR.setPosition(1);
                outWrist.setPosition(0);
                return false;
            }
        }

        public Action BoxDown() {
            return new BoxDown();
        }

        public class dumpBox implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outL.setPosition((0.6));
                outR.setPosition(0.4);
                outWrist.setPosition(0);
                return false;
            }
        }

        public Action DumpBox(){
            return new dumpBox();
        }

        public class SpecDumpBox implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outL.setPosition((0.6));
                outR.setPosition(0.4);
                return false;
            }
        }

        public Action specDumpBox() {
            return new SpecDumpBox();
        }



        public class OuttakeTuck implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outL.setPosition((0.17));
                outR.setPosition(0.83);
                outWrist.setPosition(0.18);
                return false;
            }
        }

        public Action OuttakeTuck(){
            return new OuttakeTuck();
        }



        public class CloseOutClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outClaw.setPosition(0.35);
                return false;
            }
        }

        public Action closeOutClaw() {
            return new CloseOutClaw();
        }

        public class OpenOutClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outClaw.setPosition(0);
                return false;
            }
        }

        public Action openOutClaw() {
            return new OpenOutClaw();
        }


        public class OutWristDowm implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outWrist.setPosition(0.3);
                return false;
            }
        }

        public Action OutWristDown(){
            return new OutWristDowm();
        }

        public class OutWristUp implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outWrist.setPosition(0);
                return false;
            }
        }

        public Action OutWristUp(){
            return new OutWristUp();
        }


    }

}
