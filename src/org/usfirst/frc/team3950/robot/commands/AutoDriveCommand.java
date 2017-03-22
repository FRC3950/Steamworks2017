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
				if(stop)
					break;
				
				long currTime = System.currentTimeMillis();
				if((currTime - prevTime) > intervalTime) {
				    this.setAngle(RobotMap.ahrs.getAngle());
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
		private long intervalTime = 10; // 10 msecs
		private double distance = 0;
		private boolean stop = false;
		
		private synchronized void setDistance(double distance) {
			this.distance = distance;
		}

		public synchronized double getDistance() {
			return this.distance;
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
				if(stop)
					break;
				
				long currTime = System.currentTimeMillis();
				if((currTime - prevTime) > intervalTime)
				{
					// get the nth frame from the camera
					Mat mat = Robot.usbCameraSubsystem.getNthFrame(Robot.robotConfig.shooterConfig.nthFrame);
					
					// process the image through the gear pipeline
					gtbr.process(mat);
					
					// extract the 
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
				    	double distance = VisionUtility.getGearDistance(VisionUtility.getRectWidthAvg(gearRects));
				    	logger.log(RobotLogger.LoggerLevel.debug, "Distance: " + distance);
				    	this.setDistance(distance);
					}
					prevTime = currTime;
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	private static RobotLogger logger = new RobotLogger(AutoDriveCommand.class);
	
	private boolean finished = false;
    private GearPipeline gtbr = new GearPipeline();
    private GearPipelineRunnable gearPipelineRunnable = null;
    private double initialDistance = 0.0;
    private double distanceTolerance = .5;
    private NavxRunnable navxRunnable = null;
	private Thread threadGearPipelineRunnable = null;
	private Thread threadNavxRunnable = null;

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
    	// make sure drive train is not moving
    	Robot.drivetrainSubsystem.Drive(0, 0);

    	// set navx to zero
	    RobotMap.ahrs.reset();
    	
    	// create runnable for camera subsystem
        if(gearPipelineRunnable == null)
        	gearPipelineRunnable = this.new GearPipelineRunnable();
        // need to initialize distance to gear placement
        initialDistance = gearPipelineRunnable.getDistance();

        // create runnable for navx
        if(navxRunnable == null)
        	navxRunnable = this.new NavxRunnable();
        
    	// create and start a thread for gearPipelineRunnable
        threadGearPipelineRunnable = new Thread(gearPipelineRunnable);
        threadGearPipelineRunnable.start();
        
    	// create and start a thread for navxRunnable
        threadNavxRunnable = new Thread(navxRunnable);
        threadNavxRunnable.start();
    }
    
    // Called repeatedly when this Command is scheduled to run 
    protected void execute() {
    	if(threadGearPipelineRunnable != null) {
    		double distance = gearPipelineRunnable.getDistance();
    		if((distance - distanceTolerance) <= 0) {
    			gearPipelineRunnable.stop();
    			finished = true;
    		}
    		double voltage = AutonomousUtil.VoltageProfile(distance, initialDistance, 1, AutonomousUtil.ProfileType.Linear, .3);
        	Robot.drivetrainSubsystem.Drive(voltage, 0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
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
