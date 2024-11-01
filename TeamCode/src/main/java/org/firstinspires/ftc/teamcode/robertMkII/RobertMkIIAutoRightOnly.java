package org.firstinspires.ftc.teamcode.robertMkII;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
TODO:
- change length of moving right
- add more function than moving right
 */

@Autonomous(name = "RobertMkIIAutoRightOnly")
public class RobertMkIIAutoRightOnly extends LinearOpMode {

    @Override public void runOpMode() throws InterruptedException {
        DriveTrain driveTrain = new DriveTrain(hardwareMap, telemetry);

        ElapsedTime e = new ElapsedTime();
        e.reset();
        telemetry.addLine("Right only Autonomous");
        telemetry.addData("Time elapsed", 0);
        telemetry.addData("Status", "Wait");
        waitForStart();
        e.reset();
        while (opModeIsActive()) {
            if (e.seconds() < 2.5) {
                telemetry.addData("Status", "Moving Right");
                driveTrain.tankDrive(0,-1,0);
            } else {
                telemetry.addData("Status", "All Done!");
                driveTrain.tankDrive(0,0,0);
            }
            telemetry.addData("Time elapsed", e.seconds());
        }
    }

}
