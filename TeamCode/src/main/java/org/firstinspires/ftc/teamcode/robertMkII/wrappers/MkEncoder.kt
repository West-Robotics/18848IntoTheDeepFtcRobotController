package org.firstinspires.ftc.teamcode.robertMkII.wrappers

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import com.qualcomm.robotcore.hardware.HardwareMap

class MkEncoder(
    hardwareMap: HardwareMap,
    name: String,
    val dir: Direction,
    val ticksPerRev: Double,
    ) {
    private val motor = hardwareMap.dcMotor.get(name) as DcMotorEx
    private var tickOffset = 0

    val revs
        get() = ticks / ticksPerRev
    val ticks
        get() = when (dir) {
            Direction.FORWARD -> 1
            Direction.REVERSE -> -1
        } * (motor.currentPosition - tickOffset)

    val tickV
        get() = motor.velocity

    fun reset(ticks: Int) {
        tickOffset = motor.currentPosition - ticks
    }
}