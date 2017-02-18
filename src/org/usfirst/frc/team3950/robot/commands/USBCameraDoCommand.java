package org.usfirst.frc.team3950.robot.commands;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3950.robot.GearPipeline;
import org.usfirst.frc.team3950.robot.GearPipelinePublishVideo;
import org.usfirst.frc.team3950.robot.GearPipelineRGB;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.VisionUtility;
import org.usfirst.frc.team3950.robot.subsystems.USBCameraSubsystem;

/**
 *
 */
public class USBCameraDoCommand extends Command {

    public USBCameraDoCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.usbCameraSubsystem);
    	
    }

  	private static CvSource cvSource;
//  // Called just before this Command runs the first time
    protected void initialize() {
    	
//    	cvSource = new CvSource("USBContours",  VideoMode.PixelFormat.kMJPEG, 640, 360, 30);
//    	CvSource cvSource = new CvSource("USBContours", VideoMode.PixelFormat.kMJPEG, 320, 240, 30);
//    	CameraServer.getInstance().addCamera(cvSource);
//    	VideoSink server = CameraServer.getInstance().addServer("serve_" + cvSource.getName());
//    	server.setSource(cvSource);
    }

    private static long deltaMilliseconds = 0;
    private static GearPipeline gtbr = new GearPipeline();
    private static long previousTime = System.currentTimeMillis();

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.robotLogger.debug("USBCameraDoCommand.execute");
        long currentTime = System.currentTimeMillis();
    	
        if((currentTime - previousTime) > deltaMilliseconds)
        {
	    	Mat mat = Robot.usbCameraSubsystem.getNthFrame(5);
			gtbr.process(mat);
			ArrayList<Rect> gearRects = new ArrayList<Rect>();
			for(MatOfPoint mop : gtbr.filterContoursOutput()) {
				Rect rect = Imgproc.boundingRect(mop);
				gearRects.add(rect);
				Robot.robotLogger.trace(rect.toString());
			}
			Robot.robotLogger.debug("gearRects size: " + gearRects.size());
			Rect totalRect = VisionUtility.getRectContainer(gearRects, 640, 360);
			previousTime = currentTime;
        }
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
