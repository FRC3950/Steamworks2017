package org.usfirst.frc.team3950.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3950.robot.RobotMap;
import com.ctre.CANTalon;

/**
 *
 */
public class ShooterIndexerSubsystem extends Subsystem {
	private CANTalon indexerMotor;
	private DigitalInput limSwitch;
	

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	indexerMotor = RobotMap.shooterIndexerMotor;
    	limSwitch = RobotMap.shooterIndexerSwitch;
    	
    			
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void indexerMotorForward() {
    	indexerMotor.set(1);
    }
    public void indexerMotorBackward() {
    	indexerMotor.set(-1);
    }
    public void indexerMotorStop() {
    	indexerMotor.set(0);
    }
    public boolean detectLimitSwitch() {
    	return limSwitch.get();
    }
}