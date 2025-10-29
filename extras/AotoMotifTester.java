// Código base
package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvCamera.AsyncCameraOpenListener;
import org.openftc.easyopencv.OpenCvWebcam;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.apriltag.AprilTagDetectionPipeline;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.ArrayList;

@Autonomous(name="Auto: Motif+Artefatos", group="Auto")
public class AutoMotifArtifactSequence extends LinearOpMode {

    OpenCvCamera camera;
    AprilTagDetectionPipeline tagPipeline;

    ColorSensor colorSensor;
    DcMotor leftDrive, rightDrive;

    final int TAG_ID_FACE1 = 21;
    final int TAG_ID_FACE2 = 22;
    final int TAG_ID_FACE3 = 23;

    static final double FX = 578.272;
    static final double FY = 578.272;
    static final double CX = 402.145;
    static final double CY = 221.506;
    static final double TAG_SIZE = 0.166;

    String motif = "GPP";
    int nextIndex = 0;

    @Override
    public void runOpMode() {

        int camViewId = hardwareMap.appContext.getResources()
            .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance()
            .createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), camViewId);
        tagPipeline = new AprilTagDetectionPipeline(TAG_SIZE, FX, FY, CX, CY);
        camera.setPipeline(tagPipeline);

        camera.openCameraDeviceAsync(new AsyncCameraOpenListener(){
            @Override public void onOpened(){ camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT); }
            @Override public void onError(int errorCode){ telemetry.addData("Camera error", errorCode); telemetry.update(); }
        });

        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        leftDrive  = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");

        telemetry.setMsTransmissionInterval(50);
        telemetry.addLine("Detectando AprilTag para MOTIF...");
        telemetry.update();

        while (!isStarted() && !isStopRequested()) {
            ArrayList<AprilTagDetection> detections = tagPipeline.getLatestDetections();
            if (detections.size() > 0) {
                for (AprilTagDetection tag : detections) {
                    if (tag.id == TAG_ID_FACE1) {
                        motif = "GPP";
                        telemetry.addLine("MOTIF = GPP");
                        break;
                    } else if (tag.id == TAG_ID_FACE2) {
                        motif = "PGP";
                        telemetry.addLine("MOTIF = PGP");
                        break;
                    } else if (tag.id == TAG_ID_FACE3) {
                        motif = "PPG";
                        telemetry.addLine("MOTIF = PPG");
                        break;
                    }
                }
            } else {
                telemetry.addLine("Nenhum AprilTag detectado ainda");
            }
            telemetry.addData("Motif atual", motif);
            telemetry.update();
            sleep(50);
        }

        telemetry.addData("Motif final", motif);
        telemetry.update();

        while (opModeIsActive() && nextIndex < motif.length()) {
            char expected = motif.charAt(nextIndex);
            telemetry.addData("Esperando artefato", expected);

            driveForward(0.3, 500);

            String detected = detectColor();
            telemetry.addData("Cor detectada", detected);

            if (detected.charAt(0) == expected) {
                telemetry.addLine("Cor correta!");
                nextIndex++;
            } else {
                telemetry.addLine("Cor ERRADA! Esperado: " + expected);
            }
            telemetry.update();
            sleep(200);
        }

        telemetry.addLine("Sequência MOTIF completada!");
        telemetry.update();

        driveForward(0.4, 1000);

        camera.stopStreaming();
    }

    private void driveForward(double power, int millis) {
        leftDrive.setPower(power);
        rightDrive.setPower(power);
        sleep(millis);
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }

    private String detectColor() {
        float red    = colorSensor.red();
        float green  = colorSensor.green();
        float blue   = colorSensor.blue();

        if (green > red && green > blue) {
            return "G";
        } else {
            return "P";
        }
    }
}
