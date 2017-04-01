package org.usfirst.frc.team3950.robot.subsystems;

import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.RobotMap;
import org.usfirst.frc.team3950.robot.commands.GearIntakeCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearIntakeSubsystem extends Subsystem {
	private static RobotLogger logger =new RobotLogger(GearIntakeSubsystem.class);
	CANTalon intakeMotor = RobotMap.gearIntakeMotor;
	Solenoid singleSolenoid1 = RobotMap.gearIntakeSolenoid1;
	Solenoid singleSolenoid2 = RobotMap.gearIntakeSolenoid2;
	DoubleSolenoid doubleSolenoid1 = RobotMap.gearIntakeDoubleSolenoid1;
	DoubleSolenoid doubleSolenoid2 = RobotMap.gearIntakeDoubleSolenoid2;
	DigitalInput bumperSwitch = RobotMap.gearBumperSwitch;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new GearIntakeCommand());
    }
    
    public enum State{
    	start,
    	gear,
    	floor
    }
    
    private State gearliftstate;
    private boolean isGearIn;
    
    public void GearIntake(double trigger){
    	intakeMotor.set(trigger);
    }
    
    public boolean GearBumperSwitchGet(){
    	return true;
//    	return bumperSwitch.get();
    }
    
    public boolean currentOverload(){
    	if(intakeMotor.getOutputCurrent() >= 40){
    		isGearIn = true;
    	} else {
    		isGearIn = false;
    	}
    	return isGearIn;
    }
    
    public void IntakePositionStart(){
    	singleSolenoid1.set(false);
    	singleSolenoid2.set(false);
    	doubleSolenoid1.set(DoubleSolenoid.Value.kForward);
    	doubleSolenoid2.set(DoubleSolenoid.Value.kForward);
    	gearliftstate = State.start;
    }
    
    public void IntakePositionGear(){
    	singleSolenoid1.set(true);
    	singleSolenoid2.set(true);
    	Timer.delay(.11);
    	doubleSolenoid1.set(DoubleSolenoid.Value.kForward);
    	doubleSolenoid2.set(DoubleSolenoid.Value.kForward);
    	gearliftstate = State.gear;  	
    }
    public void IntakePositionFloor(){
    	singleSolenoid1.set(false);
    	singleSolenoid2.set(false);
    	doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);
    	doubleSolenoid2.set(DoubleSolenoid.Value.kReverse);
    	gearliftstate = State.floor;
    }
    
    public State GearLiftStateGet(){
    	logger.log(RobotLogger.LoggerLevel.debug, "Gear intake" + gearliftstate);
    	return gearliftstate;
    }
}