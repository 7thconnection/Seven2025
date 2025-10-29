package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Teste", group = "Linear OpMode")
public class Teste extends LinearOpMode {

    ColorSensor sensorColor;
    DistanceSensor sensorDistance;
    int color;

    @Override
    public void runOpMode() {

        sensorColor = hardwareMap.get(ColorSensor.class, "corsens");

        waitForStart();

        while (opModeIsActive()) {
            if (sensorColor.red() > sensorColor.green() && sensorColor.red() > sensorColor.blue())
            {
                telemetry.addData("Cor", "Vermelha");
            }
            else if (sensorColor.green() > sensorColor.red() && sensorColor.green() > sensorColor.blue())
            {
                telemetry.addData("Cor", "Verde");
            }
            else if (sensorColor.blue() > sensorColor.red() && sensorColor.blue() > sensorColor.green())
            {
                telemetry.addData("Cor", "Azul");
            }

            telemetry.addData("Vermelho", sensorColor.red());
            telemetry.addData("Verde", sensorColor.green());
            telemetry.addData("Azul", sensorColor.blue());
            telemetry.addData("Luz do ambiente", sensorColor.alpha())
            telemetry.addData("Distancia", sensorDistance.getDistance(DistanceUnit.CM));
            telemetry.update();
        }
    }
}
