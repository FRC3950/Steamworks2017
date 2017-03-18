package org.usfirst.frc.team3950.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.RobotMap;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

/**
 *
 */
public class ShooterIndexerSubsystem extends Subsystem {
	private static RobotLogger logger =new RobotLogger(ShooterIndexerSubsystem.class);
	private CANTalon indexerMotor;
	private DigitalInput limSwitch;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {

    	indexerMotor = RobotMap.shooterIndexerMotor;
    	indexerMotor.changeControlMode(TalonControlMode.Voltage);
    	indexerMotor.set(0);
    	indexerMotor.configPeakOutputVoltage(0, -12.0);
    	limSwitch = RobotMap.shooterIndexerSwitch;
    	
    			
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void setMotorVoltage(double voltage) {
    	logger.log(RobotLogger.LoggerLevel.debug, "Shooter indexer motor voltage: " + voltage);
    	indexerMotor.set(voltage);
    }
    public void indexerMotorStop() {
    	logger.log(RobotLogger.LoggerLevel.debug, "Shooter indexer stop");
    	indexerMotor.set(0);
    }
    public boolean detectLimitSwitch() {
 //   	logger.log(RobotLogger.LoggerLevel.debug, "limit switch detected");
   // 	return limSwitch.get();
    	return true;
    }
}