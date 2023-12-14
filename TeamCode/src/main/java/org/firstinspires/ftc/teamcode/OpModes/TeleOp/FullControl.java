package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.HwMap;
import org.firstinspires.ftc.teamcode.Tools.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Tools.Chassis.MecanumChassis;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;
import org.firstinspires.ftc.teamcode.Tools.FieldNavigation;
import org.firstinspires.ftc.teamcode.Tools.Robot;

@TeleOp(name = "FullControl", group = "FTC")
public class FullControl extends BaseTeleOp {
    protected HwMap hwMap = new HwMap();

    // shoot
    private Servo servo3;

    // driving speeds
    protected final double speed_full = 0.5;
    protected final double speed_sneak = 0.25;
    protected boolean drive_sneak = false;

    @Override
    public void initialize() {
        hwMap.initialize(hardwareMap);

        // shoot
        servo3 = hardwareMap.get(Servo.class, "shooter");

        // set start position claw
        hwMap.claw_lifter.setPosition(hwMap.claw_lifter_min);
        hwMap.claw_servo1.setPosition(hwMap.claw_servo_max);
        hwMap.claw_servo2.setPosition(hwMap.claw_servo_min);
    }

    @Override
    public void run() {

        /* CLAW */
        {
            // claw open
            if (gamepad2.left_trigger != 0) {
                hwMap.claw_servo1.setPosition(hwMap.claw_servo_min);
                hwMap.claw_servo2.setPosition(hwMap.claw_servo_max);
            }

            // claw close
            if (gamepad2.right_trigger != 0) {
                hwMap.claw_servo1.setPosition(hwMap.claw_servo_max);
                hwMap.claw_servo2.setPosition(hwMap.claw_servo_min);
            }

            // claw up
            if (gamepad2.left_stick_y < 0 && hwMap.claw_lifter.getPosition() > (hwMap.claw_lifter_min-hwMap.claw_lifter_max)/2)
                hwMap.claw_lifter.setPosition(hwMap.claw_lifter_max);

            // claw down
            if (gamepad2.left_stick_y > 0 && hwMap.claw_lifter.getPosition() <



                    (hwMap.claw_lifter_min-hwMap.claw_lifter_max)/2)
                hwMap.claw_lifter.setPosition(hwMap.claw_lifter_min);

        }

        /* LIFT
        let the lift hold its current position and give up
        control (set new position to hold) on stick movement.
         */
        if (gamepad2.right_stick_y != 0.0) {
            if (
                    (hwMap.lift_motor.getCurrentPosition()-hwMap.lift_start_position >= -442 && gamepad2.right_stick_y > 0) ||
                    (hwMap.lift_motor.getCurrentPosition()-hwMap.lift_start_position <= hwMap.lift_max_position && gamepad2.right_stick_y < 0)
            )
                hwMap.lift_motor.setPower(0.0);

            else {
                if (hwMap.lift_motor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER)
                    hwMap.lift_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                hwMap.lift_motor.setPower(gamepad2.right_stick_y);
            }
        }
        else if (hwMap.lift_motor.getMode() == DcMotor.RunMode.RUN_USING_ENCODER) {
            hwMap.lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if ((hwMap.lift_motor.getCurrentPosition() - hwMap.lift_start_position) > 0) {
                hwMap.lift_motor.setTargetPosition(hwMap.lift_start_position);
            }
            else {
                hwMap.lift_motor.setTargetPosition(hwMap.lift_motor.getCurrentPosition());
            }
            hwMap.lift_motor.setPower(0.3);
        }

        /* SHOOTING */
        if (gamepad1.dpad_down){
            servo3.setPosition(0);
        } else if (gamepad1.dpad_up) {
            servo3.setPosition(0.15);
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
        telemetry.addData("CLAW", hwMap.claw_servo1.getPosition() == hwMap.claw_servo_min ? "OPEN" : "CLOSED");
        telemetry.addData("CLAW", hwMap.claw_lifter.getPosition() == hwMap.claw_lifter_min ? "DOWN" : "UP");
        telemetry.addData("LIFT", hwMap.lift_motor.getCurrentPosition()-hwMap.lift_start_position);
        telemetry.update();
    }
}