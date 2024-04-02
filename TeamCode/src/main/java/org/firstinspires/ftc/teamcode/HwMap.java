package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Tools.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Tools.Chassis.MecanumChassis;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;
import org.firstinspires.ftc.teamcode.Tools.FieldNavigation;
import org.firstinspires.ftc.teamcode.Tools.Modules.SimpleLift;
import org.firstinspires.ftc.teamcode.Tools.Robot;

public class HwMap {
    // robot
    public Robot robot;
    public FieldNavigation navi;
    public Chassis chassis;

    // intake
    public Servo claw_servo1;
    public Servo claw_servo2;
    public Servo intake_lifter;
    public DcMotor intake_motor;

    // lift
    public SimpleLift lift;

    // paper shooter
    public Servo paper_servo;

    // pixel shooter
    public Servo pixel_shooter_servo;

    // pull up
    public DcMotor pull_up_1;
    public DcMotor pull_up_2;
    public Servo pull_up_servo;

    // limits
    public final double claw_servo1_open = 0.45; // 0.43
    public final double claw_servo1_closed = 0.3;
    public final double claw_servo1_up = 0.15; // 0.17
    public final double claw_servo2_closed = claw_servo1_closed;
    public final double claw_servo2_open = claw_servo2_closed + (claw_servo1_closed-claw_servo1_open);
    public final double claw_servo2_up = claw_servo2_closed + (claw_servo1_closed-claw_servo1_up);
    public final double intake_lifter_down = 0.0;
    public final double intake_lifter_up = 1.0;
    public final double paper_shooter_preparation = 0;
    public final double paper_shooter_use = 0.15;

    public final double pixel_shooter_a = 1.0;
    public final double pixel_shooter_b = 0.8;

    public final double pull_up_a = 0.0;
    public final double pull_up_b = 0.4;

    public void initialize(HardwareMap hardwareMap) {
        // get chassis
        chassis = new MecanumChassis();
        chassis.setRotationAxis(1);
        chassis.populateMotorArray(hardwareMap);
        chassis.setRotation(0.0f);

        // get field navigator
        navi = new FieldNavigation(new Position2D(0.0, 0.0));

        // get robot api object
        robot = new Robot(navi, chassis);

        // intake
        claw_servo1 = hardwareMap.get(Servo.class, "claw1");
        claw_servo2 = hardwareMap.get(Servo.class, "claw2");
        claw_servo1.setPosition(claw_servo1_closed);
        claw_servo2.setPosition(claw_servo2_closed);
        intake_motor = hardwareMap.get(DcMotor.class, "intake_motor");
        intake_lifter = hardwareMap.get(Servo.class, "intake_lifter");
        intake_lifter.setPosition(intake_lifter_down);

        // lift
        lift = new SimpleLift(hardwareMap.get(DcMotor.class, "lift"), 5200, true);
        lift.setIdlePower(0.7);

        // paper shooter
        paper_servo = hardwareMap.get(Servo.class, "shooter");

        // paper shooter
        pixel_shooter_servo = hardwareMap.get(Servo.class, "pixel_shooter");
        pixel_shooter_servo.setPosition(pixel_shooter_a);

        // pull up
        pull_up_1 = hardwareMap.get(DcMotor.class, "pull_up1");
        pull_up_2 = hardwareMap.get(DcMotor.class, "pull_up2");
        pull_up_servo = hardwareMap.get(Servo.class, "pull_up_servo");
        //pull_up_servo.setPosition(pull_up_a);
    }
}