package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Extended Parking", group = "FTC BLUE")
public class ExtendedParkingBlue extends ExtendedParking {
    @Override
    public boolean isRed() {return false;}
}
