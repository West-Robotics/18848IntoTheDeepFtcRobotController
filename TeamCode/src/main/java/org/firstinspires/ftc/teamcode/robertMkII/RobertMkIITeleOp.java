package org.firstinspires.ftc.teamcode.robertMkII;
/*
TODO:
- make hand pos preset
- buy bricks
- remeber to uncomment all of the arm things
- test
*/
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "RobertMkIITeleOp")
public class RobertMkIITeleOp extends OpMode {

    private DriveTrain drivetrain;

    @Override
    public void init() {
        drivetrain = new DriveTrain(hardwareMap, telemetry);
        drivetrain.levelPos = 1;
        drivetrain.dumpPos = 0;
    }


    @Override
    public void loop() {
        if (!gamepad1.left_bumper) {
            drivetrain.tankDrive(-gamepad1.left_stick_y/2, gamepad1.left_stick_x/2, gamepad1.right_stick_x/2);
            drivetrain.moveHand(gamepad2.y, gamepad2.left_trigger-gamepad2.right_trigger, gamepad2.left_stick_y/2);
        } else {
            drivetrain.moveHand(gamepad2.y, gamepad2.left_trigger-gamepad2.right_trigger, gamepad2.left_stick_y);
            drivetrain.tankDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }
}