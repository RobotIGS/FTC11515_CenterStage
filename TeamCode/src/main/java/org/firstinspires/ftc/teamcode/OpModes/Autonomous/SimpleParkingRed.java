package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;

@Autonomous(name = "Red Simple Parking", group = "FTC RED")
public class SimpleParkingRed extends GoOutOfTheWayAutonomous {
    @Override
    public void run() {
        // GOT
        super.run();

        // parking
        runWithoutGot();
    }

    protected void runWithoutGot() {
        // set rotation accuracy to make sure that the robot
        // doesn't collide with the bridge
        hwMap.navi.setRotation_accuracy(0.5f);
        hwMap.navi.setKeepRotation(true);

        // stop movement
        sleep(1000);

        // rotate to face the board
        hwMap.robot.rotate(-90.0f);
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }

        // stop keeping rotation
        hwMap.navi.setKeepRotation(false);

        // wait to make sure that the robot stands still
        sleep(1000);

        // drive forward to the board
        hwMap.robot.drive(new Position2D(180.0f, 0.0f));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }

        // stop the robot
        hwMap.robot.stop();
    }
}
