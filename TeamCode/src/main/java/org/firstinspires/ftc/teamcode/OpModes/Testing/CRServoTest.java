package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "CR Servo")
@Disabled
public class CRServoTest extends LinearOpMode {
    public CRServo servo;

    @Override
    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.get(CRServo.class, "crservo");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a)
                servo.setPower(-1);
            if (gamepad1.b)
                servo.setPower(1);
        }
    }
}
