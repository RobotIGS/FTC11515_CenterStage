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

    // shooter
    public Servo shooter_servo;

    // limits
    public final double claw_servo_open = 0.13;
    public final double claw_servo_closed = 0.0;
    public final double intake_lifter_down = 0.0;
    public final double intake_lifter_up = 1.0;
    public final double shooter_preparation = 0;
    public final double shooter_use = 0.15;

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
        intake_motor = hardwareMap.get(DcMotor.class, "intake_motor");
        intake_lifter = hardwareMap.get(Servo.class, "intake_lifter");

        // lift
        lift = new SimpleLift(hardwareMap.get(DcMotor.class, "lift"), 5200, true);
        lift.setIdlePower(0.7);

        // shooter
        shooter_servo = hardwareMap.get(Servo.class, "shooter");
    }
}