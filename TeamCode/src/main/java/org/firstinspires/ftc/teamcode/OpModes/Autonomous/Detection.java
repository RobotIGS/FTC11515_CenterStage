package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Tools.DTypes.Position2D;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.Date;
import java.util.List;

@TeleOp(name = "DetectionExtendedParkingRed", group = "FTC RED")
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

        tfod.setMinResultConfidence(0.69f);
    }

    private void runPlacePixel() {
        // TODO: place pixel next to prop / pixel
    }

    @Override
    public void run() {
        // replace GOT and continue with EP

        //driving
        hwMap.robot.drive(new Position2D(30, 0));
        while (hwMap.navi.getDriving() && opModeIsActive()) {
            hwMap.robot.step();
        }

        // detection
        long start_time = new Date().getTime();
        while (opModeIsActive() && start_time + 2000 >= new Date().getTime()) {
            List<Recognition> currentRecognitions = tfod.getRecognitions();
            telemetry.addData("# Objects Detected", currentRecognitions.size());
            //TODO: Overwrite zoneVal

            // Step through the list of recognitions and display info for each one.
            for (Recognition recognition : currentRecognitions) {
                double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
                double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

                telemetry.addData(""," ");
                telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
                telemetry.addData("- Position", "%.0f / %.0f", x, y);
                telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
            }

            telemetry.update();
            sleep(20);
        }
        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

        // continue with EP
        super.runWithoutGot();
    }

    public void runFull() {
        run(); // TODO: normal run + placePixel()
    }
}
