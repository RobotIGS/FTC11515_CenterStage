package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;

@Autonomous(name = "Red Extended Parking", group = "FTC RED")
public class ExtendedParking extends SimpleParking {
    enum ZONEVALUE {
        UNKNOWN,
        LEFT,
        MIDDLE,
        RIGHT
    };

    protected ZONEVALUE zoneVal;

    @Override
    public void initialize() {
        super.initialize();

        zoneVal = ZONEVALUE.UNKNOWN;
    }

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
        switch (zoneVal){
            case LEFT:
                hwMap.robot.drive(new Position2D(0,20));
                break;
            case RIGHT:
                hwMap.robot.drive(new Position2D(0,-20));
                break;
            default:
                break;
        }
        while (hwMap.navi.getDriving() && opModeIsActive()){
            hwMap.robot.step();
        }

        // lift upwards
        hwMap.lift.setTargetPosition(5000);
        while (opModeIsActive() && hwMap.lift.isBusy()) {
            sleep(100);
        }

        // intake up
        hwMap.intake_lifter.setPosition(hwMap.intake_lifter_up);

        //drive exact to board
        hwMap.robot.drive(new Position2D(15, 0));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }
        sleep(100);

        // intake open
        hwMap.claw_servo1.setPosition(hwMap.claw_servo_open);
        hwMap.claw_servo2.setPosition(-hwMap.claw_servo_open);

        sleep(4000);

        // intake close
        hwMap.claw_servo1.setPosition(hwMap.claw_servo_closed);
        hwMap.claw_servo2.setPosition(-hwMap.claw_servo_closed);

        // drive back
        hwMap.robot.drive(new Position2D(-15.0, 0.0));
        while (opModeIsActive() && hwMap.navi.getDriving()) {
            hwMap.robot.step();
        }

        // intake down
        hwMap.intake_lifter.setPosition(hwMap.intake_lifter_down);

        // lift down
        hwMap.lift.setTargetPosition(0);
        while (opModeIsActive() && hwMap.lift.isBusy()) {
            sleep(100);
        }

        hwMap.robot.stop();
    }
}
