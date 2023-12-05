package org.firstinspires.ftc.teamcode.OpModes.TeleOp.Examples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OpModes.TeleOp.BaseTeleOp;
import org.firstinspires.ftc.teamcode.Tools.Chassis.MecanumChassis;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;
import org.firstinspires.ftc.teamcode.Tools.FieldNavigation;
import org.firstinspires.ftc.teamcode.Tools.Chassis.ChassisBase;
import org.firstinspires.ftc.teamcode.Tools.Robot;

@TeleOp(name="Ex FullControl", group="Examples")
@Disabled
public class FullControl extends BaseTeleOp {
    private Robot robot;
    private FieldNavigation navi;
    private ChassisBase chassis;

    @Override
    public void initialize() {
        // generate field navigator
        navi = new FieldNavigation(new Position2D(0.0, 0.0));

        // generate chassis
        chassis = new MecanumChassis();
        chassis.setRotationAxis(1);
        chassis.setRotation(0.0f);
        chassis.populateMotorArray(hardwareMap);

        // generate robot interface object
        robot = new Robot(navi, chassis);
    }

    @Override
    public void run() {
        // set robot driving speed/direction
        robot.setSpeed(-gamepad1.left_stick_y*0.5, -gamepad1.left_stick_x*0.5, -gamepad1.right_stick_x*0.5);
        robot.step(); // actually apply the values to everything and make the robot drive

        // add chassis and navi debug infos
        telemetry.addLine(chassis.debug());
        telemetry.addLine(navi.debug());
        telemetry.update(); // display telemetry output on the driver hub/phone
    }
}
