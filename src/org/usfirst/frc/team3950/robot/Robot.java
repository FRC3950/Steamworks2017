package org.usfirst.frc.team3950.robot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team3950.robot.AutonomousUtil.ProfileType;
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
import edu.wpi.first.wpilibj.command.Subsystem;
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
	public static DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
	public static BallIntakeSubsystem ballintakesubsystem = new BallIntakeSubsystem();
	public static GearIntakeSubsystem gearintakesubsystem = new GearIntakeSubsystem();
	public static ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
	public static ShooterIndexerSubsystem shooterIndexerSubsystem = new ShooterIndexerSubsystem();
	public static AxisCameraSubsystem axisCameraSubsystem = new AxisCameraSubsystem();
	public static ClimberSubsystem climberSubsystem = new ClimberSubsystem(); 
	public static USBCameraSubsystem usbCameraSubsystem = new USBCameraSubsystem();
	public static AgitatorSubsystem agitatorSubsystem = new AgitatorSubsystem();
	
	

    Command autonomousCommand;
    SendableChooser<Command> chooser;
    SendableChooser<LoggerLevelSet> myLoggerChooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	logger.log(RobotLogger.LoggerLevel.info, "I am in robotInit");
    	logger.log(RobotLogger.LoggerLevel.info, Robot.robotConfig.toString());
    	
		oi = new OI();
		chooser = new SendableChooser<Command>();
		chooser.addDefault("Auto Drive Command", new AutoDriveCommand());
        chooser.addObject("Auto Gear Command", new AutoGearCommand());
        chooser.addObject("Auto Baseline Command", new AutoBaselineCommand());
        chooser.addObject("No Auto", new AutoNoneCommand());
        myLoggerChooser = new SendableChooser<LoggerLevelSet>();
        myLoggerChooser.addObject("trace", new LoggerLevelSet(RobotLogger.LoggerLevel.trace));
        myLoggerChooser.addObject("debug", new LoggerLevelSet(RobotLogger.LoggerLevel.debug));
        myLoggerChooser.addDefault("info", new LoggerLevelSet(RobotLogger.LoggerLevel.info));
        SmartDashboard.putData("Logger Level", myLoggerChooser);
        SmartDashboard.putData("Auto", chooser);
        
//    	autonomousCommand = new AutoDriveCommand();

//        chooser.addObject("My Auto", new MyAutoCommand());
//        SmartDashboard.putNumber("Proportion", 0.2);
//        SmartDashboard.putNumber("Derivative", 1.0);
//        SmartDashboard.putNumber("Integral", 0.0);
//        SmartDashboard.putNumber("Feed Forward", 0.025);
/*        RobotMap.shooterMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
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
        _loops = 0; */
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
	

  	double initd;
	public long currentTime;
	public long prevTime;
  	
  	
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
    	//autonomousCommand = new AutoDriveCommand();
    	logger.log(RobotLogger.LoggerLevel.info, "I am in Autonomous Init");
        
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
    	
/*    	currentTime = System.currentTimeMillis();
    	prevTime = currentTime;
    	//Robot.drivetrainSubsystem.Drive(1, 0);
    	Robot.drivetrainSubsystem.straightDrive(1);
    	
    	
    	GearPipeline gtbr = new GearPipeline();
        //Scheduler.getInstance().run();
     	Mat mat = Robot.usbCameraSubsystem.getNthFrame(Robot.robotConfig.shooterConfig.nthFrame);
 		gtbr.process(mat);
 		ArrayList<Rect> gearRects = new ArrayList<Rect>();
 		for(MatOfPoint mop : gtbr.filterContoursOutput()) {
 			Rect rect = Imgproc.boundingRect(mop);
 			gearRects.add(rect);
 		}
 		logger.log(RobotLogger.LoggerLevel.debug, "NUMBER OF RECTS " + gearRects.size());
     	Rect rect = VisionUtility.getRectContainer(gearRects, Robot.robotConfig.usbCameraSettings.width, Robot.robotConfig.usbCameraSettings.height);
     	logger.log(RobotLogger.LoggerLevel.debug, rect.toString());
//     	double distance = VisionUtility.getGearDistance(rect.width);
     	Robot.drivetrainSubsystem.setDistance(VisionUtility.getGearDistance(VisionUtility.getRectWidthAvg(gearRects)));
     	logger.log(RobotLogger.LoggerLevel.debug, "Distance: " + Robot.drivetrainSubsystem.getDistance());
    	initd = Robot.drivetrainSubsystem.getDistance();
    	
    	//RobotMap.leftFrontDriveMotor.changeControlMode(vPercentBus);
  */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	//double slope = (.5/(Robot.drivetrainSubsystem.getDistance()/2));
    	Scheduler.getInstance().run();
