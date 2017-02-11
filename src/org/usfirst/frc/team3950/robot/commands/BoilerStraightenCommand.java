package org.usfirst.frc.team3950.robot.commands;

import java.util.ArrayList;

import org.opencv.core.Rect;
import org.usfirst.frc.team3950.robot.Robot;

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
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	ArrayList<Rect> boilerRects = Robot.axisCameraSubsystem.getRectangles();
    	Rect rectOne = boilerRects.get(0);
    	Rect rectTwo = boilerRects.get(1);
    	Rect rectTotal = Robot.axisCameraSubsystem.getBoilerTotalRect(rectOne, rectTwo);
    	System.out.println("TotalRect: " + rectTotal.x + " " + rectTotal.y + " " + rectTotal.width + " " + rectTotal.height);
    	double distance = Robot.axisCameraSubsystem.getBoilerDistance(rectTotal.width);
    	double angle = 0;
    	if (rectTotal.width != 0)
    		angle = Robot.axisCameraSubsystem.getBoilerAngle((rectTotal.x + (rectTotal.width/2)), rectTotal.width, 67, 639, distance);
    	System.out.println("Angle: " + angle);
    	double initialAngle = Robot.drivetrainsubsystem.getCurrentAngle();
    	
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
