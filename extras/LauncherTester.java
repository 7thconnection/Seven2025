package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="LauncherTester", group="Linear OpMode")

public class Controle extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor Arm = null;
    private double DefaultForce = 0.8;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Pronto.");
        telemetry.update();
        LauncherMotor0 = hardwareMap.get(DcMotor.class, "Arm");
      
        LauncherMotor0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LauncherMotor0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LauncherMotor0.setDirection(DcMotor.Direction.FORWARD);
        
        waitForStart();
        runtime.reset();
      
        while (opModeIsActive()) {
          if (gamepad1.a) {
            Arm.setForce(DefaultForce);
          } else {
            Arm.setForce(0);
          }
            telemetry.addData("Força padrão?", DefaultForce);
            telemetry.addData("Rotações", Arm.getCurrentPosition() / 360);
            telemetry.update();
        }
    }
}
