package org.firstinspires.ftc.teamcode.OpModes.TeleOp.Examples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.OpModes.TeleOp.BaseTeleOp;
import org.firstinspires.ftc.teamcode.Tools.Chassis.Chassis;
import org.firstinspires.ftc.teamcode.Tools.Chassis.MecanumChassis;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;
import org.firstinspires.ftc.teamcode.Tools.FieldNavigation;
import org.firstinspires.ftc.teamcode.Tools.Robot;

@TeleOp(name = "Test Rotation", group = "Examples")
@Disabled
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

        navi = new FieldNavigation(new Position2D(0.0, 0.0));

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
        if (gamepad1.a && !robot.navi.getDriving()) {
            robot.rotate(180, false);
        }if (gamepad1.b && !robot.navi.getDriving()) {
            robot.rotate(-90, false);
        }if (gamepad1.y && !robot.navi.getDriving()) {
            robot.rotate(0, false);
        }if (gamepad1.x && !robot.navi.getDriving()) {
            robot.rotate(90, false);
        }
        robot.step();

        telemetry.addLine(navi.debug());
        telemetry.update();
    }
}
