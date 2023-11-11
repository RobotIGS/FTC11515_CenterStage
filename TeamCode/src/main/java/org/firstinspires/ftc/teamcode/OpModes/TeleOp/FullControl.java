package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Tools.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Tools.Chassis.MecanumChassis;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;
import org.firstinspires.ftc.teamcode.Tools.FieldNavigation;
import org.firstinspires.ftc.teamcode.Tools.Robot;

@TeleOp(name = "FTC FullControl", group = "FTC")
public class FullControl extends BaseTeleOp {
    protected Robot robot;
    protected FieldNavigation navi;
    protected Chassis chassis;

    @Override
    public void initialize() {
        chassis = new MecanumChassis();
        navi = new FieldNavigation(new Position2D(0.0, 0.0), 0.0);

        robot = new Robot(navi, chassis);
    }

    @Override
    public void run() {
        robot.setSpeed(
                -gamepad1.left_stick_y * 0.5,
                -gamepad1.left_stick_x * 0.5,
                (gamepad1.left_trigger-gamepad1.right_trigger) * 0.4);

        telemetry.addLine(navi.debug());
        telemetry.update();
        robot.step();
    }
}