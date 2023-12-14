package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Simple Parking Board short", group = "FTC BLUE")
public class SimpleBlueParkingBoard extends SimpleRedParkingBoard {
    @Override
    public boolean isRed() {
        return false;
    }
}
