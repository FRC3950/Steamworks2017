package org.usfirst.frc.team3950.robot.subsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotMap;
import org.usfirst.frc.team3950.robot.commands.*;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *What's the difference between a well dressed man on a bicycle and a poorly dressed man on a tricycle?
 *Attire!
 */
public class DrivetrainSubsystem extends Subsystem {
	private double P = 0;
	private double I = 0;
	private double D = 0;
	private double F = 0; 
	private static RobotLogger logger = new RobotLogger(DrivetrainSubsystem.class);
    CANTalon leftFront;
    CANTalon leftBack;
    CANTalon rightFront;
    CANTalon rightBack;
    RobotDrive drivetrain;
    Solenoid gearShiftSolenoid;
    AHRS navx;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	leftFront = RobotMap.leftFrontDriveMotor;
    	leftBack = RobotMap.leftBackDriveMotor;
    	rightFront = RobotMap.rightFrontDriveMotor;
    	rightBack = RobotMap.rightBackDriveMotor;
    	drivetrain = new RobotDrive(leftFront, leftBack, rightFront, rightBack);
    	gearShiftSolenoid = RobotMap.driveGearShiftSolenoid;
    	navx = RobotMap.ahrs;
    	P = Robot.robotConfig.drivetrainConfig.pidf.p;
    	I = Robot.robotConfig.drivetrainConfig.pidf.i;
    	D = Robot.robotConfig.drivetrainConfig.pidf.d;
    	F = Robot.robotConfig.drivetrainConfig.pidf.f;
    	
    	setDefaultCommand(new DriveCommand());
        
    }
    
    public void Drive(double y, double twist){
    	drivetrain.arcadeDrive(-y, -twist);
    }
    
    public void GearShift(){
    	gearShiftSolenoid.set(!gearShiftSolenoid.get());
    	SmartDashboard.putBoolean("High Gear", gearShiftSolenoid.get());
    	logger.log(RobotLogger.LoggerLevel.info, "gear state" + gearShiftSolenoid.get());
    }
    
    public double getCurrentAngle(){
    	logger.log(RobotLogger.LoggerLevel.info, "navX angle" + navx.getAngle());
    	return navx.getAngle();
    }
    public void autoDrive(double distanceCounts) {
    	leftFront.enable();
    	leftBack.enable();
    	rightFront.enable();
    	rightBack.enable();
    	
    	leftFront.setP(P);
    	leftFront.setI(I);
    	leftFront.setD(D);
    	leftFront.setF(F);
    	rightBack.setP(P);
    	rightBack.setI(I);
    	rightBack.setD(D);
    	rightBack.setF(F);
    	leftFront.changeControlMode(CANTalon.TalonControlMode.Position);
    	rightBack.changeControlMode(CANTalon.TalonControlMode.Position);
    	leftBack.changeControlMode(CANTalon.TalonControlMode.Follower);
    	rightFront.changeControlMode(CANTalon.TalonControlMode.Follower);
    	rightFront.set(rightBack.getDeviceID()); // not sure if correct just an assumption please test
    	leftBack.set(leftFront.getDeviceID()); // same as above
    	rightBack.set(distanceCounts);
    	leftFront.set(distanceCounts);    	
    }
    
    public void returnToTeleop() {
    	
    }
    

    
}
