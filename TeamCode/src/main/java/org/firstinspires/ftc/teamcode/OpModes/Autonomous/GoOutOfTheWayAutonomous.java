package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;

@Autonomous(name = "GoOutOfTheWay", group = "FTC")
public class GoOutOfTheWayAutonomous extends BaseAutonomous {
    @Override
    public void run() {
        hwMap.robot.drive(new Position2D(58, 0), false);
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }
    }
}
