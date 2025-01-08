package org.firstinspires.ftc.teamcode.robertMkII

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.Telemetry

// for controlling the robot

class OldDriveTrain(hardwareMap: HardwareMap, private val telemetry: Telemetry) {
    private val leftFront: DcMotor = hardwareMap.get(DcMotor::class.java, "leftFront")
    private val leftBack: DcMotor = hardwareMap.get(DcMotor::class.java, "leftBack")
    private val rightFront: DcMotor = hardwareMap.get(DcMotor::class.java, "rightFront")
    private val rightBack: DcMotor = hardwareMap.get(DcMotor::class.java, "rightBack")
    private val armExtender: DcMotor = hardwareMap.get(DcMotor::class.java, "armExtender")
    private val armRotater: DcMotor = hardwareMap.get(DcMotor::class.java, "armRotater")
    private val handIntake: CRServo = hardwareMap.get(CRServo::class.java, "handIntake")
    private val rightWrist: Servo = hardwareMap.get(Servo::class.java, "rightWrist")
    private val leftWrist: Servo = hardwareMap.get(Servo::class.java, "leftWrist")
    private var wristPos: HandPosition
    private var dumpPos = 1.0
    private var levelPos = 0.7

    init  /* INIT */ {
        leftBack.direction = DcMotorSimple.Direction.REVERSE
        leftFront.direction = DcMotorSimple.Direction.REVERSE
        leftWrist.direction = Servo.Direction.REVERSE

        leftFront.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        leftBack.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        rightFront.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        rightBack.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT

        armExtender.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        armRotater.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        leftFront.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        leftBack.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        rightFront.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        rightBack.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        armExtender.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        armRotater.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        armExtender.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        armRotater.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        wristPos = HandPosition.LEVEL
    }

    fun tankDrive(straightSpeed: Double, strafeSpeed: Double, rotationSpeed: Double) {
        leftFront.power = straightSpeed - rotationSpeed - strafeSpeed
        leftBack.power = straightSpeed - rotationSpeed + strafeSpeed
        rightFront.power = straightSpeed + rotationSpeed + strafeSpeed
        rightBack.power = straightSpeed + rotationSpeed - strafeSpeed
    }

    fun moveArm(extendSpeed: Double, rotateSpeed: Double) {
        // motor uses 537.7 ppr
        // diameter of pulley gear is 120mm
        // slide extension (difference between fully retracted and fully extended) amount is 96 cm
        // full rotations until extended is 8
        // worm gear is 28:1 or 14:0.5

        val rotPos = armRotater.currentPosition.toDouble()
        val extPos = armExtender.currentPosition.toDouble()
        if (rotPos < 1400 && extPos < -1000) {
            armExtender.power = 0.6
        } else if (rotPos < 1000) {
            armExtender.power = extendSpeed
        } else {
            armExtender.power = extendSpeed - 0.1
        }

        armRotater.power = rotateSpeed
        telemetry.addData("extensionForce", extendSpeed - 0.1)
        telemetry.addData("rotationForce", rotateSpeed)

        telemetry.addData("rotations", armRotater.currentPosition)
        telemetry.addData("extensions", armExtender.currentPosition)
    }

    internal enum class HandPosition {
        DUMP,
        LEVEL
    }

    fun moveHand(toggleHandPos: Boolean, handIntakeSpeed: Double) {
        handIntake.power = handIntakeSpeed
        if (toggleHandPos) {
            wristPos =
                if ((wristPos == HandPosition.LEVEL)) HandPosition.DUMP else HandPosition.LEVEL
            if (wristPos == HandPosition.DUMP) {
                leftWrist.position = dumpPos
                rightWrist.position = dumpPos
            } else {
                leftWrist.position = levelPos
                rightWrist.position = levelPos
            }
        }
        telemetry.addData("WristPos", wristPos)
        telemetry.addData("dumpPos", dumpPos)
        telemetry.addData("levelPos", levelPos)
    }

    fun incrementPos(levelPosMod: Double, dumpPosMod: Double) {
        val prevDumpPos = dumpPos
        val prevLevelPos = levelPos
        dumpPos += dumpPosMod
        if (dumpPos > 1.0 || dumpPos < -1.0) {
            dumpPos = prevDumpPos
        }
        levelPos += levelPosMod
        if (levelPos > 1.0 || levelPos < -1.0) {
            levelPos = prevLevelPos
        }
        if (levelPos != prevLevelPos && wristPos == HandPosition.LEVEL) {
            leftWrist.position = dumpPos
            rightWrist.position = dumpPos
        }
        if (dumpPos != prevDumpPos && wristPos == HandPosition.DUMP) {
            leftWrist.position = levelPos
            rightWrist.position = levelPos
        }
    }

    fun resetEncoder(extender: Boolean, rotator: Boolean) {
        if (extender) {
            armExtender.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            armExtender.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        } else if (rotator) {
            armRotater.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            armRotater.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        }
    }
}