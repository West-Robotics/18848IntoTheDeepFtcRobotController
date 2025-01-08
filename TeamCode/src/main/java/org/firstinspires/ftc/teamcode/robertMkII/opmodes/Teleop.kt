package org.firstinspires.ftc.teamcode.robertMkII.opmodes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Extender
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Intake
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Rotator
import kotlin.math.abs





@TeleOp(name = "MkTele")
class Teleop: LinearOpMode() {

    val topExtensionLimit = 1200.0
    val topRotationLimit = 1200.0

    override fun runOpMode() {
        val previousGamepad1 = Gamepad()
        val previousGamepad2 = Gamepad()
        val currentGamepad1 = Gamepad()
        val currentGamepad2 = Gamepad()

        val drivetrain = Drivetrain(hardwareMap)
        val extender = Extender(hardwareMap)
        val rotator = Rotator(hardwareMap)
        val intake = Intake(hardwareMap)

        var targetExtPos = 0.0
        var targetRotPos = 0.0
        var manualExt = false
        var manualRot = false

        waitForStart()
        while (opModeIsActive()) {
            previousGamepad1.copy(currentGamepad1)
            previousGamepad2.copy(currentGamepad2)

            currentGamepad1.copy(gamepad1)
            currentGamepad2.copy(gamepad2)

            drivetrain.setSpeed(
                currentGamepad1.left_stick_x.toDouble(),
                -currentGamepad1.left_stick_y.toDouble(),
                currentGamepad1.right_stick_x.toDouble()
            )
            drivetrain.write()

           //extension
            if (currentGamepad2.b && !previousGamepad2.b) {
               targetExtPos = if (targetExtPos > 0.0) 0.0 else topExtensionLimit
            }
            if (currentGamepad2.start && !previousGamepad2.start) {
               manualExt = !manualExt
            }
            if (abs(extender.getEffort()) > 0.25 && extender.getVelocity() < 100) {
                if (extender.getEffort() >0) {
                    extender.resetEncoder(topExtensionLimit.toInt())
                } else {
                    extender.resetEncoder(0)
                }
            }
            if (!manualExt) {
               extender.runToPos(targetExtPos, extender.getTicks().toDouble())
            } else {
                extender.setEffort(-currentGamepad2.left_stick_y.toDouble())
                if (currentGamepad2.dpad_down && !previousGamepad2.dpad_down) {
                    extender.resetEncoder()
                } else if (currentGamepad2.dpad_up && !previousGamepad2.dpad_up) {
                    extender.resetEncoder(topExtensionLimit.toInt())
                }
            }
            extender.write()

            //rotation
            if (currentGamepad2.a && !previousGamepad2.a) {
                targetRotPos = if (targetRotPos >0.0) 0.0 else topRotationLimit
            }
            if (currentGamepad2.back && !previousGamepad2.back) {
                manualRot = !manualRot
            }
            if (abs(rotator.getEffort()) > 0.25 && rotator.getVelocity() < 100) {
                if (rotator.getEffort() >0) {
                    rotator.resetEncoder(topExtensionLimit.toInt())
                } else {
                    rotator.resetEncoder()
                }
            }
            if (!manualRot) {
                rotator.runToPos(targetRotPos, rotator.getTicks().toDouble())
            } else {
                rotator.setEffort(-currentGamepad2.right_stick_y.toDouble())
                if (currentGamepad2.dpad_left && !previousGamepad2.dpad_left) {
                    rotator.resetEncoder(topRotationLimit.toInt())
                } else if (currentGamepad2.dpad_right && !previousGamepad2.dpad_right) {
                    rotator.resetEncoder()
                }
            }
            rotator.write()

            //Intake
            if (currentGamepad2.y && !previousGamepad2.y) {
                intake.wristPos = if (intake.wristPos == Intake.HandPosition.LEVEL) Intake.HandPosition.DUMP else Intake.HandPosition.LEVEL
            }
            intake.setSpinSpeed(currentGamepad2.left_trigger.toDouble()-currentGamepad2.right_trigger.toDouble())
            intake.writeSpinner()


            telemetry.addData("extensions", extender.getTicks())
            telemetry.addData("rotations", rotator.getTicks())
            telemetry.update()
        }
    }
}