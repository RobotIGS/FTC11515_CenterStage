package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Extended Blue Parking Board", group = "FTC BLUE")
public class ExtendedBlueParkingBoard extends ExtendedRedParkingBoard{
    @Override
    public boolean isRed() {
        return false;
    }
}
