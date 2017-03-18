package org.usfirst.frc.team3950.robot.commands;

import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearShiftCommand extends Command {
	private static RobotLogger logger = new RobotLogger(GearShiftCommand.class);

    public GearShiftCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrainSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	logger.log(RobotLogger.LoggerLevel.debug, "Gear shift solenoid before: " + Robot.drivetrainSubsystem.gearShiftSolenoid.get());
    	Robot.drivetrainSubsystem.gearShift();   	
    	logger.log(RobotLogger.LoggerLevel.debug, "Gear shift solenoid after: " + Robot.drivetrainSubsystem.gearShiftSolenoid.get());
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
