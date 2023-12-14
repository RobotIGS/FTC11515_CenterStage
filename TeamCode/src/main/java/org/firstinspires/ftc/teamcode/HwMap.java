package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Tools.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Tools.Chassis.MecanumChassis;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;
import org.firstinspires.ftc.teamcode.Tools.FieldNavigation;
import org.firstinspires.ftc.teamcode.Tools.Robot;

public class HwMap {
    // robot
    public Robot robot;
    public FieldNavigation navi;
    public Chassis chassis;

    // claw
    public Servo claw_servo1;
    public Servo claw_servo2;
    public Servo claw_lifter;

    // lift
    public DcMotor lift_motor;

    // limits
    public final double claw_servo_min = 0.0;
    public final double claw_servo_max = 0.13;
    public final double claw_lifter_min = 0.4;
    public final double claw_lifter_max = 0.0;
    public int lift_start_position;
    public final int lift_max_position = -5200;

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

        // claw
        claw_servo1 = hardwareMap.get(Servo.class, "claw1");
        claw_servo2 = hardwareMap.get(Servo.class, "claw2");
        claw_lifter = hardwareMap.get(Servo.class, "claw_lifter");

        // lift
        lift_motor = hardwareMap.get(DcMotor.class, "lift");
        lift_start_position = lift_motor.getCurrentPosition();
        lift_motor.setTargetPosition(lift_start_position);
        lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}