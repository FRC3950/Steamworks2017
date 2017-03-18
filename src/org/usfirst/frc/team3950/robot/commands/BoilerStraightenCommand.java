package org.usfirst.frc.team3950.robot.commands;

import java.util.ArrayList;

import org.opencv.core.Rect;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.VisionUtility;
import org.usfirst.frc.team3950.robot.subsystems.USBCameraSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BoilerStraightenCommand extends Command {
	private static RobotLogger logger = new RobotLogger(BoilerStraightenCommand.class);

	public BoilerStraightenCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.axisCameraSubsystem);
		requires(Robot.drivetrainSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		ArrayList<Rect> boilerRects = Robot.axisCameraSubsystem.getRectangles();
		if (boilerRects.size() == 2) {
			double rectWidthAverage = VisionUtility.getRectWidthAvg(boilerRects);
			logger.log(RobotLogger.LoggerLevel.debug, "RectWidthAverage: " + rectWidthAverage);
			double boilerDistance = VisionUtility.getBoilerDistance(rectWidthAverage);
			logger.log(RobotLogger.LoggerLevel.debug, "Boiler Distance: " + boilerDistance);
			
			Rect rectTotal = VisionUtility.getRectContainer(boilerRects, Robot.robotConfig.axisCameraSettings.width, Robot.robotConfig.axisCameraSettings.height);
			double boilerAngle = 0;
			if (rectTotal.width != 0)
				logger.log(RobotLogger.LoggerLevel.info, "Boiler width not zero");
			logger.log(RobotLogger.LoggerLevel.info, "rectTotal centerX: " + (rectTotal.x + (rectTotal.width / 2)));
			logger.log(RobotLogger.LoggerLevel.info, "Distance: " + boilerDistance);
			boilerAngle = VisionUtility.getBoilerAngle((rectTotal.x + (rectTotal.width / 2)), rectTotal.width, Robot.robotConfig.axisCameraSettings.visionAngle, Robot.robotConfig.axisCameraSettings.width - 1, boilerDistance);
			logger.log(RobotLogger.LoggerLevel.debug, "Angle: " + boilerAngle);
			double initialAngle = Robot.drivetrainSubsystem.getCurrentAngle();
			double desiredAngle = initialAngle + boilerAngle;
			if ((initialAngle != desiredAngle) && (desiredAngle > 0)) {
				// movedrivetrain right
			} else if ((initialAngle != desiredAngle) && (desiredAngle < 0)) {
				// move drivetrain left
			}
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
