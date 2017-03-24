package org.usfirst.frc.team3950.robot.commands;

import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotLogger;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearIntakeCommand extends Command {
	private static RobotLogger logger = new RobotLogger(GearIntakeCommand.class);
	XboxController controller = Robot.oi.xboxController;
	double lastRightTriggerPosition;
	boolean gearIn = false;

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
    	//logger.log(RobotLogger.LoggerLevel.debug, "kRight: " + controller.getTriggerAxis(Hand.kRight) + "  kLeft: " + controller.getTriggerAxis(Hand.kLeft));
    	
    	if(!gearIn){
    		Robot.gearintakesubsystem.GearIntake(controller.getTriggerAxis(Hand.kRight) - controller.getTriggerAxis(Hand.kLeft));
        	gearIn = Robot.gearintakesubsystem.currentOverload();
    	} else if (gearIn) {
    		Robot.gearintakesubsystem.GearIntake(-controller.getTriggerAxis(Hand.kLeft));
    	}
    	
    	if (controller.getTriggerAxis(Hand.kLeft) >= .5){
    		gearIn = false;
    	}
    	
    	
    	//lastRightTriggerPosition = controller.getTriggerAxis(Hand.kRight);
    	
    	
		
/*    	if(Robot.gearintakesubsystem.GearBumperSwitchGet() != true){
//    		System.out.println("GearBumperSwitch is not true.");
    		Robot.gearintakesubsystem.GearIntake(controller.getTriggerAxis(Hand.kRight) - controller.getTriggerAxis(Hand.kLeft));
    	}
    	else if(Robot.gearintakesubsystem.GearBumperSwitchGet() == true){
//    		System.out.println("GearBumperSwitch is true.");
    		Robot.gearintakesubsystem.GearIntake(-(controller.getTriggerAxis(Hand.kLeft)));
    	}
    	else{
//    		System.out.println("GearBumperSwitch is neither true nor false.");
    	} */
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
