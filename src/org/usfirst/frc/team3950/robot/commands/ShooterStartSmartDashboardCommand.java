package org.usfirst.frc.team3950.robot.commands;

import org.usfirst.frc.team3950.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterStartSmartDashboardCommand extends Command {
	public static float targetRPM = 2000;
	private double counts = targetRPM*4096.0/600.0;
    public ShooterStartSmartDashboardCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooterSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	counts = targetRPM*4096.0/600.0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*System.out.println("Shooter Start Smart Dashboard Command Initialized");
    	
    	System.out.println(.9*1023.0/counts);
    	targetRPM = (float) SmartDashboard.getNumber("Target RPM", 2000);
    	Robot.shooterSubsystem.setFSmartDashboard(); //1023.0/counts); //.15
    	Robot.shooterSubsystem.setPSmartDashboard(); //.15); //.03
    	Robot.shooterSubsystem.setISmartDashboard(); //.0003
    	Robot.shooterSubsystem.setDSmartDashboard(); //.45); //0
       	Robot.shooterSubsystem.setTargetRPM(targetRPM);
       	System.out.println("Shooter Start Smart Dashboard Command Complete, targetRPM = " + targetRPM);*/
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
