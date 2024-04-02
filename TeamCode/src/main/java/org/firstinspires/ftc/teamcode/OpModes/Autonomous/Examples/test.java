package org.firstinspires.ftc.teamcode.OpModes.Autonomous.Examples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.teamcode.OpModes.Autonomous.BaseAutonomous;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;

@Autonomous(name = "TEST", group = "FTC")
@Disabled
public class test extends BaseAutonomous {

    @Override
    public void run() {
        hwMap.robot.drive(new Position2D(85.0f, 0.0f));
        hwMap.robot.rotate(-90.0f);
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }
        hwMap.robot.drive(new Position2D(35.0f, 0.0f));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }
        

        hwMap.robot.stop();
    }
}
