package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HwMap;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;

@TeleOp(name = "FullControl", group = "FTC")
public class FullControl extends BaseTeleOp {
    // driving speeds
    protected final double speed_full = 0.55;
    protected final double speed_sneak = 0.3;
    protected boolean drive_sneak = false;
    protected boolean stop_instantly = false;

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void run() {

        /* intake */
        {
            // claw open
            if (gamepad2.left_trigger != 0) {
                hwMap.claw_servo1.setPosition(hwMap.claw_servo1_open);
                hwMap.claw_servo2.setPosition(hwMap.claw_servo2_open);
            }

            // claw close
            if (gamepad2.right_trigger != 0) {
                hwMap.claw_servo1.setPosition(hwMap.claw_servo1_closed);
                hwMap.claw_servo2.setPosition(hwMap.claw_servo2_closed);
            }

            // claw up
            if (gamepad2.dpad_left) {
                hwMap.claw_servo1.setPosition(hwMap.claw_servo1_up);
                hwMap.claw_servo2.setPosition(hwMap.claw_servo2_up);
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
                    hwMap.claw_servo1.setPosition(hwMap.claw_servo1_closed);
                    hwMap.claw_servo2.setPosition(hwMap.claw_servo2_closed);
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

        /* PAPER SHOOTING */
        if (gamepad1.dpad_down){
            hwMap.paper_servo.setPosition(hwMap.paper_shooter_preparation);
        } else if (gamepad1.dpad_up) {
            hwMap.paper_servo.setPosition(hwMap.paper_shooter_use);
        }

        /* PULL_UP */
        /*
        if (gamepad1.dpad_left) {
            hwMap.pull_up_1.setPower(1);
            hwMap.pull_up_2.setPower(-1);
        } else if (gamepad1.dpad_right) {
            hwMap.pull_up_1.setPower(-1);
            hwMap.pull_up_2.setPower(1);
        } else {
            hwMap.pull_up_1.setPower(0);
            hwMap.pull_up_2.setPower(0);
        }
        */

        /* PIXEL SHOOTING */
        /*
        if (gamepad2.a){
            hwMap.pixel_shooter_servo.setPosition(hwMap.pixel_shooter_a);
        } else if (gamepad2.b) {
            hwMap.pixel_shooter_servo.setPosition(hwMap.pixel_shooter_b);
        }
         */

        /* PULL UP SERVO */
        /*
        if (gamepad1.a) {
            hwMap.pull_up_servo.setPosition(hwMap.pull_up_a);
        } else if (gamepad1.b) {
            hwMap.pull_up_servo.setPosition(hwMap.pull_up_b);
        }
        */

        /* DRIVING */
        if (gamepad1.left_bumper || gamepad1.right_bumper) {
            drive_sneak = !drive_sneak;
            while (gamepad1.left_bumper || gamepad1.right_bumper) {}
        }
        if (gamepad1.left_stick_y == 0 && gamepad1.right_stick_x == 0 && (gamepad1.left_trigger-gamepad1.right_trigger) == 0){
            if (!stop_instantly){
                hwMap.robot.drive(new Position2D(0, 0));
                stop_instantly = true;
            }
        } else {
            if (stop_instantly) stop_instantly = false;
            hwMap.robot.setSpeed(
                    -gamepad1.left_stick_y * (drive_sneak ? speed_sneak : speed_full),
                    -gamepad1.right_stick_x * (drive_sneak ? speed_sneak : speed_full),
                    (gamepad1.left_trigger-gamepad1.right_trigger) * (drive_sneak ? speed_sneak : speed_full));
        }

        /* UPDATE THE ROBOT */
        hwMap.robot.step();

        /* OUTPUT TELEMETRY  */
        telemetry.addLine(hwMap.chassis.debug());
        telemetry.addLine(hwMap.navi.debug());
        telemetry.addLine();

        // claw
        telemetry.addData("SNEAK", drive_sneak);
        telemetry.addData("CLAW", hwMap.claw_servo1.getPosition() == hwMap.claw_servo1_open ? "OPEN" : (hwMap.claw_servo1.getPosition() == hwMap.claw_servo1_up ? "UP" : "CLOSED"));
        telemetry.addData("CLAW", hwMap.intake_lifter.getPosition() == hwMap.intake_lifter_down ? "DOWN" : "UP");
        telemetry.addData("LIFT", hwMap.lift.getCurrentPosition());
        telemetry.update();
    }
}