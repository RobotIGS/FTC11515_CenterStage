package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;

@Autonomous(name = "Red Simple Parking", group = "FTC RED")
public class SimpleParking extends GoOutOfTheWayAutonomous {
    @Override
    public void run() {
        super.run();

        sleep(1000);

        // rotate to face the board
        hwMap.robot.rotate(isRed() ? -90.0f : 90.0f, false);
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }

        // wait to make sure that the robot stands still
        sleep(1000);

        // drive forward to the board
        hwMap.robot.drive(new Position2D(70.0f, 0.0f));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }

    }
}
