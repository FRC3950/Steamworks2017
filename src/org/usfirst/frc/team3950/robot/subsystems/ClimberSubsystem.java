package org.usfirst.frc.team3950.robot.subsystems;

import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team3950.robot.commands.*;
import org.usfirst.frc.team3950.robot.subsystems.BallIntakeSubsystem.State;

/**
 *
 */
public class ClimberSubsystem extends Subsystem {
	private static RobotLogger logger = new RobotLogger(ClimberSubsystem.class);
	
	CANTalon motor = RobotMap.climberMotor;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new ClimberStopCommand());
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
	public enum State{
		on,
		stop,
		reverse
	}
	
	private State state = State.stop;
	
    public void climberDo() {
    	//logger.log(RobotLogger.LoggerLevel.debug, "climber forward");
    	motor.set(1);
    	state = State.on;
    }
    
    public void climberDont() {
    	//logger.log(RobotLogger.LoggerLevel.debug, "climber back");
    	motor.set(-1);
    	state = State.reverse;
    }
    
    public void climberStop() {
    	//logger.log(RobotLogger.LoggerLevel.debug, "climber stopped");
    	motor.set(0);
    	state = State.stop;
    }
    
    public State getState(){
    	logger.log(RobotLogger.LoggerLevel.info, "state of climber" + state);
    	return state;
    }
}

