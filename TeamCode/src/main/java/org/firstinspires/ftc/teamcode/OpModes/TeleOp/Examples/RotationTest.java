package org.firstinspires.ftc.teamcode.OpModes.TeleOp.Examples;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.OpModes.TeleOp.BaseTeleOp;
import org.firstinspires.ftc.teamcode.Tools.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Tools.Chassis.MecanumChassis;
import org.firstinspires.ftc.teamcode.Tools.FieldNavigation;
import org.firstinspires.ftc.teamcode.Tools.Robot;

@TeleOp(name = "Test Rotation", group = "Examples")
public class RotationTest extends BaseTeleOp {
    // robot
    protected Robot robot;
    protected FieldNavigation navi;
    protected Chassis chassis;

    @Override
    public void initialize() {
        chassis = new MecanumChassis();
        chassis.setRotationAxis(1);
        chassis.populateMotorArray(hardwareMap);

        navi = new FieldNavigation();

        robot = new Robot(navi, chassis);
        navi.setKeepRotation(true);
    }

    @Override
    public void run() {
        if (gamepad1.left_bumper && !robot.navi.getDriving()) {
            robot.rotate(45);
        }
        if (gamepad1.right_bumper && !robot.navi.getDriving()) {
            robot.rotate(-45);
        }
        robot.step();

        telemetry.addLine(navi.debug());
        telemetry.update();
    }
}
