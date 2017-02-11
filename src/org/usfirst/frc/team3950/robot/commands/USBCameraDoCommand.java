package org.usfirst.frc.team3950.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3950.robot.GearPipeline;
import org.usfirst.frc.team3950.robot.GearPipelinePublishVideo;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.subsystems.USBCameraSubsystem;

/**
 *
 */
public class USBCameraDoCommand extends Command {

    public USBCameraDoCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.usbCameraSubsystem);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Mat mat = Robot.usbCameraSubsystem.getFrame();
		GearPipelinePublishVideo gtbr = new GearPipelinePublishVideo();
		gtbr.process(mat);
		ArrayList<Rect> gearRects = new ArrayList<Rect>();
		for(MatOfPoint mop : gtbr.filterContoursOutput()) {
			Rect rect = Imgproc.boundingRect(mop);
			System.out.println(rect.toString());
			gearRects.add(rect);
		}
		System.out.println("gearRects size: " + gearRects.size());
		Rect totalRect = Robot.usbCameraSubsystem.getRectContainer(gearRects, 640, 360);
/*
    	System.out.println("Hello from the USB camera");
    	USBCameraSubsystem.startCamera();
    	System.out.println("initialized1 usb");
    	Robot.usbCameraSubsystem.startCamera();
    	System.out.println("initializing2 usb");
    	gearRects = Robot.usbCameraSubsystem.getGearRectangles();
    	// operate on gear rectangles
    	 */
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
