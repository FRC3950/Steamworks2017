package org.usfirst.frc.team3950.robot.commands;

import org.opencv.core.Rect;
import org.usfirst.frc.team3950.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearDistanceCommand extends Command {

    public GearDistanceCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.usbCameraSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Rect totalRect = Robot.usbCameraSubsystem.getGearTotalRect(Robot.usbCameraSubsystem.testRectOne, Robot.usbCameraSubsystem.testRectTwo);
    	System.out.println("Total Rect: " + totalRect.x + " " + totalRect.y + " " + totalRect.width + " " + totalRect.height);
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
