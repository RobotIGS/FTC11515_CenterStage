package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Simple Red", group = "FTC Red")
public class SimpleRedAutonomous extends SimpleBlueAutonomous {
    @Override
    public boolean isBlue() {
        return false;
    }
}
