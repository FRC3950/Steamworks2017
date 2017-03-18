package org.usfirst.frc.team3950.robot.commands;

import java.util.ArrayList;

import org.opencv.core.Rect;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.VisionUtility;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BoilerRectanglesCommand extends Command {
	private static RobotLogger logger =new RobotLogger(BoilerRectanglesCommand.class);
	
    public BoilerRectanglesCommand() {
    	requires(Robot.axisCameraSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		logger.log(RobotLogger.LoggerLevel.debug, "I am in execute ;)");

		ArrayList<Rect> boilerRects = Robot.axisCameraSubsystem.getRectangles();
		logger.log(RobotLogger.LoggerLevel.debug, "BoilerRects.Size(): " + boilerRects.size());
		if (boilerRects.size() > 0) {
			double rectWidthAverage = VisionUtility.getRectWidthAvg(boilerRects);
			logger.log(RobotLogger.LoggerLevel.debug, "RectWidthAverage: " + rectWidthAverage);
			double boilerDistance = VisionUtility.getBoilerDistance(rectWidthAverage);
			logger.log(RobotLogger.LoggerLevel.debug, "Boiler Distance: " + boilerDistance);
			double targetRPM = VisionUtility.getBoilerRPM60(boilerDistance);
			logger.log(RobotLogger.LoggerLevel.debug, "Calculated RPM: " + targetRPM);


		}
		else{
			logger.log(RobotLogger.LoggerLevel.warn, "Not equal to 2 boiler rectangles");
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
