package org.usfirst.frc.team3950.robot.subsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotMap;
import org.usfirst.frc.team3950.robot.commands.BallIntakeCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BallIntakeSubsystem extends Subsystem {
	private static RobotLogger logger = new RobotLogger(BallIntakeSubsystem.class);
	private CANTalon ballIntake;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	ballIntake = RobotMap.ballIntakeMotor;
    	ballIntake.set(0);
        // Set the default command for a subsystem here.
        //setDefaultmmand(new MySpecialCommand());
//    	setDefaultCommand(new BallIntakeCommand());
    }
    
	public enum State{
		intake,
		stop,
		outtake
	}
	
	private State state = State.stop;
	
    public void ballIntakeIn(){
    	ballIntake.set(1);
    	state = State.intake;
    	
    }
    
    public void ballIntakeOut(){
    	ballIntake.set(-1);
    	state = State.outtake;
    }
    
    public void ballIntakeStop(){
    	ballIntake.set(0);
    	state = State.stop;
    }
    
    public double getCurrentSetSpeed() {
    	logger.log(RobotLogger.LoggerLevel.info, "ball intake current speed" + ballIntake.get());
    	return ballIntake.get();
    }
    
    public State getState(){
    	logger.log(RobotLogger.LoggerLevel.info, "state of ball intake" + state);
    	return state;
    }
    
}

