package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.HwMap;

public abstract class BaseTeleOp extends LinearOpMode {
    protected HwMap hwMap = new HwMap();

    /* initialize the OpMode (TeleOp) */
    public void initialize(){
        hwMap.initialize(hardwareMap);

        // set start position claw
        hwMap.intake_lifter.setPosition(hwMap.intake_lifter_down);
        hwMap.claw_servo1.setPosition(hwMap.claw_servo1_closed);
        hwMap.claw_servo2.setPosition(hwMap.claw_servo2_closed);
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
