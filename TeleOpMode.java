
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Controle Manual", group="Linear OpMode")

public class Controle extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor Arm = null;
    private Servo Gancho = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Inicializado :D");
        telemetry.update();

        leftDrive  = hardwareMap.get(DcMotor.class, "motor0");
        rightDrive = hardwareMap.get(DcMotor.class, "motor1");
        Arm = hardwareMap.get(DcMotor.class, "motor4");
        Gancho = hardwareMap.get(Servo.class, "servo1");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();
        
        double Force = 0.6;
        boolean PodeAdic = true;

        while (opModeIsActive()) {

            double StickYPower;
            double StickXPower;
            
            double EsqStick;
            
            float PowerTgLeft;

            double drive = -gamepad1.right_stick_y;
            double turn  =  gamepad1.right_stick_x;
            
            
            double arm  =  -gamepad1.left_stick_y;
            
        
            StickYPower = Range.clip(drive, -1.0, 1.0);
            StickXPower = Range.clip(turn, -1.0, 1.0);
            EsqStick = Range.clip(arm, -1.0, 1.0);
             
            if (gamepad1.right_stick_y != 0)
            {
            leftDrive.setPower(Force * (gamepad1.right_stick_y));
            rightDrive.setPower(Force * (gamepad1.right_stick_y));
            }
            else if (gamepad1.right_stick_x != 0)
            {
            rightDrive.setPower(-Force * (gamepad1.right_stick_x));
            leftDrive.setPower(Force * (gamepad1.right_stick_x));
            }
            else
            {
            rightDrive.setPower(0);
            leftDrive.setPower(0);
            }
            
            if (gamepad1.left_stick_y != 0)
            {
                Arm.setPower(EsqStick);
            }
            else
            {
                Arm.setPower(0);
            }
            
            if (gamepad1.right_bumper && Force < 0.75 && PodeAdic == true)
            {
                Force += 0.1;
                sleep(300);
                
            }
            else if (gamepad1.left_bumper && Force > 0.35 && PodeAdic == true)
            {
                Force -= 0.1;
                sleep(300);
            }
            telemetry.addData("Force", Force);
            telemetry.update();
        }
    }
}
