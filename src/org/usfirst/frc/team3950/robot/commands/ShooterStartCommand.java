package org.usfirst.frc.team3950.robot.commands;

import java.util.ArrayList;

import org.opencv.core.Rect;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.VisionUtility;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterStartCommand extends Command {
	private static RobotLogger logger =new RobotLogger(ShooterStartCommand.class);

	double P = 0.0;
	double I = 0.0;
	double D = 0.0;
	double F = 0.0;
	
	public ShooterStartCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.axisCameraSubsystem);
		requires(Robot.shooterSubsystem);
		
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		P = Robot.robotConfig.shooterConfig.pidf.p;
		I = Robot.robotConfig.shooterConfig.pidf.i;
		D = Robot.robotConfig.shooterConfig.pidf.d;
		F = Robot.robotConfig.shooterConfig.pidf.f;
		Robot.shooterSubsystem.setF(0.0256); // 1023.0/counts); //.15
		Robot.shooterSubsystem.setP(0.128); // .15); //.03
		Robot.shooterSubsystem.setI(0); // .0003
		Robot.shooterSubsystem.setD(0.2); // .45); //
	}

    // Called repeatedly when this Command is scheduled to run
	protected void execute() {
		logger.log(RobotLogger.LoggerLevel.debug, "in execute pasta");
		Robot.shooterSubsystem.setTargetRPM(-2000);
/*		logger.log(RobotLogger.LoggerLevel.info, "I am in execute ;)");
		logger.log(RobotLogger.LoggerLevel.debug, "I am in execute ;)");
//      SmartDashboard.putNumber("Proportion", 0.2); //0.2
//      SmartDashboard.putNumber("Derivative", 1.0); //1.0
//      SmartDashboard.putNumber("Integral", 0.0); //0.0
//      SmartDashboard.putNumber("Feed Forward", 0.025); //0.025
		ArrayList<Rect> boilerRects = Robot.axisCameraSubsystem.getRectangles();
		logger.log(RobotLogger.LoggerLevel.debug, "BoilerRects.Size(): " + boilerRects.size());
		if (boilerRects.size() > 0) {
			double rectWidthAverage = VisionUtility.getRectWidthAvg(boilerRects);
			logger.log(RobotLogger.LoggerLevel.debug, "RectWidthAverage: " + rectWidthAverage);
			double boilerDistance = VisionUtility.getBoilerDistance(rectWidthAverage);
			logger.log(RobotLogger.LoggerLevel.debug, "Boiler Distance: " + boilerDistance);
			double targetRPM = VisionUtility.getBoilerRPM60(boilerDistance);
			logger.log(RobotLogger.LoggerLevel.debug, "Calculated RPM: " + targetRPM);
//			double counts = targetRPM * 4096.0 / 600.0;

			Robot.shooterSubsystem.setTargetRPM(-targetRPM);
		}
		else{
			logger.log(RobotLogger.LoggerLevel.warn, "Not equal to 2 boiler rectangles");
		} */
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
