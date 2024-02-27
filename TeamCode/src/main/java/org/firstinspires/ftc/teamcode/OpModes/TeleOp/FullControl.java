package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HwMap;

@TeleOp(name = "FullControl", group = "FTC")
public class FullControl extends BaseTeleOp {
    protected HwMap hwMap = new HwMap();

    // driving speeds
    protected final double speed_full = 0.5;
    protected final double speed_sneak = 0.25;
    protected boolean drive_sneak = false;

    @Override
    public void initialize() {
        hwMap.initialize(hardwareMap);

        // set start position claw
        hwMap.intake_lifter.setPosition(hwMap.intake_lifter_down);
        hwMap.claw_servo1.setPosition(hwMap.claw_servo_closed);
        hwMap.claw_servo2.setPosition(hwMap.claw_servo_open);
    }

    @Override
    public void run() {

        /* intake */
        {
            // claw open
            if (gamepad2.left_trigger != 0) {
                hwMap.claw_servo1.setPosition(hwMap.claw_servo_open);
                hwMap.claw_servo2.setPosition(hwMap.claw_servo_closed);
            }

            // claw close
            if (gamepad2.right_trigger != 0) {
                hwMap.claw_servo1.setPosition(hwMap.claw_servo_closed);
                hwMap.claw_servo2.setPosition(hwMap.claw_servo_open);
            }

            // intake up
            if (gamepad2.left_stick_y < 0)
                hwMap.intake_lifter.setPosition(hwMap.intake_lifter_up);

            // intake down
            if (gamepad2.left_stick_y > 0)
                hwMap.intake_lifter.setPosition(hwMap.intake_lifter_down);

            // intake
            if (gamepad2.dpad_up) {
                if (hwMap.intake_motor.getPower() != 0.0)
                    hwMap.intake_motor.setPower(0.0);
                else {
                    // make sure that the claw is closed when using the intake
                    hwMap.claw_servo1.setPosition(hwMap.claw_servo_closed);
                    hwMap.claw_servo2.setPosition(hwMap.claw_servo_open);
                    hwMap.intake_motor.setPower(1.0);
                }
                while (gamepad2.dpad_up) {}
            } else if (gamepad2.dpad_down) {
                if (hwMap.intake_motor.getPower() != 0.0)
                    hwMap.intake_motor.setPower(0.0);
                else
                    hwMap.intake_motor.setPower(-1.0);
                while (gamepad2.dpad_down) {}
            }
        }

        /* LIFT
        let the lift hold its current position and give up
        control (set new position to hold) on stick movement.
         */
        if (!gamepad2.a) {
            hwMap.lift.setPower(-gamepad2.right_stick_y);
            if (gamepad2.y) {
                hwMap.lift.setTargetPosition(500);
            }
        } else {
            hwMap.lift.setRawPower(-gamepad2.right_stick_y);
            if (gamepad2.x)
                hwMap.lift.setCurrentAsStartPosition();
        }

        /* SHOOTING */
        if (gamepad1.dpad_down){
            hwMap.shooter_servo.setPosition(hwMap.shooter_preparation);
        } else if (gamepad1.dpad_up) {
            hwMap.shooter_servo.setPosition(hwMap.shooter_use);
        }

        /* DRIVING */
        if (gamepad1.left_bumper || gamepad1.right_bumper) {
            drive_sneak = !drive_sneak;
            while (gamepad1.left_bumper || gamepad1.right_bumper) {}
        }
        hwMap.robot.setSpeed(
                -gamepad1.left_stick_y * (drive_sneak ? speed_sneak : speed_full),
                -gamepad1.right_stick_x * (drive_sneak ? speed_sneak : speed_full),
                (gamepad1.left_trigger-gamepad1.right_trigger) * (drive_sneak ? speed_sneak : speed_full));

        /* UPDATE THE ROBOT */
        hwMap.robot.step();

        /* OUTPUT TELEMETRY  */
        telemetry.addLine(hwMap.chassis.debug());
        telemetry.addLine(hwMap.navi.debug());
        telemetry.addLine();

        // claw
        telemetry.addData("SNEAK", drive_sneak);
        telemetry.addData("CLAW", hwMap.claw_servo1.getPosition() == hwMap.claw_servo_open ? "OPEN" : "CLOSED");
        telemetry.addData("CLAW", hwMap.intake_lifter.getPosition() == hwMap.intake_lifter_down ? "DOWN" : "UP");
        telemetry.addData("LIFT", hwMap.lift.getCurrentPosition());
        telemetry.update();
    }
}