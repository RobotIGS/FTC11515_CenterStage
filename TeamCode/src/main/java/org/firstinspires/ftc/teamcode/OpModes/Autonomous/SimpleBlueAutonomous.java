package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Simple Red", group = "FTC Red")
public class SimpleBlueAutonomous extends SimpleAutonomous {
    @Override
    public boolean isRed() {
        return false;
    }
}
