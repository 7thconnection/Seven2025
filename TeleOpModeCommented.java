package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Modo teleoperado", group="Linear OpMode")

public class Controle extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor Arm = null;
    private Servo Gancho = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Pronto.");
        telemetry.update();
        // Componentes
        leftDrive  = hardwareMap.get(DcMotor.class, "motor0");
        rightDrive = hardwareMap.get(DcMotor.class, "motor1");
        LauncherMotor0 = hardwareMap.get(DcMotor.class, "Arm0");
        LauncherMotor1 = hardwareMap.get(DcMotor.class, "Arm1");
        // Configuração dos motores
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setDirection(DcMotor.Direction.FORWARD);
        
        waitForStart();
        runtime.reset();
        // Variáveis
        double Force = 0.6;
        boolean PodeAdic = true;
      
        while (opModeIsActive()) {

            double StickYPower;
            double StickXPower;
            
            double EsqStick;
            
            float PowerTgLeft;

            double drive = -gamepad1.right_stick_y;
            double turn  =  gamepad1.right_stick_x;
            
            StickYPower = Range.clip(drive, -1.0, 1.0);
            StickXPower = Range.clip(turn, -1.0, 1.0);
            // Controle do movimento
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
            // Controle de aceleração
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
            if (gamepad1.b)
            {
                Arm.setPower(0.5);
            }
            else
            {
                Arm.setPower(0);
            }
            // Telemetria
            telemetry.addData("Force", Force);
            telemetry.addData("Rotações", Arm.getCurrentPosition() / 360);
            telemetry.update();
        }
    }
}
