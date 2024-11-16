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
    public DriveTrain(HardwareMap hardwareMap, Telemetry telemetryImport) /* INIT */ {
        telemetry = telemetryImport;
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addLine("Ready for Action");
    }

    public void tankDrive(double straightSpeed, double strafeSpeed, double rotationSpeed) {

        leftFront.setPower(straightSpeed + rotationSpeed + strafeSpeed);
        leftBack.setPower(straightSpeed + rotationSpeed - strafeSpeed);
        rightFront.setPower(straightSpeed - rotationSpeed - strafeSpeed);
        rightBack.setPower(straightSpeed - rotationSpeed + strafeSpeed);

        telemetry.addData("Left Y", straightSpeed);
        telemetry.addData("Left X", strafeSpeed);
        telemetry.addData("Right X", rotationSpeed);
    }

}
