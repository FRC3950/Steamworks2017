package org.usfirst.frc.team3950.robot;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team3950.robot.RobotLogger.LoggerLevel;
import org.usfirst.frc.team3950.robot.commands.*;
import org.usfirst.frc.team3950.robot.config.RobotConfig;
import org.usfirst.frc.team3950.robot.subsystems.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static RobotLogger logger = new RobotLogger(Robot.class);
	public static RobotConfig robotConfig = RobotConfig.getInstance();
	
	public static OI oi;
	public static DrivetrainSubsystem drivetrainsubsystem = new DrivetrainSubsystem();
	public static BallIntakeSubsystem ballintakesubsystem = new BallIntakeSubsystem();
	public static GearIntakeSubsystem gearintakesubsystem = new GearIntakeSubsystem();
	public static ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
	public static ShooterIndexerSubsystem shooterIndexerSubsystem = new ShooterIndexerSubsystem();
	public static AxisCameraSubsystem axisCameraSubsystem = new AxisCameraSubsystem();
	public static ClimberSubsystem climberSubsystem = new ClimberSubsystem(); 
	public static USBCameraSubsystem usbCameraSubsystem = new USBCameraSubsystem();
	public static AgitatorSubsystem agitatorSubsystem = new AgitatorSubsystem();
	
	

    Command autonomousCommand;
    SendableChooser chooser;
    SendableChooser myLoggerChooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	logger.log(RobotLogger.LoggerLevel.info, "I am in robotInit");
    	logger.log(RobotLogger.LoggerLevel.info, Robot.robotConfig.toString());
    	
		oi = new OI();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new DriveCommand());
        
        myLoggerChooser = new SendableChooser();
        myLoggerChooser.addObject("trace", new LoggerLevelSet(RobotLogger.LoggerLevel.trace));
        myLoggerChooser.addObject("debug", new LoggerLevelSet(RobotLogger.LoggerLevel.debug));
        myLoggerChooser.addDefault("info", new LoggerLevelSet(RobotLogger.LoggerLevel.info));
        SmartDashboard.putData("Logger Level", myLoggerChooser);
//        chooser.addObject("My Auto", new MyAutoCommand());
//        SmartDashboard.putNumber("Proportion", 0.2);
//        SmartDashboard.putNumber("Derivative", 1.0);
//        SmartDashboard.putNumber("Integral", 0.0);
//        SmartDashboard.putNumber("Feed Forward", 0.025);
        RobotMap.shooterMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        RobotMap.shooterMotor.reverseSensor(false);
        RobotMap.shooterMotor.configNominalOutputVoltage(+0.0f, -0.0f);
        RobotMap.shooterMotor.configPeakOutputVoltage(+12.0f, -12.0f);
        RobotMap.shooterMotor.setProfile(0);
        RobotMap.shooterMotor.setF(0.1);
        RobotMap.shooterMotor.setP(0.1);
        RobotMap.shooterMotor.setI(0.0);
        RobotMap.shooterMotor.setD(0.0);
        _talon = RobotMap.shooterMotor;
        _joy = oi.driveStick;
        _loops = 0;
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
//        autonomousCommand = (Command) chooser.getSelected();
        autonomousCommand = new AutoDriveCommand();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        RobotMap.ahrs.reset();
    }

    private CANTalon _talon = RobotMap.shooterMotor;
    private Joystick _joy;
    private int _loops;
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
//    	LoggerLevelSet x = (LoggerLevelSet) myLoggerChooser.getSelected();
//    	RobotLogger.setLoggerLevel(x.getLevel());
        Scheduler.getInstance().run();
        
        /*double leftYStick = _joy.getAxis(AxisType.kY);
        double motorOutput = _talon.getOutputVoltage() / _talon.getBusVoltage();
        
        logger.log(LoggerLevel.debug, "\tout:" + motorOutput + "\tspd:" + _talon.getSpeed());
        
        if(_joy.getRawButton(1)) {
        	double targetSpeed = leftYStick * 1500;
        	_talon.changeControlMode (TalonControlMode.Speed);
        	_talon.set(targetSpeed);
        	
            logger.log(LoggerLevel.debug, "\terr:" + _talon.getClosedLoopError() + "\ttrg:" + targetSpeed);
        }
//        else {
//        	_talon.changeControlMode (TalonControlMode.PercentVbus);
//        	_talon.set(leftYStick);
//        }
        
        if(++_loops >= 10) {
        	_loops = 0;
        } */
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
