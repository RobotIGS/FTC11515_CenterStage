package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;

@Autonomous(name = "Red Extended Parking Board", group = "FTC RED")
public class ExtendedRedParkingBoard extends BaseAutonomous {
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
        // set up claw
        hwMap.claw_servo1.setPosition(hwMap.claw_servo_min);
        hwMap.claw_servo2.setPosition(hwMap.claw_servo_max);
        sleep(1000);

        hwMap.claw_lifter.setPosition(hwMap.claw_lifter_max);
        sleep(1000);

        //drive forward
        hwMap.robot.drive(new Position2D(65, 0));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }
        sleep(1000);

        //drive to board
        hwMap.robot.drive(new Position2D(0,isRed() ? -100 : (100)));
        hwMap.lift_motor.setTargetPosition(-200);
        hwMap.lift_motor.setPower(10);
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }
        while (hwMap.lift_motor.isBusy() && opModeIsActive()) {
            hwMap.robot.step();
        }
        //correct if wrong position
        sleep(600);

        //drive near to board
        hwMap.robot.drive(new Position2D(0, isRed() ? -10 : (10)));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }
        sleep(500);

        //open claw
        hwMap.claw_servo1.setPosition(hwMap.claw_servo_min);
        hwMap.claw_servo2.setPosition(hwMap.claw_servo_max);
        sleep(500);

        //drive backwards to park
        hwMap.robot.drive(new Position2D(0, isRed() ? -10 : (10)));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }
        sleep(500);

        //close claw & lift to initial position
        hwMap.claw_servo1.setPosition(hwMap.claw_servo_max);
        hwMap.claw_servo2.setPosition(hwMap.claw_servo_min);
        hwMap.claw_lifter.setPosition(hwMap.claw_lifter_min);
        sleep(500);

        //lift to initial position
        hwMap.lift_motor.setTargetPosition(0); //back to normal position (what value is 0 ?)
        while (hwMap.lift_motor.isBusy() && opModeIsActive()) {
            hwMap.robot.step();
        }
        sleep(200);


    }

}
