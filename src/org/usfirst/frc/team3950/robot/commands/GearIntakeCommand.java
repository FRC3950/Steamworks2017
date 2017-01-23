package org.usfirst.frc.team3950.robot.commands;

import org.usfirst.frc.team3950.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearIntakeCommand extends Command {
	
	XboxController controller = Robot.oi.xboxController;

    public GearIntakeCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gearintakesubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.gearintakesubsystem.GearBumperSwitchGet() == false){
    		Robot.gearintakesubsystem.GearIntake(controller.getTriggerAxis(Hand.kRight) - controller.getTriggerAxis(Hand.kLeft));
    	}
    	else {
    		Robot.gearintakesubsystem.GearIntake(-(controller.getTriggerAxis(Hand.kLeft)));
    	}
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
