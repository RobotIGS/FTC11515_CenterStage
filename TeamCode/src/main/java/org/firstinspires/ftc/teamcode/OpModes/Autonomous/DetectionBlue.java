package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "DetectionExtendedParkingBlue", group = "FTC BLUE")
public class DetectionBlue extends Detection {
    @Override
    public boolean isRed() {
        return false;
    }
}
