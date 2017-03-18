 package org.usfirst.frc.team3950.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team3950.robot.RobotMap;
import org.usfirst.frc.team3950.robot.TestBoundingRectangles;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.NewBoilerPipeline;
import org.usfirst.frc.team3950.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AxisCameraSubsystem extends Subsystem {
	private static RobotLogger logger = new RobotLogger(AxisCameraSubsystem.class);
	
	private static AxisCamera camera = null;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static CvSink cvSink = null;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	camera = CameraServer.getInstance().addAxisCamera("10.39.50.11");
    	camera.setResolution(Robot.robotConfig.axisCameraSettings.width, Robot.robotConfig.axisCameraSettings.height);
    	cvSink = CameraServer.getInstance().getVideo(); //capture mats from camera
    	logger.log(RobotLogger.LoggerLevel.info, "CAMERA ENABLED");
    }
    
    public Mat getFrame() {
    	Mat mat = new Mat();
    	if(cvSink != null) {
    		cvSink.grabFrame(mat);
//    		org.opencv.imgcodecs.Imgcodecs.imwrite("/home/lvuser/source_" + idx++ + ".jpg", mat);
    	}
    	return mat;
    }
    
    public Mat getNthFrame(int N){
    	Mat mat = new Mat();
    	if(N <= 0){
    		return null;
    	}
    	while(N --> 0){
    		if(cvSink != null) {
    			cvSink.grabFrame(mat);
//    			org.opencv.imgcodecs.Imgcodecs.imwrite("/home/lvuser/source_" + idx++ + ".jpg", mat);
    		}
    	}
    	return mat;
    }
    
    private static NewBoilerPipeline gtbr = new NewBoilerPipeline();

    public ArrayList<Rect> getRectangles() {
    	logger.log(RobotLogger.LoggerLevel.debug, "getRectangles - Nth frame is: " + Robot.robotConfig.shooterConfig.nthFrame);
		Mat mat = Robot.axisCameraSubsystem.getNthFrame(Robot.robotConfig.shooterConfig.nthFrame);
		gtbr.process(mat);
		ArrayList<Rect> boilerRects = new ArrayList<Rect>();
		for(MatOfPoint mop : gtbr.filterContoursOutput()) {
			Rect rect = Imgproc.boundingRect(mop);
			boilerRects.add(rect);
			logger.log(RobotLogger.LoggerLevel.debug, rect.toString());
		}
		return boilerRects;
    }

 /*   public double getBoilerAngle(int boilerRectCenterX, int boilerRectWidth, double cameraAngle, int cameraXRes, double boilerDistance){
    	logger.log(RobotLogger.LoggerLevel.debug, "Boiler rect width: " + boilerRectWidth);
    	double width = boilerRectWidth;
    	double inch = (15.0/width);
    	logger.log(RobotLogger.LoggerLevel.debug, "inches: " + inch);
    	double feet = inch/12.0;
    	logger.log(RobotLogger.LoggerLevel.debug, "Inch: " + inch);
    	logger.log(RobotLogger.LoggerLevel.debug, "Feet: " + feet);
    	int pixelDistance = boilerRectCenterX - (cameraXRes/2);
    	double feetDistance = pixelDistance * feet;
    	double angle = Math.asin(feetDistance/boilerDistance);
    	double degreeAngle = angle * (180/Math.PI);
    	return degreeAngle;
    	
    }
    
  public ArrayList<Rect> getRectangles() {
		Mat mat = new Mat(); //define mat in order to reuse it
		ArrayList<Rect> boilerRects = new ArrayList<Rect>();
		if(AxisCameraSubsystem.cvSink.grabFrame(mat) != 0) {
			NewBoilerPipeline tbr = new NewBoilerPipeline();
			tbr.process(mat);
			if(tbr.filterContoursOutput().size() == 2) {
				MatOfPoint mop1 = tbr.filterContoursOutput().get(0);
				MatOfPoint mop2 = tbr.filterContoursOutput().get(1);
				Rect r1 = Imgproc.boundingRect(mop1); //get the first MatOfPoint (contour), calculate bounding rectangle
				Rect r2 = Imgproc.boundingRect(mop2); //get the second MatOfPoint (contour)
				
				Rect rectOne = r1;
				Rect rectTwo = r2;
				
				boilerRects.add(r1);
				boilerRects.add(r2);
				
				logger.log(RobotLogger.LoggerLevel.debug, "RectOne: " + rectOne.height + "  " + rectOne.width + "  " + rectOne.area());
				logger.log(RobotLogger.LoggerLevel.debug, "RectTwo: " + rectTwo.height + "  " + rectTwo.width + "  " + rectTwo.area());
				logger.log(RobotLogger.LoggerLevel.debug, "Average" + (((int)rectOne.height) + ((int)rectTwo.height)) / 2);

				//rect.x is the left edge 
				//rect.y is the top edge 
				int centerXOne = rectOne.x + (rectOne.width/2); //returns the center of the bounding rectangle
				int centerYOne = rectOne.y + (rectOne.height/2); //returns the center of the bounding rectangle
				int centerXTwo = rectTwo.x + (rectTwo.width/2);
				int centerYTwo = rectTwo.y + (rectTwo.height/2);
				int centerYAvg = (centerYOne + centerYTwo)/2;
				int centerXAvg = (centerXOne + centerXTwo)/2;

				logger.log(RobotLogger.LoggerLevel.debug, "The boiler centerX average is" + centerXAvg);
			}
			else {
				logger.log(RobotLogger.LoggerLevel.debug, "NO");
			}
		}
		return boilerRects;
    } */
}

