package org.usfirst.frc.team3950.robot.commands;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.usfirst.frc.team3950.robot.PipelineClass;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoGearCommand extends Command {
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;

    public AutoGearCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//        AxisCamera camera = CameraServer.getInstance().addAxisCamera("10.39.50.11");
        camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
        CvSink cvSink = CameraServer.getInstance().getVideo();
        Mat source = new Mat();
        cvSink.grabFrame(source);
        PipelineClass pc = new PipelineClass();
        pc.process(source);
        ArrayList<MatOfPoint> mop = pc.filterContoursOutput();
        for (MatOfPoint m : mop) {
        	System.out.println(m);
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
