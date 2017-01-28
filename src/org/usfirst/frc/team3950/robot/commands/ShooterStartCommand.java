package org.usfirst.frc.team3950.robot.commands;

import org.usfirst.frc.team3950.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterStartCommand extends Command {
	public static float targetRPM = 2000;
    public ShooterStartCommand() {
        // Use requires() here to declare subsystem dependencies    	
        requires(Robot.shooterSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//System.out.println("Shooter Start Command Initialized");
    	double counts = targetRPM*4096.0/600.0;
    	System.out.println(.9*1023.0/counts);
    	Robot.shooterSubsystem.setF(.05); //1023.0/counts); //.15
    	Robot.shooterSubsystem.setP(.02); //.15); //.03
    	Robot.shooterSubsystem.setI(.0003); //.0003
    	Robot.shooterSubsystem.setD(.08); //.45); //0
       	Robot.shooterSubsystem.setTargetRPM(targetRPM);
       	//System.out.println("Shooter Start Command Complete, targetRPM = " + targetRPM);
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
