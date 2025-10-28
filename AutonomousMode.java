package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Robot: Auto Drive By Encoder", group="Robot")
public class Autonomo2 extends LinearOpMode {

    private DcMotor motor0 = null; 
    private DcMotor motor1 = null; 

    private static final double WHEEL_CIRCUMFERENCE = 29;
    private static final int TICKS_PER_ROTATION = 1440;
    private static final double DRIVE_SPEED = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {

        motor0 = hardwareMap.get(DcMotor.class, "motor0");
        motor1 = hardwareMap.get(DcMotor.class, "motor1");

        motor0.setDirection(DcMotor.Direction.FORWARD); 
        motor1.setDirection(DcMotor.Direction.REVERSE); 

        waitForStart();

        if (opModeIsActive()) {
            moveDistance(30.0, DRIVE_SPEED, DRIVE_SPEED); 
            drive(0.0, 0.0, 1000);
            moveDistance(-30.0, DRIVE_SPEED, DRIVE_SPEED); 
        }
    }

    public void moveDistance(double distanceInCm, double leftSpeed, double rightSpeed) throws InterruptedException {
        int targetTicks = calculateTicksForDistance(distanceInCm);

        motor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(100);

        motor0.setTargetPosition(targetTicks);
        motor1.setTargetPosition(targetTicks);

        motor0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor0.setPower(leftSpeed);
        motor1.setPower(rightSpeed);

        while (opModeIsActive() && (motor0.isBusy() && motor1.isBusy())) {
            telemetry.addData("Motor0 Ticks", motor0.getCurrentPosition());
            telemetry.addData("Motor1 Ticks", motor1.getCurrentPosition());
            telemetry.addData("Target", targetTicks);
            telemetry.update();
        }

        motor0.setPower(0.0);
        motor1.setPower(0.0);
    }
  
    public void drive(double powerLeft, double powerRight, long timeInMillis) throws InterruptedException {
        motor0.setPower(powerLeft);
        motor1.setPower(powerRight);
        sleep(timeInMillis);
        motor0.setPower(0.0);
        motor1.setPower(0.0);
    }

    public int calculateTicksForDistance(double distanceInCm) {
        double rotationsRequired = distanceInCm / WHEEL_CIRCUMFERENCE;
        return (int) (rotationsRequired * TICKS_PER_ROTATION);
    }
}
