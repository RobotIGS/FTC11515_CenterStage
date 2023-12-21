package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Tools.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Tools.Chassis.MecanumChassis;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;
import org.firstinspires.ftc.teamcode.Tools.FieldNavigation;
import org.firstinspires.ftc.teamcode.Tools.Robot;

@Autonomous(name = "Red Simple Parking", group = "FTC RED")
public class SimpleRedParking extends BaseAutonomous {
    @Override
    public void initialize() {
        super.initialize();

        // move claw into position
        hwMap.claw_lifter.setPosition(hwMap.claw_lifter_min);
        hwMap.claw_servo1.setPosition(hwMap.claw_servo_max);
        hwMap.claw_servo2.setPosition(hwMap.claw_servo_min);
    }

    @Override
    public void run() {
        hwMap.claw_lifter.setPosition(hwMap.claw_lifter_max);
        sleep(1000);

        hwMap.robot.drive(new Position2D(300, isRed() ? 60 : (-60)));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }

        // stop the robot
        hwMap.robot.stop();
    }
}
