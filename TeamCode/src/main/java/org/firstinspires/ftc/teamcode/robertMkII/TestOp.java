package org.firstinspires.ftc.teamcode.robertMkII;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "ServoTestOp")
public class TestOp extends OpMode {
    private CRServo servo;

    @Override
    public void init() { servo = hardwareMap.get(CRServo.class, "servo"); }

    @Override
    public void loop() {
        servo.setPower(gamepad1.left_trigger-gamepad1.right_trigger);
    }
}
