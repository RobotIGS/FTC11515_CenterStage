package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;

@Autonomous(name = "GoOutOfTheWay", group = "FTC")
public class GoOutOfTheWayAutonomous extends BaseAutonomous {
    @Override
    public void run() {
        hwMap.intake_lifter.setPosition(hwMap.intake_lifter_up);
        sleep(1000);

        hwMap.robot.drive(new Position2D(62, 0));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }

        // stop the robot
        hwMap.robot.stop();
    }
}
