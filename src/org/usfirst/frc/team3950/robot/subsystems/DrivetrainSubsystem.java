package org.usfirst.frc.team3950.robot.subsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotMap;
import org.usfirst.frc.team3950.robot.commands.*;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *What's the difference between a well dressed man on a bicycle and a poorly dressed man on a tricycle?
 *Attire!
 */
public class DrivetrainSubsystem extends Subsystem implements PIDSource, PIDOutput {
	private double P = 0;
	private double I = 0;
	private double D = 0;
	private double F = 0; 
	public double distance;
	private static RobotLogger logger = new RobotLogger(DrivetrainSubsystem.class);
    CANTalon leftFront;
    CANTalon leftBack;
    CANTalon rightFront;
    Victor left;
    Victor right;
    CANTalon rightBack;
    RobotDrive drivetrain;
    public Solenoid gearShiftSolenoid;
    AHRS navx;
	private static PIDController pid;
	boolean pidInit;
	boolean gyroPID;

    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public double getDistance() {
		return distance;
	}
	public void setDistance(double d) {
		distance = d;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	leftFront = RobotMap.leftFrontDriveMotor;
    	leftBack = RobotMap.leftBackDriveMotor;
    	//leftFront.setInverted(true);
    	//leftBack.setInverted(true);
    	rightFront = RobotMap.rightFrontDriveMotor;
    	rightBack = RobotMap.rightBackDriveMotor;
    	left = RobotMap.leftVictor;
    	right = RobotMap.rightVictor;
    	//left.setInverted(true);
    	//right.setInverted(true);
    	//drivetrain = new RobotDrive(leftFront, leftBack, rightFront, rightBack);
    	//drivetrain = new RobotDrive(right, leftBack, left, leftFront);
    	drivetrain = new RobotDrive(leftBack, leftFront);
    	gearShiftSolenoid = RobotMap.driveGearShiftSolenoid;
    	navx = RobotMap.ahrs;
		pid = new PIDController(0, 0, 0, this, this);
		pid.disable();
		pid.setInputRange(-180.0f,  180.0f);
		pid.setOutputRange(-0.5f, 0.5f);
		pid.setAbsoluteTolerance(2f);
        pid.setContinuous(true);
    	P = Robot.robotConfig.drivetrainConfig.pidf.p;
    	I = Robot.robotConfig.drivetrainConfig.pidf.i;
    	D = Robot.robotConfig.drivetrainConfig.pidf.d;
    	F = Robot.robotConfig.drivetrainConfig.pidf.f;
    	pidInit = false;
    	gyroPID = false;
    	
    	setDefaultCommand(new DriveCommand());
        
    }
    
    public void Drive(double y, double twist){
    	drivetrain.arcadeDrive(-y, -twist);
    }
    
	public double getNavxAngle() {
		return navx.pidGet();
	}
	
	public void resetNavx() {
		navx.reset();
	}
    
	public void driveStraightNavX(double power) {
		if(getNavxAngle() > Robot.robotConfig.drivetrainConfig.DT_NAVX_ERROR_THRESHOLD){
			Drive(power, power * -.05);
		}
		else if(getNavxAngle() > -Robot.robotConfig.drivetrainConfig.DT_NAVX_ERROR_THRESHOLD){
			Drive(power, power * .05);
		} else {
			Drive(power, 0);
		}
	}
    
    public void gearShift(){
    	gearShiftSolenoid.set(!gearShiftSolenoid.get());    	
//    	SmartDashboard.putBoolean("High Gear", gearShiftSolenoid.get());
    	logger.log(RobotLogger.LoggerLevel.info, "high gear state" + gearShiftSolenoid.get());
    }
    
  /*  public void GearShiftLow(){
    	gearShiftSolenoid.set(gearShiftSolenoid.get());
    	logger.log(RobotLogger.LoggerLevel.info, "low gear state" + gearShiftSolenoid.get());
    }
    */
    
    public double getCurrentAngle(){
    	logger.log(RobotLogger.LoggerLevel.info, "navX angle" + navx.getAngle());
    	return navx.getAngle();
    }
    
/*	public static void setPIDtoGyro() {
		if(!pidInit) {
			gyroPID = true;
			pid = new PIDController(Robot.robotConfig.drivetrainConfig.pidf.p, Robot.robotConfig.drivetrainConfig.pidf.i, Robot.robotConfig.drivetrainConfig.pidf.d, navx, Robot.drivetrainSubsystem);
			pid.setInputRange(-180.0f,  180.0f);
			pid.setOutputRange(-0.75f, 0.75f);
			pid.setAbsoluteTolerance(2f);
	        pid.setContinuous(true);
		}
        pidInit = true;
	}*/
	
	/*public static void turnAngle(double setPoint) {
		setPIDtoGyro();
		pid.enable();
		pid.setSetpoint(setPoint);
		
	}*/
    
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
    public void autoDrive() {
    	pid.enable();
    	pid.setSetpoint(3);
    	pid.setPID(P, I, D);
    	
    }
    
 /*   public void turnAngle(double angle){
    	double initAngle = navx.getAngle();
    	double finalAngle = angle + initAngle;
    	if(angle < 0){
    	
    	}
    }*/
    
    public void returnToTeleop() {
    	pid.disable();
    }

	@Override
	public void pidWrite(double output) {
		//drivetrain.drive(output, 0);
		this.driveStraightNavX(output);
		logger.log(RobotLogger.LoggerLevel.debug, "Output is: " + output);
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidtype = pidSource;
		
	}
	private PIDSourceType pidtype = PIDSourceType.kDisplacement;
	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return pidtype;
	}

	@Override
	public double pidGet() {
		return distance;
	}
    

    
}
