package org.usfirst.frc.team3950.robot.subsystems;

import org.usfirst.frc.team3950.robot.RobotMap;
import org.usfirst.frc.team3950.robot.commands.GearIntakeCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearIntakeSubsystem extends Subsystem {
	CANTalon intakeMotor = RobotMap.gearIntakeMotor;
	Solenoid solenoid1 = RobotMap.gearIntakeSolenoid1;
	Solenoid solenoid2 = RobotMap.gearIntakeSolenoid2;
	DigitalInput bumperSwitch = RobotMap.gearBumperSwitch;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new GearIntakeCommand());
    }
    
    public enum State{
    	up,
    	down
    }
    
    private State gearliftstate;
    
    public void GearIntake(double trigger){
    	intakeMotor.set(trigger);
    }
    
    public boolean GearBumperSwitchGet(){
    	return bumperSwitch.get();
    }
    
    public void GearIntakeLiftUp(){
    	solenoid1.set(true);
    	solenoid2.set(true);
    	gearliftstate = State.up;
    }
    
    public void GearIntakeLiftDown(){
    	solenoid1.set(false);
    	solenoid2.set(false);
    	gearliftstate = State.down;
    }
    
    public State GearLiftStateGet(){
    	return gearliftstate;
    }
}