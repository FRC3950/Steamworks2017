package org.usfirst.frc.team3950.robot.commands;

import java.util.ArrayList;

import org.opencv.core.Rect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.VisionUtility;
import org.usfirst.frc.team3950.robot.subsystems.AxisCameraSubsystem;
import org.usfirst.frc.team3950.robot.subsystems.USBCameraSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterStartCommand extends Command {
	private static RobotLogger logger =new RobotLogger(ShooterStartCommand.class);

	int rectWidthAverage;
	double boilerDistance;
	double RPM;

	public static float targetRPM = 0;

	public ShooterStartCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.shooterSubsystem);
		requires(Robot.axisCameraSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double P = Robot.robotConfig.shooterConfig.pidf.p;
		double I = Robot.robotConfig.shooterConfig.pidf.i;
		double D = Robot.robotConfig.shooterConfig.pidf.d;
		double F = Robot.robotConfig.shooterConfig.pidf.f;

		AxisCameraSubsystem axisCameraSubsystem = Robot.axisCameraSubsystem;
		ArrayList<Rect> boilerRects = axisCameraSubsystem.getRectangles();
		if (boilerRects.size() >= 2) {
			Rect rectOne = boilerRects.get(0);
			Rect rectTwo = boilerRects.get(1);
			logger.log( RobotLogger.LoggerLevel.debug, "RectOne: " + rectOne.height + "  " + rectOne.width + "  " + rectOne.area());
			logger.log( RobotLogger.LoggerLevel.debug, "RectTwo: " + rectTwo.height + "  " + rectTwo.width + "  " + rectTwo.area());
			rectWidthAverage = (rectTwo.width + rectOne.width) / 2;
			logger.log(RobotLogger.LoggerLevel.debug, "RectWidthAverage: " + rectWidthAverage);
			boilerDistance = VisionUtility.getBoilerDistance(rectWidthAverage);
			System.out.println("Boiler Distance: " + boilerDistance);
			logger.log(RobotLogger.LoggerLevel.debug, "Boiler Distance: " + boilerDistance);
			RPM = VisionUtility.getBoilerRPM60(boilerDistance);
			logger.log(RobotLogger.LoggerLevel.debug, "Calculated RPM:" + RPM);
			// System.out.println("Shooter Start Command Initialized");
			double counts = targetRPM * 4096.0 / 600.0;
			System.out.println(.9 * 1023.0 / counts);
			Robot.shooterSubsystem.setF(F); // 1023.0/counts); //.15
			Robot.shooterSubsystem.setP(P); // .15); //.03
			Robot.shooterSubsystem.setI(I); // .0003
			Robot.shooterSubsystem.setD(D); // .45); //0
			Robot.shooterSubsystem.setTargetRPM(RPM);
			// System.out.println("Shooter Start Command Complete, targetRPM = "
			// + targetRPM);
		}
		else{
			logger.log(RobotLogger.LoggerLevel.debug, "Less than 2 boiler rectangles");
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
