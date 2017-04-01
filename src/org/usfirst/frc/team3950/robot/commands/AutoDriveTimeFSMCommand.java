package org.usfirst.frc.team3950.robot.commands;

import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    int stateStartEncoder = 0;
    
	boolean driveStraight1_useNavX = false;
	double driveStraight1_driveVoltage = -.75;
	long driveStraight1_forHowLong = 1900;
	int driveStraight1_encoderCount = 0;
	double rotate_twistVoltage = .7;
	double rotate_targetAngle = 30;
	double driveStraight2_driveVoltage = -.75;
	double driveStraight1_twistVoltage = 0;
	double driveStraight2_twistVoltage = 0;
	long driveStraight2_forHowLong = 1900;
	boolean driveStraight2_useNavX = false;
	int driveStraight2_encoderCount = 0;
	double reverse_driveVoltage = -.75;
	long reverse_forHowLong = 1900;
	boolean reverse_useNavX = false;
    
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
        stateStartEncoder = 0;
        //for encoder things
        Robot.drivetrainSubsystem.resetEncPosition();
        Robot.gearintakesubsystem.IntakePositionStart();
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
    	logger.log(RobotLogger.LoggerLevel.debug, "Current Angle: " + currentAngle + " State start ANGLE: " + stateStartAngle + "  target angle: " + targetAngle);

    	if(Math.abs(currentAngle - stateStartAngle) >= targetAngle) {
    		return getNextState(currentState);
    	}
    	
    	// here is where we write the action autonomous
    	// should perform in this state
    	//logger.log(RobotLogger.LoggerLevel.debug, "The current angle is: " + currentAngle);
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
        	RobotMap.ahrs.reset();
        	stateStartAngle = RobotMap.ahrs.getAngle();    	
    	}
    	
    	// here is where we right the logic to
    	// determine if we transition to the next state
    	if((System.currentTimeMillis() - stateStartTime) > forHowLong) {
    		return getNextState(currentState);
    	}

    	double twistVoltage = 0;
    	if(currentState == State.DriveStraight1) {
    		twistVoltage = driveStraight1_twistVoltage;
    	} else if(currentState == State.DriveStraight2) {
    		twistVoltage = driveStraight2_twistVoltage;
    	}
    	
    	if(useNavx) {
    		double currentAngle = RobotMap.ahrs.getAngle();
    		twistVoltage = -(currentAngle - stateStartAngle) / twistVoltageNormalizer;
    		logger.log(RobotLogger.LoggerLevel.debug, "Twist Voltage= " + twistVoltage + "  Angle= " + currentAngle + "State Start Angle= " + stateStartAngle); 
    	}
    	
    	
    	// here is where we write the action autonomous
    	// should perform in this state
		Robot.drivetrainSubsystem.Drive(driveVoltage, twistVoltage);
		
    	return currentState;
    }
    
    private State driveStraightEncoder(State currentState, Object... params) {
    	int encoderCount = (int)params[0];
    	double driveVoltage = (double)params[1];
    	boolean useNavx = (boolean)params[2];
    	
    	// log the first time autonomous has entered this state
    	if(currentState != prevState) {
        	logger.log(RobotLogger.LoggerLevel.debug, currentState.name() + ":  encoderCount=" + encoderCount + "  driveVoltage=" + driveVoltage + "  useNavx=" + useNavx);
        	stateStartEncoder = Robot.drivetrainSubsystem.getEncPosition();
        	prevState = currentState;
    	}
    	
    	// here is where we right the logic to
    	// determine if we transition to the next state
    	if((Robot.drivetrainSubsystem.getEncPosition() - stateStartEncoder) > encoderCount) {
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
	   		currentState = driveStraight(currentState, driveStraight1_forHowLong, driveStraight1_driveVoltage, driveStraight1_useNavX);
	   		break;
    		
    	case Rotate:
	    	currentState = rotate(currentState, rotate_targetAngle, rotate_twistVoltage);
	    	break;

    	case DriveStraight2:
			currentState = driveStraight(currentState, driveStraight2_forHowLong, driveStraight2_driveVoltage, driveStraight2_useNavX);
			break;

    	case Reverse:
			currentState = driveStraight(currentState, reverse_forHowLong, reverse_driveVoltage, reverse_useNavX);
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
