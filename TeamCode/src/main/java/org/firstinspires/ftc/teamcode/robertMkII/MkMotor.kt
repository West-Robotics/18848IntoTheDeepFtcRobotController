package org.firstinspires.ftc.teamcode.robertMkII

import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import com.qualcomm.robotcore.hardware.HardwareMap

class MkMotor(
    hardwareMap: HardwareMap,
    name: String,
    dir: Direction,
    zpb: ZeroPowerBehavior
) {
    private val motor = hardwareMap.dcMotor.get(name)
    
}