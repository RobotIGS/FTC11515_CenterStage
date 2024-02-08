package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.Date;
import java.util.List;

@Autonomous(name = "Detection Extended Parking Red", group = "FTC RED")
public class Detection extends ExtendedParking {
    private TfodProcessor tfod;
    private VisionPortal visionPortal;

    @Override
    public void initialize() {
        super.initialize();

        tfod = new TfodProcessor.Builder().build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        tfod.setMinResultConfidence(0.90f);
    }

    private void runPlacePixel() {
        // TODO: place pixel next to prop / pixel
    }

    @Override
    public void run() {
        // replace GOT and continue with EP

        //driving
        hwMap.robot.drive(new Position2D(50, 0));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }

        // detection in front
        long start_time = new Date().getTime();
        while (opModeIsActive() && start_time + 2000 >= new Date().getTime() && zoneVal == RANDOMPOSITION.UNKNOWN) {
            List<Recognition> currentRecognitions = tfod.getRecognitions();
            if (!currentRecognitions.isEmpty())
                zoneVal = RANDOMPOSITION.MIDDLE;
            sleep(20);
        }

        // detection far pixel
        if (zoneVal == RANDOMPOSITION.UNKNOWN) {
            hwMap.robot.rotate(75);
            while (opModeIsActive() && hwMap.navi.getDriving()) {
                hwMap.robot.step();
            }

            start_time = new Date().getTime();
            while (opModeIsActive() && start_time + 2000 >= new Date().getTime() && zoneVal == RANDOMPOSITION.UNKNOWN) {
                List<Recognition> currentRecognitions = tfod.getRecognitions();
                if (!currentRecognitions.isEmpty())
                    zoneVal = isRed() ? RANDOMPOSITION.LEFT : RANDOMPOSITION.RIGHT;
                sleep(20);
            }

            if (zoneVal == RANDOMPOSITION.UNKNOWN) {
                zoneVal = isRed() ? RANDOMPOSITION.RIGHT : RANDOMPOSITION.LEFT;
            }
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

        // rotate back
        hwMap.robot.rotate(0, false);
        while (opModeIsActive() && hwMap.navi.getDriving()) {
            hwMap.robot.step();
        }
        sleep(200);

        //driving
        hwMap.robot.drive(new Position2D(62, 0), false);
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }

        // continue with EP
        super.runWithoutGot();
    }

    public void runFull() {
        run(); // TODO: normal run + placePixel()
    }
}
