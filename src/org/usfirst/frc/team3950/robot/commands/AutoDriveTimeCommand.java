package org.usfirst.frc.team3950.robot.commands;

import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.subsystems.AxisCameraSubsystem;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDriveTimeCommand extends Command {
	
	private static RobotLogger logger = new RobotLogger(AutoDriveTimeCommand.class);
	private long currTime;
	private long startTime;
	private long driveTime = 1900;
	private long pauseTime = 500;
	private long subTime;

    public AutoDriveTimeCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrainSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        isFinished = false;
    	startTime = System.currentTimeMillis();
    	logger.log(RobotLogger.LoggerLevel.debug, "startTime is:" + startTime);
    }

    boolean loggerBool = false;
    boolean isFinished = false;
    
    boolean shouldITurn = true;
    long forward1Time = 3800;
    long forward2Time = 0;
    long turnTime = 0;
    long reverseTime = 3800;
    double turnAngle = 0;
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currTime = System.currentTimeMillis();
    	logger.log(RobotLogger.LoggerLevel.debug, "currTime is:" + currTime);

    	if((currTime - startTime) <= forward1Time) {
    		// set voltage for robotdrive
    		Robot.drivetrainSubsystem.Drive(-.75, 0); //0.03);
    	} else {
    		if(shouldITurn) {
    			shouldITurn = false;
    			// turn the robot
    			if(turnAngle != 0) {
    				// then turn the robot
    	    		Robot.drivetrainSubsystem.Drive(0, turnAngle / 90.0); //0.03);
    			}
    			turnTime = System.currentTimeMillis() - currTime;
    		}
   	    	if((currTime - startTime) <= (forward1Time + forward2Time + turnTime)) {
   	    		// set voltage for robotdrive
   	    		Robot.drivetrainSubsystem.Drive(-.75, 0); //0.03);
   	    	} else {
   	   	    	if((currTime - startTime) <= (forward1Time + forward2Time + turnTime + reverseTime)) {
   	   	    		// set voltage for robotdrive
   	   	    		Robot.drivetrainSubsystem.Drive(.75, 0); //0.03);
   	   	    	} else {
   	        		Robot.drivetrainSubsystem.Drive(0, 0); //0.03);
   	        		isFinished = true;
   	   	    	}
   	    	}
    	}
    	
/*    	if((currTime - startTime) <= driveTime) {
    		Robot.drivetrainSubsystem.Drive(-.75, 0); //0.03);
    	} else {
    		if(loggerBool == false) {
    			loggerBool = true;
    			logger.log(RobotLogger.LoggerLevel.debug, "PauseTime - currTime - startTime is:" + (currTime - startTime));
    		}
    		Robot.drivetrainSubsystem.Drive(.75, 0); //0.03);
    		if((currTime - startTime) > (driveTime + pauseTime)) {
        		logger.log(RobotLogger.LoggerLevel.debug, "currTime - startTime is:" + (currTime - startTime));
        		Robot.drivetrainSubsystem.Drive(0, 0); //0.03);
        		isFinished = true;
    		}
    	} */
/*    	
    	else if((currTime - startTime) == driveTime) {
			subTime = System.currentTimeMillis();
			
		}
    	
    	else if(currTime - startTime >= driveTime) {
    		logger.log(RobotLogger.LoggerLevel.debug, "currTime - startTime is:" + (currTime - startTime));
    		logger.log(RobotLogger.LoggerLevel.debug, "currTime - driveTime" + (currTime - driveTime));
    		Robot.drivetrainSubsystem.Drive(.75, 0);
    	}

    	else if(currTime - subTime >= pauseTime) {
    		logger.log(RobotLogger.LoggerLevel.debug, "currTime - subTime" + (currTime - driveTime));
    		Robot.drivetrainSubsystem.Drive(0, 0);
    	} */
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
