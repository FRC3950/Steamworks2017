package org.usfirst.frc.team3950.robot.subsystems;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3950.robot.TestBoundingRectangles;

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
     
    public Rect testRectOne = new Rect(4, 2, 3, 6);
    public Rect testRectTwo = new Rect(5, 4, 1, 2);
    public Rect testRectThree = new Rect(8, 2, 4, 3);
    public Rect testRectFour = new Rect(6, 3, 5, 7);
    public Rect testRectFive = new Rect(1, 10, 1, 1);
    
    public Rect getGearTotalRect(Rect rectOne, Rect rectTwo) {
    	int x, y, width, height;
    	//find x and width
    	if ((rectOne.x < rectTwo.x) && ((rectOne.x + rectOne.width) < (rectTwo.x + rectTwo.width))){
    		x = rectOne.x;
    		width = rectTwo.x + rectTwo.width;
    	}
    	else if (rectOne.x < rectTwo.x){
    		x = rectOne.x;
    		width = rectOne.x + rectOne.width;
    	}
    	else if ((rectTwo.x + rectTwo.width) > (rectOne.x + rectOne.width)){
    		x = rectTwo.x;
    		width = rectTwo.x + rectTwo.width;
    	}
    	else{
    		x = rectTwo.x;
    		width = rectOne.x + rectOne.width;
    	}
    	//find y and width
    	if ((rectOne.y < rectTwo.y) && ((rectOne.y + rectOne.height) < (rectTwo.y + rectTwo.height))){
    		y = rectOne.y;
    		height = rectTwo.y + rectTwo.height;
    	}
    	else if (rectOne.y < rectTwo.y){
    		y = rectOne.y;
    		height = rectOne.y + rectOne.height;
    	}
    	else if ((rectTwo.y + rectTwo.height) > (rectOne.y + rectOne.height)){
    		y = rectTwo.y;
    		height = rectTwo.y + rectTwo.height;
    	}
    	else{
    		y = rectTwo.y;
    		height = rectOne.y + rectOne.height;
    	}
    	//create total rectangle
    	Rect rectTotal = new Rect(x, y, width - x, height - y);
    	return rectTotal;
    }
}
   

