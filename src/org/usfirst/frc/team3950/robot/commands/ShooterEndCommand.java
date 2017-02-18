package org.usfirst.frc.team3950.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.subsystems.USBCameraSubsystem;

/**
 *
 */
public class ShooterEndCommand extends Command {
	private static RobotLogger logger = new RobotLogger(USBCameraSubsystem.class);

    public ShooterEndCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooterSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.log( RobotLogger.LoggerLevel.info, "ShooterEndCommand Initialized");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	Robot.shooterSubsystem.shutOffMotor();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.log(RobotLogger.LoggerLevel.info, "ShooterEndCommand Finshed");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
