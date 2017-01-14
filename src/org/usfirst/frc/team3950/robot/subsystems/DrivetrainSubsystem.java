package org.usfirst.frc.team3950.robot.subsystems;

import org.usfirst.frc.team3950.robot.RobotMap;
import org.usfirst.frc.team3950.robot.commands.*;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *What's the difference between a well dressed man on a bicycle and a poorly dressed man on a tricycle?
 *Attire!
 */
public class DrivetrainSubsystem extends Subsystem {
    TalonSRX leftFront;
    TalonSRX leftBack;
    TalonSRX rightFront;
    TalonSRX rightBack;
    RobotDrive drivetrain;
    Solenoid gearShiftSolenoid;
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
    	
    	setDefaultCommand(new DriveCommand());
        
    }
    
    public void Drive(double y, double twist){
    	drivetrain.arcadeDrive(y, twist);
    }
    
    public void GearShift(){
    	gearShiftSolenoid.set(!gearShiftSolenoid.get());
    	SmartDashboard.putBoolean("High Gear", gearShiftSolenoid.get());
    }
}

