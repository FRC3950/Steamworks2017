package org.usfirst.frc.team3950.robot.subsystems;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class USBCameraSubsystem extends Subsystem {
	
	private static UsbCamera camera = null;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static CvSink cvSink = null;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void initCamera() {
    	if(camera == null) {
    		camera = CameraServer.getInstance().startAutomaticCapture(0);
    		camera.setResolution(640, 480);
    	}
    }
    
    public static void startCamera() {
    	if(camera != null) {
//    		camera = CameraServer.getInstance().startAutomaticCapture(0);
//    		camera.setResolution(640, 480);
    		cvSink = CameraServer.getInstance().getVideo(); //capture mats from camera
    	}
    }
    
    public void getGearRectangles(){
    	
    }
}

