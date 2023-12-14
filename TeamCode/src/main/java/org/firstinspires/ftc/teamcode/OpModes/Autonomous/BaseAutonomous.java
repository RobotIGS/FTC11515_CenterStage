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
        hwMap.initialize(hardwareMap);
    };

    /* this methode runs the code */
    public abstract void run();

    /* this internal methode is used to run initialize and run */
    public void runOpMode() {
        initialize();
        waitForStart();
        run();
    }
}
