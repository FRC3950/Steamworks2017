
package org.usfirst.frc.team3950.robot.commands;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3950.robot.AutonomousUtil;
import org.usfirst.frc.team3950.robot.GearPipeline;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.RobotMap;
import org.usfirst.frc.team3950.robot.VisionUtility;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDriveCommand extends Command {
	
	
	private class NavxRunnable implements Runnable {
		private long prevTime = System.currentTimeMillis();
		private long intervalTime = 10; // 10 msecs
		private double angle = 0;
		private boolean stop = false;
		
		private synchronized void setAngle(double angle) {
			this.angle = angle;
		}

		public synchronized double getAngle() {
			return this.angle;
		}

		private synchronized void setStop(boolean stop) {
			this.stop = stop;
			
		}

		public synchronized boolean getStop() {
			return this.stop;
		}

		public void stop() {
			this.setStop(true);
		}
		
		@Override
		public void run() {
			while(true)
			{
//				logger.log(RobotLogger.LoggerLevel.debug, "NavxRunnable.run");
				if(stop)
					break;
				
				long currTime = System.currentTimeMillis();
				if((currTime - prevTime) > intervalTime) {
				    this.setAngle(RobotMap.ahrs.getAngle());
//				    logger.log(RobotLogger.LoggerLevel.info, "Angle is: " + RobotMap.ahrs.getAngle());
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	private class GearPipelineRunnable implements Runnable {
		private long prevTime = System.currentTimeMillis();
		private long intervalTime = 1; // 10 msecs
		private double distance = 0;
		private boolean stop = false;
		private double initDistance = 0;
		private boolean firstTimeThrough = true;
		
		private synchronized void setDistance(double distance) {
			this.distance = distance;
		}

		public synchronized double getDistance() {
			return this.distance;
		}

		private synchronized void setInitDistance(double initDistance) {
			this.initDistance = initDistance;
		}

		public synchronized double getInitDistance() {
			return this.initDistance;
		}

		private synchronized void setStop(boolean stop) {
			this.stop = stop;
		}

		public synchronized boolean getStop() {
			return this.stop;
		}

		public void stop() {
			this.setStop(true);
		}
		
		@Override
		public void run() {
			while(true)
			{
				long startTime = System.currentTimeMillis();
				logger.log(RobotLogger.LoggerLevel.debug, "GearPipelineRunnable.run");
				if(stop)
					break;
				
				long currTime = System.currentTimeMillis();
				logger.log(RobotLogger.LoggerLevel.debug, "current time: " + currTime);
				logger.log(RobotLogger.LoggerLevel.debug, "prev time: " + prevTime);
				logger.log(RobotLogger.LoggerLevel.debug, "prev time: " + (currTime - prevTime));
				if((currTime - prevTime) > intervalTime)
				{
					// get the nth frame from the camera
					long startTimeNthFrame= System.currentTimeMillis();
					Mat mat = Robot.usbCameraSubsystem.getNthFrame(Robot.robotConfig.shooterConfig.nthFrame);
					long endTimeNthFrame = System.currentTimeMillis();
					
					logger.log(RobotLogger.LoggerLevel.debug, "Nth Frame run time: " + (endTimeNthFrame - startTimeNthFrame));
					// process the image through the gear pipeline
					startTimeNthFrame= System.currentTimeMillis();
					gtbr.process(mat);
					endTimeNthFrame= System.currentTimeMillis();
					logger.log(RobotLogger.LoggerLevel.debug, "gtbr run time: " + (endTimeNthFrame - startTimeNthFrame));
					// extract the 
					startTimeNthFrame= System.currentTimeMillis();
					ArrayList<Rect> gearRects = new ArrayList<Rect>();
					for(MatOfPoint mop : gtbr.filterContoursOutput()) {
						gearRects.add(Imgproc.boundingRect(mop));
					}
					int numGearRects = gearRects.size();
					if(!(numGearRects > 0 && numGearRects <= 3))
						logger.log(RobotLogger.LoggerLevel.error, "NUMBER OF RECTS: " + numGearRects);
					else {
						logger.log(RobotLogger.LoggerLevel.debug, "NUMBER OF RECTS: " + numGearRects);
				    	Rect rect = VisionUtility.getRectContainer(gearRects, Robot.robotConfig.usbCameraSettings.width, Robot.robotConfig.usbCameraSettings.height);
				    	logger.log(RobotLogger.LoggerLevel.debug, "CONTAINER RECT: " + rect.toString());
				    	// double distance = VisionUtility.getGearDistance(rect.width);
				    	double distance = VisionUtility.getGearDistance(rect.width); //VisionUtility.getRectWidthAvg(gearRects));
				    	logger.log(RobotLogger.LoggerLevel.debug, "Distance " + distance + "width pixels: " + rect.width);
				    	this.setDistance(distance);
				    	if(firstTimeThrough) {
				    		this.setInitDistance(distance);
				    		firstTimeThrough = false;
				    	}
				    	
					}
					endTimeNthFrame= System.currentTimeMillis();
			    	logger.log(RobotLogger.LoggerLevel.debug, "gearRects run time: " + (endTimeNthFrame - startTimeNthFrame));
					prevTime = currTime;
				}
				long endTime = System.currentTimeMillis();
				logger.log(RobotLogger.LoggerLevel.debug, "Start time:  " + startTime);
				logger.log(RobotLogger.LoggerLevel.debug, "End time:  " + endTime);
				logger.log(RobotLogger.LoggerLevel.debug, "Time run:  " + (endTime - startTime));
//				try {
//					Thread.sleep(10);
//				} catch (InterruptedException e) {
//				}
			}
		}
	}
	
	private static RobotLogger logger = new RobotLogger(AutoDriveCommand.class);
	
	private boolean finished = false;
    private GearPipeline gtbr = new GearPipeline();
    private GearPipelineRunnable gearPipelineRunnable = null;
    private double initialDistance = 0.0;
    private double distanceTolerance = 4.0;
    private NavxRunnable navxRunnable = null;
	private Thread threadGearPipelineRunnable = null;
	private Thread threadNavxRunnable = null;
	private double initAngle = 0;

    public AutoDriveCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//divide by twelve if needed in inches
    	//distanceCounts = distanceFeet * 2607.5;
    	requires(Robot.usbCameraSubsystem);
    	requires(Robot.drivetrainSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    //	double totalCounts = (63.8/(2*Math.PI *4))*4096;
    //	Robot.drivetrainSubsystem.autoDrive(totalCounts);
    	
    
    	// make sure drive train is not moving
    	Robot.drivetrainSubsystem.Drive(0, 0);
//    	Robot.gearintakesubsystem.IntakePositionStart();
    	logger.log(RobotLogger.LoggerLevel.info, "I am in pastAutonomousDriveCommand Init");
    	// set navx to zero
	    initAngle = RobotMap.ahrs.getAngle();
    	
    	// create runnable for camera subsystem
        if(gearPipelineRunnable == null)
        	gearPipelineRunnable = this.new GearPipelineRunnable();

        // create runnable for navx
        if(navxRunnable == null)
        	navxRunnable = this.new NavxRunnable(); 
        
    	// create and start a thread for gearPipelineRunnable
        threadGearPipelineRunnable = new Thread(gearPipelineRunnable);
        threadGearPipelineRunnable.start();
        
    	// create and start a thread for navxRunnable
        threadNavxRunnable = new Thread(navxRunnable);
        threadNavxRunnable.start();

        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
        // need to initialize distance to gear placement
        initialDistance = gearPipelineRunnable.getDistance();
    	logger.log(RobotLogger.LoggerLevel.info, "initialDistance: " + initialDistance);
    	
        prevDistance = 0;
    	voltage = 0;
    	twist = 0;
        distanceTolerance = 4.0;
        targetTime = 0;
        maxVoltage = .6;
        minVoltage = .5;
        
    }
    
    
    
    double prevTime = 0;
    double maxVoltage = .6;
    double minVoltage = .5;
    double prevDistance = 0;
	double voltage = 0;
	double twist = 0;
	double velocity = 0;
	double targetTime = 0;
	//double totalCounts = (63.8/(2*Math.PI *4))*4096;
    // Called repeatedly when this Command is scheduled to run 

    
    protected void execute() {
    	//if(RobotMap.leftFrontDriveMotor.getEncPosition() <= totalCounts){
    		//RobotMap.leftFrontDriveMotor.
    	//}
    	//else if(RobotMap.leftFrontDriveMotor.getEncPosition() > totalCounts)
    	
    	//	finished = true;
    	
		long currTime = System.currentTimeMillis();
//    	logger.log(RobotLogger.LoggerLevel.info, "I am in AutonomousDriveCommand Execute");
    	if(!navxRunnable.getStop()) {
    		double angle = navxRunnable.getAngle() - initAngle;
    		twist = (angle / 15.0) + .12;	
    		logger.log(RobotLogger.LoggerLevel.debug, "Twist is: " + twist);
    	}
    	if(!gearPipelineRunnable.getStop()) {
    		double distance = gearPipelineRunnable.getDistance();
    		if(distance != prevDistance) {
    			logger.log(RobotLogger.LoggerLevel.info, "Distance to gear: " + distance);
        		voltage = AutonomousUtil.VoltageProfile1(distance, initialDistance, maxVoltage, AutonomousUtil.ProfileType.Linear, minVoltage, .5 * gearPipelineRunnable.getInitDistance());   		
    			logger.log(RobotLogger.LoggerLevel.info, "Voltage: " + voltage);
    			if(prevDistance != 0) {
    				velocity = (distance - prevDistance) / (currTime - prevTime);
        			logger.log(RobotLogger.LoggerLevel.info, "Velocity: " + velocity);
    			}
    			prevDistance = distance;
    			prevTime = currTime;
    		}   	
    	}
    	
		if((prevDistance - distanceTolerance) <= 0) {
	    	if(!gearPipelineRunnable.getStop())
	    		logger.log(RobotLogger.LoggerLevel.info, "Within distanceTolerance: " + distanceTolerance);
//	    	logger.log(RobotLogger.LoggerLevel.info, "stop");
			gearPipelineRunnable.stop();
		//	navxRunnable.stop();
			if(targetTime == 0) {
				targetTime = -prevDistance / velocity;
		    	logger.log(RobotLogger.LoggerLevel.info, "TargetTime: " + targetTime);
    			prevTime = currTime;
			} else {
				if((currTime - prevTime + 153) > targetTime) {
					finished = true;
					voltage = 0;
					twist = 0;
				}
			}
//			finished = true;
//			voltage = 0;
//			twist = 0;
		}
		//logger.log(RobotLogger.LoggerLevel.info, "Voltage: " + voltage + "  Twist: " + twist);
    	Robot.drivetrainSubsystem.Drive(-voltage, twist);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.log(RobotLogger.LoggerLevel.info, "I am in pastAutonomousDriveCommand End");
    	Robot.drivetrainSubsystem.returnToTeleop();
    	Robot.drivetrainSubsystem.Drive(0, 0);

    	if(gearPipelineRunnable != null)
    		gearPipelineRunnable.stop();
    	
    	if(navxRunnable != null)
    		navxRunnable.stop();
        
    	threadGearPipelineRunnable = null;
        threadNavxRunnable = null;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.log(RobotLogger.LoggerLevel.info, "I am in pastAutonomousDriveCommand interrupted");
    	Robot.drivetrainSubsystem.returnToTeleop();
    	Robot.drivetrainSubsystem.Drive(0, 0);

    	if(gearPipelineRunnable != null)
    		gearPipelineRunnable.stop();
    	
    	if(navxRunnable != null)
    		navxRunnable.stop();
        
    	threadGearPipelineRunnable = null;
        threadNavxRunnable = null;
    }
}

