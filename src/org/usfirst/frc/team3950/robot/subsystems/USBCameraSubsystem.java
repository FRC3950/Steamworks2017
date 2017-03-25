package org.usfirst.frc.team3950.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team3950.robot.TestBoundingRectangles;
import org.usfirst.frc.team3950.robot.commands.USBCameraDoCommand;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3950.robot.GearPipeline;
import org.usfirst.frc.team3950.robot.GearPipelinePublishVideo;
import org.usfirst.frc.team3950.robot.GearPipelineRGB;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.TestBoundingRectangles;


/**
 *
 */
public class USBCameraSubsystem extends Subsystem {
	private static RobotLogger logger = new RobotLogger(USBCameraSubsystem.class);
	private static UsbCamera camera = null;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static CvSink cvSink = null;
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
		camera = CameraServer.getInstance().startAutomaticCapture(0);
		logger.log(RobotLogger.LoggerLevel.info, "camera server has started automatic capture");
		camera.setResolution(Robot.robotConfig.usbCameraSettings.width, Robot.robotConfig.usbCameraSettings.height);
//		System.out.println("camera has set resolution");
		camera.setWhiteBalanceManual(Robot.robotConfig.usbCameraSettings.whiteBalance);
		camera.setExposureManual(-10);
		camera.setBrightness(Robot.robotConfig.usbCameraSettings.cameraBrightness);
		cvSink = CameraServer.getInstance().getVideo(camera); //capture mats from camera
		logger.log(RobotLogger.LoggerLevel.info, "system has established cvsink");
		
//		setDefaultCommand(new USBCameraDoCommand());
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
    
}