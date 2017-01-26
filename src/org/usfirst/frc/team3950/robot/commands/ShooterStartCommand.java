package org.usfirst.frc.team3950.robot.commands;

import org.usfirst.frc.team3950.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterStartCommand extends Command {
	private float targetRPM = 100;
    public ShooterStartCommand() {
        // Use requires() here to declare subsystem dependencies    	
        requires(Robot.shooterSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("Shooter Start Command Initialized");
    	float counts = (targetRPM*4096*(1/600));
    	Robot.shooterSubsystem.setF(1023/counts);
    	Robot.shooterSubsystem.setP(.15);
    	Robot.shooterSubsystem.setD(.5);
       	Robot.shooterSubsystem.setTargetRPM(targetRPM);
       	System.out.println("Shooter Start Command Complete, targetRPM = " + targetRPM);
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
