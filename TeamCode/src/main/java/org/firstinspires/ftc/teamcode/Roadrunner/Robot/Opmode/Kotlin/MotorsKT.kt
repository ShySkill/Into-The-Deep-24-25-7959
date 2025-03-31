package org.firstinspires.ftc.teamcode.Roadrunner.Robot.Opmode.Kotlin

import com.qualcomm.robotcore.hardware.*


object MotorsKT {
    lateinit var hardwareMap: HardwareMap

    val motors = mutableMapOf<String, DcMotorEx>()
    val servos = mutableMapOf<String, Servo>()

    fun init(hardwareMap: HardwareMap) {
        this.hardwareMap = hardwareMap

        val motorNames = listOf("leftFront", "leftBack", "rightBack", "rightFront", "liftL", "extendo")
        motorNames.forEach { name ->
            motors[name] = hardwareMap.get(DcMotorEx::class.java, name)
        }

        val servoNames = listOf("wristL", "wristR", "claw", "outL", "elbowR", "elbowL", "outR", "outWrist", "outClaw")
        servoNames.forEach { name ->
            servos[name] = hardwareMap.get(Servo::class.java, name)
        }
    }

    fun encoderPrep(){
        runWithoutEncoder()
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)
    }

    fun getMotor(name: String) = motors[name]
    fun getServo(name: String) = servos[name]
    fun runWithoutEncoder() {
        motors.forEach { (name, motor) ->
            motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        }
    }

    fun setZeroPowerBehavior(behavior: DcMotor.ZeroPowerBehavior) {
        motors.forEach { (_, motor) ->
            motor.zeroPowerBehavior = behavior
        }
    }


    fun resetEncoders() {
        MotorsKT.motors.values.forEach { motor ->
            motor.mode = DcMotor.RunMode.RESET_ENCODERS
        }
    }

    fun runToPosition() {
        MotorsKT.motors.values.forEach { motor ->
            motor.mode = DcMotor.RunMode.RUN_TO_POSITION
        }
    }

    fun runWithEncoder() {
        MotorsKT.motors.values.forEach { motor ->
            motor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        }
    }

    fun setTargetPositions(LF: Int, RF: Int, LB: Int, RB: Int) {
        MotorsKT.motors["rightBack"]?.targetPosition = RB
        MotorsKT.motors["rightFront"]?.targetPosition = RF
        MotorsKT.motors["leftFront"]?.targetPosition = LF
        MotorsKT.motors["leftBack"]?.targetPosition = LB
    }

    fun setTargetPosition(pos: Int) {
        MotorsKT.motors.values.forEach { motor ->
            motor.targetPosition = pos
        }
    }


}
