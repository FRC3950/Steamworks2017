package org.usfirst.frc.team3950.robot.subsystems;

import org.usfirst.frc.team3950.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team3950.robot.commands.*;

/**
 *
 */
public class ClimberSubsystem extends Subsystem {
	
	CANTalon motor = RobotMap.climberMotor;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new ClimberStopCommand());
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void climberDo() {
    	motor.set(1);	
    }
    
    public void climberDont() {
    	motor.set(-1);
    }
    
    public void climberStop() {
    	motor.set(0);
    }
}

