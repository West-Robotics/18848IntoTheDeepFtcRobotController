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
    private CRServo handIntake;
    private Servo rightWrist;
    private Servo leftWrist;
    private HandPosition wristPos;
    private HandPosition lastWristPos;
    public double dumpPos = 0;
    public double levelPos = 1;
    public DriveTrain(HardwareMap hardwareMap, Telemetry telemetryImport) /* INIT */ {
        telemetry = telemetryImport;
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        armExtender = hardwareMap.get(DcMotor.class, "armExtender");

        rightWrist = hardwareMap.get(Servo.class, "rightWrist");
        leftWrist = hardwareMap.get(Servo.class, "leftWrist");
        handIntake = hardwareMap.get(CRServo.class, "handIntake");

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftWrist.setDirection(Servo.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        armExtender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        armExtender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armExtender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        wristPos = HandPosition.LEVEL;
        lastWristPos = HandPosition.LEVEL;
    }

    public void tankDrive(double straightSpeed, double strafeSpeed, double rotationSpeed) {

        leftFront.setPower(straightSpeed - rotationSpeed - strafeSpeed);
        leftBack.setPower(straightSpeed - rotationSpeed + strafeSpeed);
        rightFront.setPower(straightSpeed + rotationSpeed + strafeSpeed);
        rightBack.setPower(straightSpeed + rotationSpeed - strafeSpeed);

    }
/*
    public void manipulateArm(double extendSpeed, double rotateSpeed) {
        // motor uses 537.7 ppr
        // diameter of pulley gear is 120mm
        // slide extension amount is 107 cm
        // full rotations until extended is 59
        // worm gear is 28:1 or 14:0.5
        double extenderRevCount = armExtender.getCurrentPosition() / 537.7;
        double rotationCount = armRotater.getCurrentPosition() / 537.7;
        if ((extenderRevCount < 89 && extendSpeed > 0) || (extenderRevCount > 2 && extendSpeed < 0)) {
            armExtender.setPower(extendSpeed);
        } else { armExtender.setPower(0); }
        if ((rotationCount < 13.5 && rotateSpeed > 0) || (rotationCount > 2 && rotateSpeed < 0)) {
            armRotater.setPower(rotateSpeed);
        } else { armRotater.setPower(0); }

    }
*/
    enum HandPosition {
        DUMP,
        LEVEL
    }
    public void moveHand(boolean toggleHandPos, double handIntakeSpeed, double armExtendSpeed) {
        armExtender.setPower(armExtendSpeed);
        handIntake.setPower(handIntakeSpeed);

        lastWristPos = wristPos;
        wristPos = (toggleHandPos && wristPos==HandPosition.LEVEL) ? HandPosition.DUMP : HandPosition.LEVEL;
        telemetry.addData("WristPos", (wristPos ==HandPosition.LEVEL) ? "LEVEL" : "DUMP");
        if (wristPos ==HandPosition.DUMP && lastWristPos !=HandPosition.DUMP) {
            leftWrist.setPosition(dumpPos);
            rightWrist.setPosition(dumpPos);
        } else if (wristPos ==HandPosition.LEVEL && lastWristPos !=HandPosition.LEVEL) {
            leftWrist.setPosition(levelPos);
            rightWrist.setPosition(dumpPos);
        }
    }
}
