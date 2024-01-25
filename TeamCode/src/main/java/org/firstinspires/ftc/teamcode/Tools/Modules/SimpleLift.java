package org.firstinspires.ftc.teamcode.Tools.Modules;

import com.qualcomm.robotcore.hardware.DcMotor;

public class SimpleLift {
    private final DcMotor motor;
    private int start_position;
    private int end_position;
    
    private final int gravity_delta_position;
    private final boolean steps_negative_is_up;

    private double idle_power;

    public SimpleLift(DcMotor motor, int steps_length, boolean neg_is_up) {
        this.motor = motor;
        gravity_delta_position = -442;
        steps_negative_is_up = neg_is_up;
        idle_power = 0.3;

        // set current position as start position and calculate the end position
        setCurrentAsStartPosition();
        setDeltaStepsStartEnd(steps_length);

        // set start position as target and set power => hold start position
        motor.setTargetPosition(start_position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(0.3);
    }
    
    public void setCurrentAsStartPosition() {
        start_position = motor.getCurrentPosition();
    }

    public void setCurrentAsEndPosition() {
        end_position = motor.getCurrentPosition();
    }
    
    public void setDeltaStepsStartEnd(int delta) {
        end_position = start_position + (steps_negative_is_up ? -delta : delta);
    }
    
    public int getCurrentPosition() {
        return (steps_negative_is_up ? -1 : 1) * (motor.getCurrentPosition() - start_position);
    }

    public boolean isBusy() {
        return motor.isBusy();
    }

    public void setTargetPosition(int target) {
        // calculate target position for the motor while keeping the value between end and start value
        target = Math.max(0, Math.min(target, Math.abs(end_position-start_position)));
        if (steps_negative_is_up)
            target = -target;
        target += start_position;
        motor.setTargetPosition(target);
        if (motor.getMode() != DcMotor.RunMode.RUN_TO_POSITION)
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(idle_power);
    }

    public void setPower(double power) {
        if (power != 0.0) {
            if (
                    (getCurrentPosition() <= gravity_delta_position && power < 0) ||
                    (getCurrentPosition() >= Math.abs(end_position-start_position) && power > 0)
            )
                motor.setPower(0.0);

            else {
                if (motor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER)
                    motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor.setPower(steps_negative_is_up ? -power : power);
            }
        }
        else if (motor.getMode() == DcMotor.RunMode.RUN_USING_ENCODER) {
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if ((getCurrentPosition()) <= 0) {
                motor.setTargetPosition(start_position);
            }
            else if ((getCurrentPosition()) >= Math.abs(end_position-start_position)) {
                motor.setTargetPosition(end_position);
            }
            else {
                motor.setTargetPosition(motor.getCurrentPosition());
            }
            motor.setPower(idle_power);
        }
    }
}
