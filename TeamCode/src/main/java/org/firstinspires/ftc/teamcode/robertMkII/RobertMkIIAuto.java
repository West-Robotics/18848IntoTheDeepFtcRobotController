package org.firstinspires.ftc.teamcode.robertMkII;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
TODO:
- change length of moving right
- add more function than moving right
 */

@Autonomous(name = "RobertMkIIAuto")
public class RobertMkIIAuto extends LinearOpMode {

    @Override public void runOpMode() throws InterruptedException {
        DriveTrain driveTrain = new DriveTrain(hardwareMap, telemetry);

        ElapsedTime e = new ElapsedTime();
        telemetry.addData("Stage", "Wait");
        waitForStart();
        e.reset();
        while (opModeIsActive()) {
            if (e.seconds() < 2.5) {
                telemetry.addData("Stage", "Left");
                driveTrain.tankDrive(0,1,0);
            } else if (e.seconds() > 2.5 && e.seconds() < 6){
                telemetry.addData("Stage", "Right");
                driveTrain.tankDrive(0,-1,0);
            } else {
                telemetry.addData("Stage", "Done!");
                driveTrain.tankDrive(0,0,0);
            }
        }
    }

}
