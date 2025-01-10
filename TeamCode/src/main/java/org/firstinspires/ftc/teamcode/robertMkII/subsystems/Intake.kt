package org.firstinspires.ftc.teamcode.robertMkII.subsystems

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.robertMkII.wrappers.MkCRServo
import org.firstinspires.ftc.teamcode.robertMkII.wrappers.MkServo

class Intake(hardwareMap: HardwareMap) {
    private val wrist = MkServo(hardwareMap, "wrist", MkServo.ModelPWM.GOBILDA_SPEED)
    private val spinner = MkCRServo(hardwareMap, "spinner", MkCRServo.ModelPWM.CR_GOBILDA_SPEED, DcMotorSimple.Direction.FORWARD)

    private var _wristPos = HandPosition.DUMMY
    var wristPos: HandPosition
        get() = _wristPos
        set(value: HandPosition) = if (value.pos != wrist.getPosition()) {
            _wristPos = value
            wrist.position(value.pos)
        } else Unit

    enum class HandPosition(val pos: Double) {
        DUMMY(0.5),
        INTAKE(0.33),
        OUTTAKE(0.65)
    }

    fun setSpinSpeed(speed: Double) {
        spinner.effort = speed
    }

    fun writeSpinner() {
        spinner.write()
    }
}