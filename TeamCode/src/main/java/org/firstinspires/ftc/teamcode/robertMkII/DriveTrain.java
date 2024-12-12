package org.firstinspires.ftc.teamcode.robertMkII;

// for controlling the robot

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveTrain {

    private Telemetry telemetry;
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;
    private DcMotor armExtender;
    private DcMotor armRotater;
    private CRServo handIntake;
    private Servo rightWrist;
    private Servo leftWrist;
   // private HandPosition wristPos;
    private double dumpPos = 0;
    private double levelPos = 1;
    public DriveTrain(HardwareMap hardwareMap, Telemetry telemetryImport) /* INIT */ {
        telemetry = telemetryImport;
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        armExtender = hardwareMap.get(DcMotor.class, "armExtender");
        armRotater = hardwareMap.get(DcMotor.class, "armRotater");
/*
        rightWrist = hardwareMap.get(Servo.class, "rightWrist");
        leftWrist = hardwareMap.get(Servo.class, "leftWrist");
        handIntake = hardwareMap.get(CRServo.class, "handIntake");
*/
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
//        leftWrist.setDirection(Servo.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        armExtender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRotater.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        armExtender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotater.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armExtender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armRotater.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

       // wristPos = HandPosition.LEVEL;
    }

    public void tankDrive(double straightSpeed, double strafeSpeed, double rotationSpeed) {

        leftFront.setPower(straightSpeed - rotationSpeed - strafeSpeed);
        leftBack.setPower(straightSpeed - rotationSpeed + strafeSpeed);
        rightFront.setPower(straightSpeed + rotationSpeed + strafeSpeed);
        rightBack.setPower(straightSpeed + rotationSpeed - strafeSpeed);

    }
    public void moveArm(double extendSpeed, double rotateSpeed) {
        // motor uses 537.7 ppr
        // diameter of pulley gear is 120mm
        // slide extension (difference between fully retracted and fully extended) amount is 96 cm
        // full rotations until extended is 8
        // worm gear is 28:1 or 14:0.5
        /*
        double extenderRevCount = armExtender.getCurrentPosition() / 537.7;
        double rotationCount = armRotater.getCurrentPosition() / 537.7;
        double armlength = (extenderRevCount * 12.0) + 38.4;
        double armangle = rotationCount * (90.0/14.0);
        double botlength = armlength * Math.sin(Math.toRadians(armangle));


        if ((extenderRevCount < 7.8 && extendSpeed > 0 && botlength < 100) || (extenderRevCount > 0.2 && extendSpeed < 0)) {
            armExtender.setPower(extendSpeed);
        } else { armExtender.setPower(0); }
        if ((rotationCount < 13.5 && rotateSpeed < 0) || (rotationCount > 0.2 && rotateSpeed > 0 && botlength < 100)) {
            armRotater.setPower(rotateSpeed);
        } else { armRotater.setPower(0); }
        */

        armExtender.setPower(extendSpeed);
        armRotater.setPower(rotateSpeed);
        telemetry.addData("rotations", armRotater.getCurrentPosition());
        telemetry.addData("extensions", armExtender.getCurrentPosition());

    }
    enum HandPosition {
        DUMP,
        LEVEL
    }
   /*
    public void moveHand(boolean toggleHandPos, double handIntakeSpeed) {
        handIntake.setPower(handIntakeSpeed);
        HandPosition prevWristPos = wristPos;
        if (toggleHandPos) {
            wristPos = (wristPos == HandPosition.LEVEL) ? HandPosition.DUMP : HandPosition.LEVEL;
        }
        telemetry.addData("WristPos", wristPos);
        telemetry.addData("dumpPos", dumpPos);
        telemetry.addData("levelPos", levelPos);
        if (wristPos == HandPosition.DUMP) {
            leftWrist.setPosition(dumpPos);
            rightWrist.setPosition(dumpPos);
        } else if (wristPos == HandPosition.LEVEL) {
            leftWrist.setPosition(levelPos);
            rightWrist.setPosition(dumpPos);
        }
    }
    public void incrementPos(double levelPosMod, double dumpPosMod) {
        double prevDumpPos = dumpPos;
        double prevLevelPos = levelPos;
        dumpPos += dumpPosMod;
        if (dumpPos > 1.0 || dumpPos < 0) {
            dumpPos = prevDumpPos;
        }
        levelPos += levelPosMod;
        if (levelPos > 1.0 || levelPos < 0) {
            levelPos = prevLevelPos;
        }
    }*/
}