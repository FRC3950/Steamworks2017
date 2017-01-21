package org.usfirst.frc.team3950.robot.subsystems;

import org.usfirst.frc.team3950.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BallIntakeSubsystem extends Subsystem {

	private CANTalon ballIntake;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	ballIntake = RobotMap.ballIntakeMotor;
    	ballIntake.set(0);
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
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
    	return ballIntake.get();
    }
    
    public State getState(){
    	return state;
    }
    
}

