package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class BaseTeleOp extends LinearOpMode {
    /* initialize the OpMode (TeleOp) */
    public abstract void initialize();

    /* this methode runs the code */
    public abstract void run();

    /* this internal methode is used to run initialize and run */
    public void runOpMode() {
        initialize();
        waitForStart();
        while (opModeIsActive())
            run();
    }
}
