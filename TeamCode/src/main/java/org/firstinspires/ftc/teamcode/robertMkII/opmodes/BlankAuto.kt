package org.firstinspires.ftc.teamcode.robertMkII.opmodes

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Drivetrain


@Autonomous(name = "blank auto", group = "Mk", preselectTeleOp = "MkTele")
class BlankAuto: LinearOpMode() {

    override fun runOpMode() {
        val e = ElapsedTime()
        waitForStart()
        e.reset()
        while (opModeIsActive()) {
            telemetry.addLine((30 - e.seconds()).toString())
            telemetry.update()
        }
    }
}