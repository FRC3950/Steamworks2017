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
    
}
