package org.usfirst.frc.team3950.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3950.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.*;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
	private double P = 0;
	private double I = 0;
	private double D = 0;
	private double F = 0;
	private int range = 10;
	private CANTalon motor;
	private int targetRPM = 0;
	private boolean running = false; // not set up yet.
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	motor = RobotMap.shooterMotor;
    	motor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	motor.reverseSensor(false);
    	motor.changeControlMode(TalonControlMode.Speed);
    	motor.configNominalOutputVoltage(+0.0f, -0.0f);
    	motor.configPeakOutputVoltage(+12.0f, -12.0f);
    	motor.setProfile(0);
    	motor.setF(F);
    	motor.setP(P);
    	motor.setI(I);
    	motor.setD(D);
    	motor.set(0);
    	SmartDashboard.putNumber("Feed Forward", F);
    	SmartDashboard.putNumber("Proportion", P);
    	SmartDashboard.putNumber("Integral", I);
    	SmartDashboard.putNumber("Derivative", D);
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void setTargetRPM(double rpm) {
    	//targetRPM = (int) rpm;
    	motor.enable();
    	motor.set(rpm);
    }
	public double getP() {
		return P;
	}
	public void setP(double p) {
		P = p;
		motor.setP(p);
	}
	public double getI() {
		return I;
	}
	public void setI(double i) {
		motor.setI(i);
		I = i;
	}
	public double getD() {
		return D;
	}
	public void setD(double d) {
		motor.setD(d);
		D = d;
	}
	public double getF() {
		return F;
	}
	public void setF(double f) {
		motor.setF(f);
		F = f;
	}
	public boolean speedInRange() {
		return (motor.getEncVelocity() > targetRPM - range) && (motor.getEncVelocity() < targetRPM - range);
	}
}

