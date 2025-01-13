package org.firstinspires.ftc.teamcode.robertMkII.opmodes

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.robertMkII.subsystems.Drivetrain

@Config
object AutoContstants {
    @JvmField var time = 2.0
}

@Autonomous(name = "real auto", group = "Mk", preselectTeleOp = "MkTele")
class Auto: LinearOpMode() {

    override fun runOpMode() {
        val e = ElapsedTime()
        val drivetrain = Drivetrain(hardwareMap)
        waitForStart()
        e.reset()
        while (opModeIsActive()) {
            if (e.seconds() < AutoContstants.time) {
                drivetrain.setSpeed(-0.5, 0.0, 0.0)
            } else {
                drivetrain.setSpeed(0.0, 0.0, 0.0)
            }
            drivetrain.write()
            telemetry.addLine((30 - e.seconds()).toString())
            telemetry.update()
        }
    }
}