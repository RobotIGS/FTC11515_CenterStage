package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Tools.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Tools.Chassis.MecanumChassis;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;
import org.firstinspires.ftc.teamcode.Tools.FieldNavigation;
import org.firstinspires.ftc.teamcode.Tools.Robot;

@TeleOp(name = "FullControl", group = "FTC")
public class FullControl extends BaseTeleOp {
    // robot
    protected Robot robot;
    protected FieldNavigation navi;
    protected Chassis chassis;

    // claw
    protected Servo claw_servo1;
    protected Servo claw_servo2;
    protected Servo claw_lifter;

    protected final double claw_servo_min = 0.0;
    protected final double claw_servo_max = 0.13;
    protected final double claw_lifter_min = 0.4;
    protected final double claw_lifter_max = 0.0;

    // lift
    protected DcMotor lift_motor;
    protected int lift_startposition;
    protected final int lift_maxposition = -7400;

    // shoot
    private Servo servo3;

    // driving speeds
    protected final double speed_full = 0.5;
    protected final double speed_sneak = 0.25;
    protected boolean drive_sneak = false;

    @Override
    public void initialize() {
        chassis = new MecanumChassis();
        chassis.setRotationAxis(1);
        chassis.setRotation(0.0f);
        chassis.populateMotorArray(hardwareMap);
        navi = new FieldNavigation(new Position2D(0.0, 0.0));

        robot = new Robot(navi, chassis);

        // claw
        claw_servo1 = hardwareMap.get(Servo.class, "claw1");
        claw_servo2 = hardwareMap.get(Servo.class, "claw2");
        claw_lifter = hardwareMap.get(Servo.class, "clawlifter");
        claw_lifter.setPosition(claw_lifter_min);
        claw_servo1.setPosition(claw_servo_max);
        claw_servo2.setPosition(claw_servo_min);

        // lift
        lift_motor = hardwareMap.get(DcMotor.class, "lift");
        lift_startposition = lift_motor.getCurrentPosition();
        lift_motor.setTargetPosition(lift_startposition);
        lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // shoot
        servo3 = hardwareMap.get(Servo.class, "shooter");

        // set start position claw
        claw_lifter.setPosition(claw_lifter_min);
        claw_servo1.setPosition(claw_servo_max);
        claw_servo2.setPosition(claw_servo_min);
    }

    @Override
    public void run() {

        /* CLAW */
        {
            // claw open
            if (gamepad2.left_trigger != 0) {
                claw_servo1.setPosition(claw_servo_min);
                claw_servo2.setPosition(claw_servo_max);
            }

            // claw close
            if (gamepad2.right_trigger != 0) {
                claw_servo1.setPosition(claw_servo_max);
                claw_servo2.setPosition(claw_servo_min);
            }

            // claw up
            if (gamepad2.left_stick_y < 0 && claw_lifter.getPosition() > (claw_lifter_min-claw_lifter_max)/2)
                claw_lifter.setPosition(claw_lifter_max);

            // claw down
            if (gamepad2.left_stick_y > 0 && claw_lifter.getPosition() <



                    (claw_lifter_min-claw_lifter_max)/2)
                claw_lifter.setPosition(claw_lifter_min);

        }

        /* LIFT
        let the lift hold its current position and give up
        control (set new position to hold) on stick movement.
         */
        if (gamepad2.right_stick_y != 0.0) {
            if (
                    (lift_motor.getCurrentPosition()-lift_startposition >= -442 && gamepad2.right_stick_y > 0) ||
                    (lift_motor.getCurrentPosition()-lift_startposition <= lift_maxposition && gamepad2.right_stick_y < 0)
            )
                lift_motor.setPower(0.0);

            else {
                if (lift_motor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER)
                    lift_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift_motor.setPower(gamepad2.right_stick_y);
            }
        }
        else if (lift_motor.getMode() == DcMotor.RunMode.RUN_USING_ENCODER) {
            lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift_motor.setTargetPosition(lift_motor.getCurrentPosition());
            lift_motor.setPower(0.3);
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
        robot.setSpeed(
                -gamepad1.left_stick_y * (drive_sneak ? speed_sneak : speed_full),
                -gamepad1.right_stick_x * (drive_sneak ? speed_sneak : speed_full),
                (gamepad1.left_trigger-gamepad1.right_trigger) * (drive_sneak ? speed_sneak : speed_full));

        /* UPDATE THE ROBOT */
        robot.step();

        /* OUTPUT TELEMETRY  */
        telemetry.addLine(chassis.debug());
        telemetry.addLine(navi.debug());
        telemetry.addLine();

        // claw
        telemetry.addData("SNEAK", drive_sneak);
        telemetry.addData("CLAW", claw_servo1.getPosition() == claw_servo_min ? "OPEN" : "CLOSED");
        telemetry.addData("CLAW", claw_lifter.getPosition() == claw_lifter_min ? "DOWN" : "UP");
        telemetry.addData("LIFT", lift_motor.getCurrentPosition()-lift_startposition);
        telemetry.update();
    }
}