/*    	currentTime = System.currentTimeMillis();
    	if((currentTime - prevTime) < 1000){
    		return;
    	}
		logger.log(RobotLogger.LoggerLevel.debug, "leftBackControlMode: " + RobotMap.leftBackDriveMotor.getControlMode());
		logger.log(RobotLogger.LoggerLevel.debug, "leftBackGet: " + RobotMap.leftBackDriveMotor.get());
		logger.log(RobotLogger.LoggerLevel.debug, "leftFrontControlMode: " + RobotMap.leftFrontDriveMotor.getControlMode());
		logger.log(RobotLogger.LoggerLevel.debug, "leftFrontGet: " + RobotMap.leftFrontDriveMotor.get());

		RobotMap.leftBackDriveMotor.set(.5);
		RobotMap.leftFrontDriveMotor.set(.5);
//    	Robot.drivetrainSubsystem.straightDrive(.5);
    	
		logger.log(RobotLogger.LoggerLevel.debug, "leftBackGet: " + RobotMap.leftBackDriveMotor.get());
		logger.log(RobotLogger.LoggerLevel.debug, "leftFrontGet: " + RobotMap.leftFrontDriveMotor.get());

		GearPipeline gtbr = new GearPipeline();
    	Mat mat = Robot.usbCameraSubsystem.getNthFrame(Robot.robotConfig.shooterConfig.nthFrame);
		gtbr.process(mat);
		ArrayList<Rect> gearRects = new ArrayList<Rect>();
		for(MatOfPoint mop : gtbr.filterContoursOutput()) {
			Rect rect = Imgproc.boundingRect(mop);
			gearRects.add(rect);
		}
		logger.log(RobotLogger.LoggerLevel.debug, "NUMBER OF RECTS " + gearRects.size());
    	Rect rect = VisionUtility.getRectContainer(gearRects, Robot.robotConfig.usbCameraSettings.width, Robot.robotConfig.usbCameraSettings.height);
    	logger.log(RobotLogger.LoggerLevel.debug, rect.toString());
//    	double distance = VisionUtility.getGearDistance(rect.width);
    	Robot.drivetrainSubsystem.setDistance(VisionUtility.getGearDistance(VisionUtility.getRectWidthAvg(gearRects)));
    	logger.log(RobotLogger.LoggerLevel.debug, "Distance: " + Robot.drivetrainSubsystem.getDistance());
    	double distance = Robot.drivetrainSubsystem.getDistance();
    	
    	double voltage = AutonomousUtil.VoltageProfile(distance, initd, 1.0, ProfileType.Linear, 1.0);

    	if(voltage < 0.5) {
    		voltage = 0.5;
    	}
    	
*/
    	

    	/*    	if (distance == initd){
    		voltage = .1;
    	}
    	
    	else if(distance > (initd/2)){
    		voltage = (initd - distance)/(initd/2);
    	}
    	
    	else if(distance < (initd/2)){
    		voltage = distance/(initd/2);
    	}
    	
    	if(voltage != 0){
    		Robot.drivetrainSubsystem.Drive(voltage, 0);
    	}
 */    	
//    	logger.log(RobotLogger.LoggerLevel.debug, "Voltage: " + voltage);
    	}
    


    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        new DriveCommand().start();
        RobotMap.ahrs.reset();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
//    	LoggerLevelSet x = (LoggerLevelSet) myLoggerChooser.getSelected();
//    	RobotLogger.setLoggerLevel(x.getLevel());
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Shooter Setpoint", RobotMap.shooterMotor.getSetpoint());
        SmartDashboard.putNumber("Shooter RPM", RobotMap.shooterMotor.getSpeed());
        SmartDashboard.putNumber("Left Front Speed", RobotMap.leftVictor.get());
        SmartDashboard.putNumber("Left Back Value", RobotMap.leftFrontDriveMotor.get());
        SmartDashboard.putNumber("Right Front Value", RobotMap.rightVictor.get());
        SmartDashboard.putNumber("Right Back Value", RobotMap.leftBackDriveMotor.get());
        Robot.drivetrainSubsystem.getCurrentAngle();
        
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
