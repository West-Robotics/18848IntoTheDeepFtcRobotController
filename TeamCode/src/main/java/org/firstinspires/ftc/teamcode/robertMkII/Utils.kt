package org.firstinspires.ftc.teamcode.robertMkII

fun controlEffort(targetPos: Double, currentPos: Double, kp: Double, feedforward: Double = 0.0) = kp*(targetPos-currentPos) + feedforward