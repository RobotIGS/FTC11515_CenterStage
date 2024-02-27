package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HwMap;

public abstract class BaseAutonomous extends LinearOpMode {
    // hardware map
    protected HwMap hwMap;

    /* return the alliance color */
    public boolean isRed() {
        return true;
    }

    /* initialize the OpMode (Autonomous) */
    public void initialize() {
        hwMap = new HwMap();
        hwMap.initialize(hardwareMap);
        hwMap.navi.setKeepRotation(true);

        // claw
        hwMap.claw_servo1.setPosition(hwMap.claw_servo_closed);
        hwMap.claw_servo2.setPosition(hwMap.claw_servo_open);
        hwMap.lift.setTargetPosition(500);
    };

    /* this methode runs the code */
    public abstract void run();

    /* this internal methode is used to run initialize and run */
    public void runOpMode() {
        initialize();
        waitForStart();
        run();

        hwMap.lift.setTargetPosition(0);
    }
}
