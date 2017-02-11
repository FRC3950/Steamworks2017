package org.usfirst.frc.team3950.robot.commands;

import java.util.ArrayList;

import org.opencv.core.Rect;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.VisionUtility;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BoilerStraightenCommand extends Command {

	public BoilerStraightenCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.axisCameraSubsystem);
		requires(Robot.drivetrainsubsystem);
		requires(Robot.visionUtilitySubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		ArrayList<Rect> boilerRects = Robot.axisCameraSubsystem.getRectangles();
		System.out.println(boilerRects.size());
		if (boilerRects.size() >= 2) {
			Rect rectOne = boilerRects.get(0);
			Rect rectTwo = boilerRects.get(1);
			Rect rectTotal = VisionUtility.getBoilerTotalRect(rectOne, rectTwo);
			System.out.println("TotalRect: " + rectTotal.x + " " + rectTotal.y + " " + rectTotal.width + " " + rectTotal.height);
			 double distance = VisionUtility.getBoilerDistance(rectTotal.width);
			 double boilerAngle = 0;
			 if (rectTotal.width != 0)
				 System.out.println("Boiler width not zero");
			 	 System.out.println("rectTotal centerX: " + (rectTotal.x + (rectTotal.width/2)));
			 	 System.out.println("Distance: " + distance);
				 boilerAngle = VisionUtility.getBoilerAngle((rectTotal.x + (rectTotal.width/2)), rectTotal.width, 67, 639, distance);
			 System.out.println("Angle: " + boilerAngle);
			 double initialAngle = Robot.drivetrainsubsystem.getCurrentAngle();
			 double desiredAngle = initialAngle + boilerAngle;
			 if((initialAngle != desiredAngle) && (desiredAngle > 0)){ 
				 //movedrivetrain right 
			 } 
			 else if ((initialAngle != desiredAngle) && (desiredAngle < 0)){
				 //move drivetrain left
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
