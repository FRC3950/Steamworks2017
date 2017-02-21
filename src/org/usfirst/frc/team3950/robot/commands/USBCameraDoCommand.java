package org.usfirst.frc.team3950.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3950.robot.GearPipeline;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.VisionUtility;

/**
 *
 */
public class USBCameraDoCommand extends Command {
	private static RobotLogger logger = new RobotLogger(USBCameraDoCommand.class);

    public USBCameraDoCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.usbCameraSubsystem);
    	
    }

//  // Called just before this Command runs the first time
    protected void initialize() {
    }

    private static long deltaMilliseconds = 0;
    private static GearPipeline gtbr = new GearPipeline();
    private static long previousTime = System.currentTimeMillis();

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	logger.log(RobotLogger.LoggerLevel.debug, "USBCameraDoCommand.execute");
        long currentTime = System.currentTimeMillis();
    	
        if((currentTime - previousTime) > deltaMilliseconds)
        {
	    	Mat mat = Robot.usbCameraSubsystem.getNthFrame(Robot.robotConfig.shooterConfig.nthFrame);
			gtbr.process(mat);
			ArrayList<Rect> gearRects = new ArrayList<Rect>();
			for(MatOfPoint mop : gtbr.filterContoursOutput()) {
				Rect rect = Imgproc.boundingRect(mop);
				gearRects.add(rect);
				logger.log(RobotLogger.LoggerLevel.trace, rect.toString());
			}
			logger.log(RobotLogger.LoggerLevel.trace, "gearRects size: " + gearRects.size());
			Rect totalRect = VisionUtility.getRectContainer(gearRects, Robot.robotConfig.usbCameraSettings.width, Robot.robotConfig.usbCameraSettings.height);
			logger.log(RobotLogger.LoggerLevel.trace, "TotalRect is: " + totalRect);
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
