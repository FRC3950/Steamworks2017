package org.usfirst.frc.team3950.robot.commands;

import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.subsystems.AgitatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AgitatorCommand extends Command {
	private static RobotLogger logger =new RobotLogger(AgitatorCommand.class);
	private boolean isExecuted = false;
	
    public AgitatorCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.agitatorSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isExecuted = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!isExecuted) {
    		logger.log(RobotLogger.LoggerLevel.debug, "Setting agitator motor voltage to: " +  Robot.robotConfig.shooterConfig.agitatorVoltage);
    		Robot.agitatorSubsystem.motorVoltage(Robot.robotConfig.shooterConfig.agitatorVoltage);
    		isExecuted = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.log(RobotLogger.LoggerLevel.debug, "Agitator end");
    	isExecuted = false;  	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.log(RobotLogger.LoggerLevel.debug, "Agitator interrupted");
    	isExecuted = false;
    }
}
