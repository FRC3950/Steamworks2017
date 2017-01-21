package org.usfirst.frc.team3950.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team3950.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.*;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
	private double P = 0;
	private double I = 0;
	private double D = 0;
	private int range = 10;
	private CANTalon motor;
	private int targetRPM = 0;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	motor = RobotMap.shooterMotor;
    	motor.changeControlMode(TalonControlMode.Speed);
    	motor.setP(P);
    	motor.setI(I);
    	motor.setD(D);
    	motor.set(0);
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void setTargetRPM(double rpm) {
    	targetRPM = (int) rpm;
    	motor.set(rpm);
    }
	public double getP() {
		return P;
	}
	public void setP(double p) {
		P = p;
	}
	public double getI() {
		return I;
	}
	public void setI(double i) {
		I = i;
	}
	public double getD() {
		return D;
	}
	public void setD(double d) {
		D = d;
	}
	public boolean speedInRange() {
		return (motor.getEncVelocity() > targetRPM - range) && (motor.getEncVelocity() < targetRPM - range);
	}
    
}

