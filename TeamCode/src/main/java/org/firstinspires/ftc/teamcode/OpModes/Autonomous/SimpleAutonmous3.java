package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Tools.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Tools.Chassis.MecanumChassis;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;
import org.firstinspires.ftc.teamcode.Tools.FieldNavigation;
import org.firstinspires.ftc.teamcode.Tools.Robot;

public class SimpleAutonmous3 {

    public class SimpleAutonomous3 extends BaseAutonomous {
        //robot
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

        @Override
        public void initialize() {
            navi = new FieldNavigation(new Position2D(0.0, 0.0));

            chassis = new MecanumChassis();
            chassis.setRotationAxis(1);
            chassis.setRotation(0.0f);
            chassis.populateMotorArray(hardwareMap);

            robot = new Robot(navi, chassis);

            // claw
            claw_servo1 = hardwareMap.get(Servo.class, "claw1");
            claw_servo2 = hardwareMap.get(Servo.class, "claw2");
            claw_lifter = hardwareMap.get(Servo.class, "clawlifter");

            // move claw into position
            claw_lifter.setPosition(claw_lifter_min);
            claw_servo1.setPosition(claw_servo_max);
            claw_servo2.setPosition(claw_servo_min);

            //lift
            lift_startposition = lift_motor.getCurrentPosition();

        }

        @Override
        public void run() {
            // set up claw
            claw_servo1.setPosition(claw_servo_min);
            claw_servo2.setPosition(claw_servo_max);
            sleep(1000);

            claw_lifter.setPosition(claw_lifter_max);
            sleep(1000);

            //drive forward
            robot.drive(new Position2D(65, 0));
            while (navi.getDriving() && opModeIsActive()) {
                robot.step();
            }
            sleep(1000);

            //drive to board
            robot.drive(new Position2D(0,isRed() ? -100 : (100)));
            lift_motor.setTargetPosition(-200);
            lift_motor.setPower(10);
            while (navi.getDriving() && opModeIsActive()) {
                robot.step();
            }
            while (lift_motor.isBusy() && opModeIsActive()) {
                robot.step();
            }
            //correct if wrong position
            sleep(600);

            //drive near to board
            robot.drive(new Position2D(0, isRed() ? -10 : (10)));
            while (navi.getDriving() && opModeIsActive()) {
                robot.step();
            }
            sleep(500);

            //open claw
            claw_servo1.setPosition(claw_servo_min);
            claw_servo2.setPosition(claw_servo_max);
            sleep(500);

            //drive backwards to park
            robot.drive(new Position2D(0, isRed() ? -10 : (10)));
            while (navi.getDriving() && opModeIsActive()) {
                robot.step();
            }
            sleep(500);

            //close claw & lift to initial position
            claw_servo1.setPosition(claw_servo_max);
            claw_servo2.setPosition(claw_servo_min);
            claw_lifter.setPosition(claw_lifter_min);
            sleep(500);

            //lift to initial position
            lift_motor.setTargetPosition(0); //back to normal position (what value is 0 ?)
            while (lift_motor.isBusy() && opModeIsActive()) {
                robot.step();
            }
            sleep(200);


        }

    }
}
