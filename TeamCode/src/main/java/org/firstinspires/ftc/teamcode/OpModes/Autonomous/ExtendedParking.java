package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;

@Autonomous(name = "Red Extended Parking", group = "FTC RED")
public class ExtendedParking extends SimpleParking {
    protected int zoneVal;

    @Override
    public void run() {
        super.run();
        runWithoutParking();
    }

    public void runWithoutGot() {
        super.runWithoutGot();
        runWithoutParking();
    }

    protected void runWithoutParking() {
        // scoring
        //TODO: use zoneVal (drive left / right to place the pixel in the right zone)

        // lift up
        hwMap.lift.setTargetPosition(1000);
        while (opModeIsActive() && hwMap.lift.isBusy()) {
            sleep(100);
        }

        // intake up
        hwMap.intake_lifter.setPosition(hwMap.intake_lifter_max);

        //drive exact to board
        hwMap.robot.drive(new Position2D(10, 0));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }
        sleep(100);

        // intake open
        hwMap.claw_servo1.setPosition(hwMap.claw_servo1_open);
        hwMap.claw_servo2.setPosition(-hwMap.claw_servo1_open);

        sleep(500);

        // intake close
        hwMap.claw_servo1.setPosition(hwMap.claw_servo1_closed);
        hwMap.claw_servo2.setPosition(-hwMap.claw_servo1_closed);

        // intake down
        hwMap.intake_lifter.setPosition(hwMap.intake_lifter_min);

        // lift down
        hwMap.lift.setTargetPosition(0);
        while (opModeIsActive() && hwMap.lift.isBusy()) {
            sleep(100);
        }

        hwMap.robot.stop();
    }
}
