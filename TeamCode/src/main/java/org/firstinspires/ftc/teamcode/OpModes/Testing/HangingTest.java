package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.OpModes.TeleOp.BaseTeleOp;

@TeleOp(name = "HangingTest", group = "TESTS")
public class HangingTest extends BaseTeleOp {
    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void run() {
        hwMap.pull_up_1.setPower(gamepad1.left_stick_y);
        hwMap.pull_up_2.setPower(gamepad1.right_stick_y);
    }
}
