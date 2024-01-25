package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Simple Parking", group = "FTC BLUE")
public class SimpleParkingBlue extends SimpleParkingRed {
    @Override
    public boolean isRed() {
        return false;
    }
}
