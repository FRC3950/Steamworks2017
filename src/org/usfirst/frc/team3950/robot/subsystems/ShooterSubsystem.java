package org.usfirst.frc.team3950.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.*;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
	private static RobotLogger logger =new RobotLogger(ShooterSubsystem.class);
	private int range = 10;
	private CANTalon motor;
	private int targetRPM; 
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	motor = RobotMap.shooterMotor;
//    	motor.configEncoderCodesPerRev(1024);
    	motor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	motor.reverseSensor(true);
    	motor.changeControlMode(TalonControlMode.Speed);
    	motor.configNominalOutputVoltage(+0.0f, -0.0f);
    	motor.configPeakOutputVoltage(+0.0f, -12.0f);
    	motor.setProfile(0);
//    	motor.setF(Robot.robotConfig.shooterConfig.pidf.f);
//    	motor.setP(Robot.robotConfig.shooterConfig.pidf.p);
//    	motor.setI(Robot.robotConfig.shooterConfig.pidf.i);
//    	motor.setD(Robot.robotConfig.shooterConfig.pidf.d);
//    	motor.set(1000);
//    	motor.enable();


        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void setTargetRPM(double rpm) {
    	logger.log(RobotLogger.LoggerLevel.debug, "sets targetRPM= " + rpm);
    	targetRPM = (int)rpm;
    	SmartDashboard.putNumber("Target RPM", targetRPM);
//    	targetRPM = (int) rpm;
    	motor.enable();
    	motor.set(rpm);
    }
	public void setP(double p) {
		logger.log(RobotLogger.LoggerLevel.debug, "sets proportion value= " + p);
    	SmartDashboard.putNumber("Proportion", p);
		motor.setP(p);
	}
	public void setI(double i) {
		logger.log(RobotLogger.LoggerLevel.debug, "sets integral value= " + i);
    	SmartDashboard.putNumber("Integral", i);
		motor.setI(i);
	}
	public void setD(double d) {
		logger.log(RobotLogger.LoggerLevel.debug, "sets derivative value= " + d);
    	SmartDashboard.putNumber("Derivative", d);
		motor.setD(d);
	}
	public void setF(double f) {
		logger.log(RobotLogger.LoggerLevel.debug, "sets the feed forward= " + f) ;
    	SmartDashboard.putNumber("Feed Forward", f);
		motor.setF(f);
	}
	public boolean speedInRange() {
		return (motor.getEncVelocity() > targetRPM - range) && (motor.getEncVelocity() < targetRPM - range);
	}
	public void shutOffMotor() {
		motor.set(0);
		motor.disable();
	}
}
