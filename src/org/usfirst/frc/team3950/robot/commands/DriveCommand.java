package org.usfirst.frc.team3950.robot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.subsystems.USBCameraSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveCommand extends Command {
	private static RobotLogger logger = new RobotLogger(DriveCommand.class);
	
	Joystick stick = Robot.oi.driveStick;
	
    public DriveCommand() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.drivetrainSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//logger.log(RobotLogger.LoggerLevel.debug, "stick Y: " + stick.getY() + "stick twist: " + stick.getTwist());    	
    	Robot.drivetrainSubsystem.Drive(stick.getY(), stick.getTwist());
    	
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
