package org.usfirst.frc.team3950.robot.subsystems;

import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AgitatorSubsystem extends Subsystem {
	private static RobotLogger logger =new RobotLogger(AgitatorSubsystem.class);
	private CANTalon agitatorMotor;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	agitatorMotor = RobotMap.agitatorMotor;
    	agitatorMotor.set(0);
    	agitatorMotor.configPeakOutputVoltage(12.0, 0);
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void motorVoltage(double voltage){
    	logger.log(RobotLogger.LoggerLevel.debug, "agitatorMotor forward");
    	agitatorMotor.set(voltage);
    }
//    public void hopperMotorBackward(){
//    	logger.log(RobotLogger.LoggerLevel.debug, "hopper motor backward");
//   	hopperMotor.set(-1);
    public void motorStop() {
    	logger.log(RobotLogger.LoggerLevel.debug, "agitatorMotor stop");
    	agitatorMotor.set(0);
    }
}


