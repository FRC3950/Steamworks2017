package org.usfirst.frc.team3950.robot.commands;

import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDriveTimeFSMCommand extends Command {
	public enum State {
	    Start, DriveStraight1, Rotate, DriveStraight2, Reverse, End
	}	
	
	public State getNextState(State currentState) {
		State nextState = currentState;

		prevState = currentState;
		switch(currentState)
		{
		case Start:
			nextState = State.DriveStraight1;
			break;
		case DriveStraight1:
			nextState = State.Rotate;
			break;
		case Rotate:
			nextState = State.DriveStraight2;
			break;
		case DriveStraight2:
			nextState = State.Reverse;
			break;
		case Reverse:
			nextState = State.End;
			break;
		case End:
			nextState = State.End;
			break;
		}
		
		return nextState;
	}
	
	private static RobotLogger logger = new RobotLogger(AutoDriveTimeCommand.class);
    State currentState = State.Start;
    State prevState = State.Start;
    long stateStartTime = 0;
    double stateStartAngle = 0;
    boolean isFinished = false;
    double twistVoltageNormalizer = 90;
    
    public AutoDriveTimeFSMCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrainSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	currentState = State.Start;
    	prevState = State.Start;
    	stateStartTime = System.currentTimeMillis();
        stateStartAngle = 0;
        isFinished = false;
        twistVoltageNormalizer = 90;

        logger.log(RobotLogger.LoggerLevel.debug, "initialize - startTime is:" + stateStartTime);
    }

    private State rotate(State currentState, Object... params) {
    	double targetAngle = (double)params[0];
    	double twistVoltage = (double)params[1];
    	
    	// log the first time autonomous has entered this state
    	if(currentState != prevState) {
        	logger.log(RobotLogger.LoggerLevel.debug, currentState.name() + ":  targetAngle=" + targetAngle + "  twistVoltage=" + twistVoltage);
        	if(targetAngle == 0) {
        		return getNextState(currentState);
        	}
        	stateStartTime = System.currentTimeMillis();
        	RobotMap.ahrs.reset();
        	stateStartAngle = RobotMap.ahrs.getAngle();
        	prevState = currentState;
    	}
    	
    	// here is where we right the logic to
    	// determine if we transition to the next state
    	double currentAngle = RobotMap.ahrs.getAngle();
    	if((currentAngle - stateStartAngle) >= targetAngle) {
    		return getNextState(currentState);
    	}
    	
    	// here is where we write the action autonomous
    	// should perform in this state
		Robot.drivetrainSubsystem.Drive(0, twistVoltage);
		
    	return currentState;
    }

    private State driveStraight(State currentState, Object... params) {
    	long forHowLong = (long)params[0];
    	double driveVoltage = (double)params[1];
    	boolean useNavx = (boolean)params[2];
    	
    	// log the first time autonomous has entered this state
    	if(currentState != prevState) {
        	logger.log(RobotLogger.LoggerLevel.debug, currentState.name() + ":  forHowLong=" + forHowLong + "  driveVoltage=" + driveVoltage + "  useNavx=" + useNavx);
        	stateStartTime = System.currentTimeMillis();
        	prevState = currentState;
    	}
    	
    	// here is where we right the logic to
    	// determine if we transition to the next state
    	if((System.currentTimeMillis() - stateStartTime) > forHowLong) {
    		return getNextState(currentState);
    	}

    	double twistVoltage = 0;
    	if(useNavx) {
    		twistVoltage = RobotMap.ahrs.getAngle() / twistVoltageNormalizer;
    	}
    	
    	// here is where we write the action autonomous
    	// should perform in this state
		Robot.drivetrainSubsystem.Drive(driveVoltage, twistVoltage);
		
    	return currentState;
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(currentState)
    	{
    	case Start:
        	logger.log(RobotLogger.LoggerLevel.debug, currentState.name());
    		currentState = getNextState(currentState);
    		break;
    		
    	case DriveStraight1:
	   		{
	   			double driveVoltage = -.75;
	   			long forHowLong = 1900;
	   			currentState = driveStraight(currentState, forHowLong, driveVoltage, false);
	   		}
	   		break;
    		
    	case Rotate:
	    	{
	   			double twistVoltage = .03;
	    		double targetAngle = 30;
	    		currentState = rotate(currentState, targetAngle, twistVoltage);
	    	}
	    	break;

    	case DriveStraight2:
			{
				double driveVoltage = -.75;
				long forHowLong = 1900;
				currentState = driveStraight(currentState, forHowLong, driveVoltage, false);
			}
			break;

    	case Reverse:
			{
				double driveVoltage = .75;
				long forHowLong = 500;
				currentState = driveStraight(currentState, forHowLong, driveVoltage, false);
			}
	   		break;
    		
    	case End:
        	logger.log(RobotLogger.LoggerLevel.debug, currentState.name());
    		isFinished = true;
    		break;

    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
