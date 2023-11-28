package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Tools.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Tools.Chassis.MecanumChassis;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;
import org.firstinspires.ftc.teamcode.Tools.FieldNavigation;
import org.firstinspires.ftc.teamcode.Tools.Robot;

@Autonomous(name = "Simple long parking", group = "FTC")
public class SimpleAutonomous2 extends BaseAutonomous {
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


    @Override
    public void initialize() {
        chassis = new MecanumChassis();
        chassis.setRotationAxis(1);
        chassis.populateMotorArray(hardwareMap);
        navi = new FieldNavigation(new Position2D(0.0, 0.0), 0.0);

        robot = new Robot(navi, chassis);

        // claw
        claw_servo1 = hardwareMap.get(Servo.class, "claw1");
        claw_servo2 = hardwareMap.get(Servo.class, "claw2");
        claw_lifter = hardwareMap.get(Servo.class, "clawlifter");

        // move claw into position
        claw_lifter.setPosition(claw_lifter_min);
        claw_servo1.setPosition(claw_servo_max);
        claw_servo2.setPosition(claw_servo_min);

    }

    @Override
    public void run() {
        claw_lifter.setPosition(claw_lifter_max);
        sleep(1000);

        robot.drive(new Position2D(75, 0));
        while (navi.getDriving() && opModeIsActive()) {
            robot.step();
        }
        sleep(1000);

        robot.drive(new Position2D(0,120)); //(-120?)
        while (navi.getDriving() && opModeIsActive()) {
            robot.step();
        }
        sleep(1000);

        robot.drive(new Position2D(2400,0));
        while (navi.getDriving() && opModeIsActive()) {
            robot.step();
        }
        sleep(1000);

        robot.drive(new Position2D(0,90)); //(-90) kann mit den vorherigen
        while (navi.getDriving() && opModeIsActive()) {
            robot.step();
        }
        sleep(1000);

    }
}
