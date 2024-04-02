package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.HwMap;

public abstract class BaseTeleOp extends LinearOpMode {
    protected HwMap hwMap = new HwMap();

    /* initialize the OpMode (TeleOp) */
    public void initialize(){
        hwMap.initialize(hardwareMap);
    }

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
