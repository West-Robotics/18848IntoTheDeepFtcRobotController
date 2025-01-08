package org.firstinspires.ftc.teamcode.robertMkII.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.robertMkII.controlEffort
import org.firstinspires.ftc.teamcode.robertMkII.wrappers.MkEncoder
import org.firstinspires.ftc.teamcode.robertMkII.wrappers.MkMotor

class Rotator(hardwareMap: HardwareMap) {
    val kp = 1.5

    private val motor = MkMotor(hardwareMap, "rotator", DcMotorSimple.Direction.FORWARD, DcMotor.ZeroPowerBehavior.BRAKE)
    private val encoder = MkEncoder(hardwareMap, "rotator", DcMotorSimple.Direction.FORWARD, 537.7)

    fun setEffort(power: Double) {
        motor.effort = power
    }

    fun getEffort() = motor.effort

    fun resetEncoder(ticks: Int = 0) {
        encoder.reset(ticks)
    }

    fun write() {
        motor.write()
    }

    fun runToPos(targetPos: Double, currentPos:Double) {
        val effort = controlEffort(targetPos, currentPos, kp)
        setEffort(effort)
    }

    fun getTicks() = encoder.ticks
    fun getRevs() = encoder.revs
    fun getVelocity() = encoder.tickV
}