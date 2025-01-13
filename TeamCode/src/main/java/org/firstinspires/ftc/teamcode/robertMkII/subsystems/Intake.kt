package org.firstinspires.ftc.teamcode.robertMkII.subsystems

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.robertMkII.wrappers.MkCRServo
import org.firstinspires.ftc.teamcode.robertMkII.wrappers.MkServo

@Config
object WristConstants {
    @JvmField var dummyPos = 0.0
}

class Intake(hardwareMap: HardwareMap) {
    private val wrist = MkServo(hardwareMap, "wrist", MkServo.ModelPWM.GOBILDA_SPEED)
    private val spinner = MkCRServo(hardwareMap, "spinner", MkCRServo.ModelPWM.CR_GOBILDA_SPEED, DcMotorSimple.Direction.FORWARD)

    private lateinit var _wristPos: HandPosition
    var wristPos: HandPosition
        get() = _wristPos
        set(value: HandPosition) = if (value.pos != wrist.getPosition()) {
            _wristPos = value
            wrist.position(value.pos)
        } else Unit


    enum class HandPosition(val pos: Double) {
        DUMMY(WristConstants.dummyPos),
        INTAKE(0.34),
        OUTTAKE(0.65)
    }

    fun setSpinSpeed(speed: Double) {
        spinner.effort = speed
    }

    fun writeSpinner() {
        spinner.write()
    }
}