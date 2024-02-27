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

        tfod.setMinResultConfidence(0.80f);
    }

    private void placePixel() {
        // TODO: place pixel next to prop / pixel
    }

    @Override
    public void run() {
        //driving
        hwMap.robot.drive(new Position2D(25, 0));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }

        sleep(1000);

        // detection in front
        long start_time = new Date().getTime();
        while (opModeIsActive() && start_time + 2000 >= new Date().getTime() && zoneVal == ZONEVALUE.UNKNOWN) {
            List<Recognition> currentRecognitions = tfod.getRecognitions();
            if (!currentRecognitions.isEmpty())
                zoneVal = ZONEVALUE.MIDDLE;
            hwMap.robot.step();
        }

        // detection far pixel
        if (zoneVal == ZONEVALUE.UNKNOWN) {
            hwMap.robot.drive(new Position2D(-10, 0));
            while (opModeIsActive() && hwMap.navi.getDriving()) {
                hwMap.robot.step();
            }
            sleep(1000);
            hwMap.robot.rotate(isRed()?28:-28, false);
            while (opModeIsActive() && hwMap.navi.getDriving()) {
                hwMap.robot.step();
            }

            hwMap.robot.drive(new Position2D(10, 0));
            start_time = new Date().getTime();
            while (opModeIsActive() && ((start_time + 2000 >= new Date().getTime() && zoneVal == ZONEVALUE.UNKNOWN) || hwMap.navi.getDriving())) {
                List<Recognition> currentRecognitions = tfod.getRecognitions();
                if (!currentRecognitions.isEmpty())
                    zoneVal = isRed() ? ZONEVALUE.LEFT : ZONEVALUE.RIGHT;
                hwMap.robot.step();
            }
            hwMap.robot.drive(new Position2D(-10, 0));
            while (opModeIsActive() && hwMap.navi.getDriving()) {
                hwMap.robot.step();
            }

            if (zoneVal == ZONEVALUE.UNKNOWN) {
                zoneVal = isRed() ? ZONEVALUE.RIGHT : ZONEVALUE.LEFT;
            }
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

        // rotate back
        hwMap.robot.rotate(0, false);
        while (opModeIsActive() && hwMap.navi.getDriving()) {
            hwMap.robot.step();
        }

        telemetry.addData("detected zone", zoneVal);
        telemetry.update();

        sleep(1000);

        // continue with EP
        super.run();
    }

    public void runFull() {
        run(); // TODO: normal run + placePixel()
    }
}
