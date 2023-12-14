package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Simple Parking", group = "FTC BLUE")
public class SimpleBlueParking extends SimpleRedParking {
    @Override
    public boolean isRed() {
        return false;
    }
}
