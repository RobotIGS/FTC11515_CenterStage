package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;

@Autonomous(name = "Red Simple Parking Board short", group = "FTC RED")
public class SimpleRedParkingBoard extends BaseAutonomous {
    public void initialize() {
        super.initialize();

        // move claw into position
        hwMap.claw_lifter.setPosition(hwMap.claw_lifter_min);
        hwMap.claw_servo1.setPosition(hwMap.claw_servo_max);
        hwMap.claw_servo2.setPosition(hwMap.claw_servo_min);

        //rotation
        hwMap.navi.setKeepRotation(true);
    }


    public void run() {
        hwMap.claw_lifter.setPosition(hwMap.claw_lifter_max);
        sleep(1000);

        hwMap.robot.drive(new Position2D(55, 0));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }
        sleep(1000);

        hwMap.robot.rotate(isRed() ? -90 : 90);
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }

        sleep(1000);
        hwMap.robot.drive(new Position2D(isRed() ? -50 : 50,0));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }
        sleep(1000);
    }
}
