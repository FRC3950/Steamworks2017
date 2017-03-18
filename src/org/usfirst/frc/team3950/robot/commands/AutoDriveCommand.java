package org.usfirst.frc.team3950.robot.commands;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3950.robot.GearPipeline;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.VisionUtility;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDriveCommand extends Command {
	private static RobotLogger logger = new RobotLogger(AutoDriveCommand.class);
	
	private double distanceCounts;
	private boolean finished = false;

    public AutoDriveCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//divide by twelve if needed in inches
    	//distanceCounts = distanceFeet * 2607.5;
    	requires(Robot.usbCameraSubsystem);
    	requires(Robot.drivetrainSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }
    
    private static GearPipeline gtbr = new GearPipeline();

    // Called repeatedly when this Command is scheduled to run 
    protected void execute() {
    	Mat mat = Robot.usbCameraSubsystem.getNthFrame(Robot.robotConfig.shooterConfig.nthFrame);
		gtbr.process(mat);
		ArrayList<Rect> gearRects = new ArrayList<Rect>();
		for(MatOfPoint mop : gtbr.filterContoursOutput()) {
			Rect rect = Imgproc.boundingRect(mop);
			gearRects.add(rect);
		}
		logger.log(RobotLogger.LoggerLevel.debug, "NUMBER OF RECTS " + gearRects.size());
    	Rect rect = VisionUtility.getRectContainer(gearRects, Robot.robotConfig.usbCameraSettings.width, Robot.robotConfig.usbCameraSettings.height);
    	logger.log(RobotLogger.LoggerLevel.debug, rect.toString());
//    	double distance = VisionUtility.getGearDistance(rect.width);
    	double distance = VisionUtility.getGearDistance(VisionUtility.getRectWidthAvg(gearRects));
    	logger.log(RobotLogger.LoggerLevel.debug, "Distance: " + distance);
    	
    	if (distance <= 3) {
    		finished = true;
    		
    	}
    	logger.log(RobotLogger.LoggerLevel.debug, "VALUE OF FINISHED " + finished);
    	logger.log(RobotLogger.LoggerLevel.debug, "DISTANCE " + distance);
    	
    	//Robot.stolenDrivetrainSubsystem.driveStraightNavX(.5);
    	Robot.drivetrainSubsystem.Drive(-.5, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrainSubsystem.Drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrainSubsystem.Drive(0, 0);
    }
}
