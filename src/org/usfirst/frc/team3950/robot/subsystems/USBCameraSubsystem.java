package org.usfirst.frc.team3950.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3950.robot.GearPipeline;
import org.usfirst.frc.team3950.robot.TestBoundingRectangles;


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
    
    public static void startCamera() {
    	if(camera == null) {
    		camera = CameraServer.getInstance().startAutomaticCapture(0);
    		System.out.println("camera server has started automatic capture");
    		camera.setResolution(640, 360);
    		System.out.println("camera has set resolution");
    		camera.setWhiteBalanceManual(4500);
    		camera.setExposureManual(-10);
    		
    		cvSink = CameraServer.getInstance().getVideo(); //capture mats from camera
    		System.out.println("system has established cvsink");
    	}
    
    }
    
    public ArrayList<Rect> getGearRectangles(){
		ArrayList<Rect> gearRects = new ArrayList<Rect>();
    	USBCameraSubsystem.startCamera();
    	Mat mat = new Mat();
		System.out.println("i am in get gear rectangle");
    	if(cvSink.grabFrame(mat) == 0) {
    		System.out.println(cvSink.getError());
    	} else {
    		System.out.println("i am in gear rectangle else");
    		GearPipeline gtbr = new GearPipeline();
    		gtbr.process(mat);
    		System.out.println("processde mat");
    		System.out.println("size: " + gtbr.filterContoursOutput().size());
    		if((gtbr.filterContoursOutput().size() >= 2) && (gtbr.filterContoursOutput().size() < 4)){
    			for(MatOfPoint mop : gtbr.filterContoursOutput()) {
    				gearRects.add(Imgproc.boundingRect(mop));
    			}
    		}
    	} 
    	
    	return gearRects;
    } 
}

