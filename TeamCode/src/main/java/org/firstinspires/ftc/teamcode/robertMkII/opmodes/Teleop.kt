package org.firstinspires.ftc.teamcode.robertMkII.opmodes

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robertMkII.opmodes.TeleConstants.debug
import org.firstinspires.ftc.teamcode.robertMkII.opmodes.TeleConstants.dummyOn
import org.firstinspires.ftc.teamcode.robertMkII.opmodes.TeleConstants.extenderTimeLimit
import org.firstinspires.ftc.teamcode.robertMkII.opmodes.TeleConstants.rotatorTimeLimit
import org.firstinspires.ftc.teamcode.robertMkII.opmodes.TeleConstants.topExtensionLimit
import org.firstinspires.ftc.teamcode.robertMkII.opmodes.TeleConstants.topRotationLimit
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Extender
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Intake
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Rotator
import kotlin.math.abs

/*
TODO:
- time extender?
- g1 speed toggle
-
 */

@Config
object TeleConstants {
    @JvmField var topExtensionLimit = 1200
    @JvmField var topRotationLimit = 2000
    @JvmField var extenderTimeLimit = 1.0
    @JvmField var rotatorTimeLimit = 0.75
    @JvmField var dummyOn = false
    @JvmField var debug = false
}
@TeleOp(name = "MkTele", group = "Mk")
class Teleop: LinearOpMode() {

    private val er = ElapsedTime()
    private val ex = ElapsedTime()

    override fun runOpMode() {
        val telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)

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
        var manualExt = true
        var manualRot = false
        var rotOverride = false
        var extOverride = false


        waitForStart()
        intake.wristPos = Intake.HandPosition.INTAKE
        while (opModeIsActive()) {
            previousGamepad1.copy(currentGamepad1)
            previousGamepad2.copy(currentGamepad2)

            currentGamepad1.copy(gamepad1)
            currentGamepad2.copy(gamepad2)

            drivetrain.setSpeed(
                -currentGamepad1.left_stick_x.toDouble(),
                -currentGamepad1.left_stick_y.toDouble(),
                -currentGamepad1.right_stick_x.toDouble()
            )
            drivetrain.write()

           //extension
            if (currentGamepad2.b && !previousGamepad2.b) {
                targetExtPos = if (targetExtPos > 0.0) -1.0 else topExtensionLimit.toDouble()
                extOverride = false
                ex.reset()
            }
            if (currentGamepad2.right_bumper && !previousGamepad2.right_bumper) {
                manualExt = !manualExt
                currentGamepad2.rumble(100)
            }
            if ((abs(extender.getEffort()) > 0.85 && abs(extender.getVelocity()) < 10) || ex.seconds() > extenderTimeLimit) {
                if (extender.getEffort() >0) {
                    topExtensionLimit = extender.getTicks()
                } else {
                    extender.resetEncoder(0)
                }
                extOverride = true
            }
            if (!manualExt) {
                if (!extOverride) {
                    extender.runToPos(targetExtPos, extender.getTicks().toDouble())
                }
            } else {
                extender.setEffort(-currentGamepad2.left_stick_y.toDouble())
                if (currentGamepad2.dpad_down && !previousGamepad2.dpad_down) {
                    extender.resetEncoder()
                } else if (currentGamepad2.dpad_up && !previousGamepad2.dpad_up) {
                    extender.resetEncoder(topExtensionLimit)
                }
            }
            extender.write()

            //rotation
            if (currentGamepad2.y && !previousGamepad2.y) {
                //targetRotPos = if (targetRotPos >0.0) 0.0 else topRotationLimit.toDouble()
                targetRotPos = if (targetRotPos >0.0) -1.0 else 1.0
                rotOverride = false
                er.reset()
            }
            if (currentGamepad2.back && !previousGamepad2.back) {
                manualRot = !manualRot
                rotOverride = true
            }
            if (er.seconds() >= rotatorTimeLimit) {
                rotator.setEffort(0.0)
                rotOverride = true
            }
            if (!manualRot) {
                if (!rotOverride){
                    //rotator.runToPos(targetRotPos, rotator.getTicks().toDouble())
                    rotator.setEffort(targetRotPos)
                }
            } else {
                rotator.setEffort(currentGamepad2.right_stick_y.toDouble())
                if (currentGamepad2.dpad_left && !previousGamepad2.dpad_left) {
                    topRotationLimit = rotator.getTicks()
                } else if (currentGamepad2.dpad_right && !previousGamepad2.dpad_right) {
                    rotator.resetEncoder()
                }
            }
            rotator.write()

            //Intake
            if (currentGamepad2.a && !previousGamepad2.a) {
                intake.wristPos = if (intake.wristPos == Intake.HandPosition.INTAKE) Intake.HandPosition.OUTTAKE else Intake.HandPosition.INTAKE
            }
            if (dummyOn) {
                intake.wristPos = Intake.HandPosition.DUMMY
            }
            if (intake.wristPos == Intake.HandPosition.INTAKE) {
                intake.setSpinSpeed(-currentGamepad2.left_trigger.toDouble()+currentGamepad2.right_trigger.toDouble())
            } else {
                intake.setSpinSpeed(currentGamepad2.left_trigger.toDouble()-currentGamepad2.right_trigger.toDouble())
            }
            intake.writeSpinner()


            if (debug) {
                telemetry.addData("extensions", extender.getTicks())
                telemetry.addData("rotations", rotator.getTicks())
                //telemetry.addData("Top Extension Limit", topExtensionLimit)
                //telemetry.addData("Top Rotation Limit", topRotationLimit)
                telemetry.addData("Extension force", extender.getEffort())
                telemetry.addData("Rotation force", rotator.getEffort())
                telemetry.addData("Wrist Position", intake.wristPos)
                telemetry.addData("Extender tickV", extender.getVelocity())
                telemetry.addData("Rotator tickV", rotator.getVelocity())
                telemetry.addData("Rotator targetPos", targetRotPos)
                telemetry.addData("Extender targetPos", targetExtPos)
                telemetry.addData("Rotator current", rotator.getCurrent())
                telemetry.addData("Extender current", extender.getCurrent())
                telemetry.addData("e", er.seconds())
            }
            telemetry.addLine("g2 y = toggle rot")
            telemetry.addLine("g2 a = toggle wrist")
            telemetry.addLine("g2 rt = spin correct")
            telemetry.update()
        }
    }
}