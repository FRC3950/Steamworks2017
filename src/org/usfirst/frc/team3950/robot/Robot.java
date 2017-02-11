package org.usfirst.frc.team3950.robot;

import org.opencv.core.Mat;
import org.usfirst.frc.team3950.robot.commands.*;
import org.usfirst.frc.team3950.robot.subsystems.*;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
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

	public static OI oi;
	public static DrivetrainSubsystem drivetrainsubsystem = new DrivetrainSubsystem();
	public static BallIntakeSubsystem ballintakesubsystem = new BallIntakeSubsystem();
	public static GearIntakeSubsystem gearintakesubsystem = new GearIntakeSubsystem();
	public static ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
	public static ShooterIndexerSubsystem shooterIndexerSubsystem = new ShooterIndexerSubsystem();
	public static AxisCameraSubsystem axisCameraSubsystem = new AxisCameraSubsystem();
	public static ClimberSubsystem climberSubsystem = new ClimberSubsystem(); 
	public static USBCameraSubsystem usbCameraSubsystem = new USBCameraSubsystem();
	

    Command autonomousCommand;
    SendableChooser chooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        System.out.println("I am in robotInit");
//    	usbCameraSubsystem.initCamera();
		oi = new OI();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new DriveCommand());
//        chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putNumber("Proportion", 0.2);
        SmartDashboard.putNumber("Derivative", 1.0);
        SmartDashboard.putNumber("Integral", 0.0);
        SmartDashboard.putNumber("Feed Forward", 0.025);
//        System.out.println("I am in robotInit");

//		CameraServer.getInstance().removeCamera("USB");
//		CameraServer.getInstance().removeCamera("Axis Camera");
//		CameraServer.getInstance().removeCamera("USB Camera 0");
        /*        new Thread(() -> {
            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
            camera.setResolution(640, 480);
            
            CvSink cvSink = CameraServer.getInstance().getVideo();
            CvSource outputStream = CameraServer.getInstance().putVideo("USB", 640, 480);
            
            Mat source = new Mat();
            Mat oldsource = new Mat();
  
            long t1 = (new java.util.Date().getTime())/1000;
            long t2 = t1;
            while(!Thread.interrupted()) {
            	try {
					Thread.sleep(250);
				} catch (Exception e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}
               // t2 = (new java.util.Date().getTime())/1000;
           		if((t2 - t1) >= 1) 
            	{
	                cvSink.grabFrame(source);
	                oldsource = source;
	                outputStream.putFrame(source);
	                t1 = t2;
            	}
           		//outputStream.putFrame(oldsource);
           	}
        }).start();
*/
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
        autonomousCommand = (Command) chooser.getSelected();
        
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
    StringBuilder _sb = new StringBuilder();
    double test;
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
     //   double motorOutputVoltage = RobotMap.shooterMotor.getOutputVoltage();
     //  double motorOutputBus = RobotMap.shooterMotor.getBusVoltage();
    	/* prepare line to print */
		/*_sb.append("\toutVoltage:");
		_sb.append(motorOutputVoltage);
		_sb.append("\toutBus:");
		_sb.append(motorOutputBus);
        _sb.append("\tspd:");
        _sb.append(RobotMap.shooterMotor.getSpeed() );
        SmartDashboard.putNumber("Shooter speed", RobotMap.shooterMotor.getSpeed());
        test = SmartDashboard.getNumber("Test", 0);
        _sb.append("\terr:");
        _sb.append(RobotMap.shooterMotor.getClosedLoopError());
        _sb.append("\ttrg:");
        _sb.append(ShooterStartCommand.targetRPM);
//        System.out.println(_sb);
        _sb.setLength(0);*/
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
