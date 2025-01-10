package org.firstinspires.ftc.teamcode.robertMkII.wrappers

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import com.qualcomm.robotcore.hardware.HardwareMap
import kotlin.math.abs

class MkMotor(
    hardwareMap: HardwareMap,
    name: String,
    dir: Direction,
    zpb: ZeroPowerBehavior,
    val eps: Double = 0.005
) {
    private val motor = hardwareMap.dcMotor.get(name) as DcMotorEx
    private var _effort = 0.0;

    var effort
        get() = _effort
        set(value) = if (abs(value - _effort) > eps) {
            _effort = value
        } else Unit

    fun write() { motor.power = effort }

    init {
        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        motor.direction = dir
        motor.zeroPowerBehavior = zpb
    }
}