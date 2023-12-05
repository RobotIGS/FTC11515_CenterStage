package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Simple Blue", group = "FTC Blue")
public class SimpleBlueAutonomous extends SimpleAutonomous {
    @Override
    public boolean isRed() {
        return false;
    }
}
