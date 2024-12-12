package org.firstinspires.ftc.teamcode.robertMkII;
/*
TODO:
 - idek
 - make
*/
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;


@TeleOp(name = "RobertMkIITeleOp", group = "RobertMkII")
public class RobertMkIITeleOp extends OpMode {
    private Gamepad currentGamepad1;
    private Gamepad currentGamepad2;

    private Gamepad previousGamepad1;
    private Gamepad previousGamepad2;
    private DriveTrain drivetrain;

    @Override
    public void init() {
        drivetrain = new DriveTrain(hardwareMap, telemetry);
        currentGamepad1 = new Gamepad();
        currentGamepad2 = new Gamepad();

        previousGamepad1 = new Gamepad();
        previousGamepad2 = new Gamepad();
    }


    @Override
    public void loop() {
        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad1.copy(gamepad1);
        currentGamepad2.copy(gamepad2);
        if (!currentGamepad1.left_bumper) {
            drivetrain.tankDrive(-currentGamepad1.left_stick_y / 2, currentGamepad1.left_stick_x / 2, currentGamepad1.right_stick_x / 2);
            drivetrain.moveArm(-currentGamepad2.left_stick_y / 2, -currentGamepad2.right_stick_y / 2);
        } else {
            drivetrain.tankDrive(-currentGamepad1.left_stick_y, currentGamepad1.left_stick_x, currentGamepad1.right_stick_x);
            drivetrain.moveArm(-currentGamepad2.left_stick_y, -currentGamepad2.right_stick_y);
        }
        if (currentGamepad2.y && !previousGamepad2.y) {
            drivetrain.moveHand(true, currentGamepad2.left_trigger - currentGamepad2.right_trigger);
        } else {
            drivetrain.moveHand(false, currentGamepad2.left_trigger - currentGamepad2.right_trigger);
        }
        if (currentGamepad2.dpad_up && !previousGamepad2.dpad_up) {
            drivetrain.incrementPos(0.1, 0);
        } else if (currentGamepad2.dpad_down && !previousGamepad2.dpad_down) {
            drivetrain.incrementPos(-0.1, 0);
        } if (currentGamepad2.dpad_right && !previousGamepad2.dpad_right) {
            drivetrain.incrementPos(0, 0.1);
        } else if (currentGamepad2.dpad_left && !previousGamepad2.dpad_left) {
            drivetrain.incrementPos(0, -0.1);
        }
    }
}
