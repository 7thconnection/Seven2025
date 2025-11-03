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
    private DcMotor Launcher = null;
    private Servo Alavanca = null;
    private double PosAberta = 0.74;
    private double PosFechada = 0.45;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Pronto.");
        telemetry.update();

        leftDrive  = hardwareMap.get(DcMotor.class, "motor0");
        rightDrive = hardwareMap.get(DcMotor.class, "motor1");
        Launcher = hardwareMap.get(DcMotor.class, "lancador");
        Alavanca = hardwareMap.get(Servo.class, "alavanca");
  
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        Launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Launcher.setDirection(DcMotor.Direction.FORWARD);
        
        waitForStart();
        runtime.reset();
        
        double Force = 1;
        boolean AlState = false;

        Alavanca.setPosition(PosAberta);
        
        while (opModeIsActive()) {
             
            if (gamepad1.right_stick_y != 0)
            {
            leftDrive.setPower(Force * (gamepad1.right_stick_y));
            rightDrive.setPower(Force * (gamepad1.right_stick_y));
            }
            else if (gamepad1.left_stick_x != 0)
            {
            rightDrive.setPower(Force * (gamepad1.left_stick_x));
            leftDrive.setPower(-Force * (gamepad1.left_stick_x));
            }
            else
            {
            rightDrive.setPower(0);
            leftDrive.setPower(0);
            }
            
            if (gamepad1.right_bumper && Force < 1)
            {
                Force += 0.1;
                sleep(300);
            }
            else if (gamepad1.left_bumper && Force > 0.35)
            {
                Force -= 0.1;
                sleep(300);
            }
            if (gamepad1.right_trigger != 0)
            {
                Launcher.setPower(1);
            }
            else
            {
                Launcher.setPower(0);
            }
            
            if (gamepad1.left_trigger != 0)
            {
                if (AlState == true)
                {
                    AlState = ! AlState;
                    Alavanca.setPosition(PosFechada);
                    sleep(300);
                }
                else
                {
                    AlState = ! AlState;
                    Alavanca.setPosition(PosAberta);
                    sleep(300);
                }
            }

            telemetry.addData("Força Base", Force);
            telemetry.addData("Força D/R Direita", rightDrive.getPower());
            telemetry.addData("Força D/R Esquerda", leftDrive.getPower());
            telemetry.addData("Estado da Alavanca", AlState == false ? "Aberta" : "Fechada");
            // telemetry.addData("Rotações", Launcher.getCurrentPosition() / 1444);
            telemetry.update();
        }
    }
}
