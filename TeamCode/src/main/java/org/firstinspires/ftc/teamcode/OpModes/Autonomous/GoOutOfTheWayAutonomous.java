package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;

@Autonomous(name = "GoOutOfTheWay", group = "FTC")
public class GoOutOfTheWayAutonomous extends BaseAutonomous {
    @Override
    public void initialize() {
        super.initialize();

        // move claw into position
        hwMap.intake_lifter.setPosition(hwMap.intake_lifter_min);
        hwMap.claw_servo1.setPosition(hwMap.claw_servo1_closed);
        hwMap.claw_servo2.setPosition(hwMap.claw_servo1_open);
    }

    @Override
    public void run() {
        hwMap.intake_lifter.setPosition(hwMap.intake_lifter_max);
        sleep(1000);

        hwMap.robot.drive(new Position2D(62, 0));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }

        // stop the robot
        hwMap.robot.stop();
    }
}
