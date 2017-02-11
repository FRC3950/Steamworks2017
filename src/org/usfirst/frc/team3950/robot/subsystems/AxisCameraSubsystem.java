package org.usfirst.frc.team3950.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3950.robot.RobotMap;
import org.usfirst.frc.team3950.robot.TestBoundingRectangles;
import org.usfirst.frc.team3950.robot.NewBoilerPipeline;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AxisCameraSubsystem extends Subsystem {
	
	private static AxisCamera camera = null;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public static CvSink cvSink = null;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	camera = CameraServer.getInstance().addAxisCamera("10.39.50.11");
    	camera.setResolution(320, 240);
    	cvSink = CameraServer.getInstance().getVideo(); //capture mats from camera
        System.out.println("I have enabled camera");
    }
    
    static boolean cameraEnabled = true;
    
    
    public static void startCamera() {
/*    	if(camera == null) {
    		camera = CameraServer.getInstance().addAxisCamera("10.39.50.11");
    		camera.setResolution(320, 240);
    		cvSink = CameraServer.getInstance().getVideo(); //capture mats from camera
            System.out.println("I have enabled camera");
    	}
    	else {
    		CameraServer.getInstance().removeCamera("10.39.50.11");
            System.out.println("I have disabled camera");
            camera = null;
    	} */
    }
    
    public static void stopCamera()
    {    	
    	//System.out.println("null");
    	//camera = null;
    	
    	//System.out.println("free");
    	//camera.setVideoMode(null);
    	
    	System.out.println("I HAVE STOPPED");
    	}
    
    public double getBoilerDistance(int width) {
    	double distance = 506.8/(width - 15.28);
    	return distance;
    }
    
    public double getBoilerRPM60(double distance){
    	double RPM = (100*distance) + 3500;
    	return RPM;
    }
    
    public Rect getBoilerTotalRect(Rect rectOne, Rect rectTwo){
    	Rect rectHigh = rectOne;
    	Rect rectLow = rectTwo;
    	if(rectOne.y < rectTwo.y){
    		 rectHigh = rectTwo;
    		 rectLow = rectOne;
    	}
    	Rect rectTotal = new Rect(rectHigh.tl(), rectLow.br());
    	return rectTotal;
    }
    
    public double getBoilerAngle(int boilerRectCenterX, int boilerRectWidth, double cameraAngle, int cameraXRes, double boilerDistance){
    	double inch = (15/boilerRectWidth);
    	double feet = inch/12;
    	int pixelDistance = boilerRectCenterX - (cameraXRes/2);
    	double feetDistance = pixelDistance * feet;
    	double angle = Math.asin(feetDistance/boilerDistance);
    	return angle;
    }
    
    
    public ArrayList<Rect> getRectangles() {
    	AxisCameraSubsystem.startCamera();
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
				
				System.out.println("RectOne: " + rectOne.height + "  " + rectOne.width + "  " + rectOne.area());
				System.out.println("RectTwo: " + rectTwo.height + "  " + rectTwo.width + "  " + rectTwo.area());
				System.out.println((((int)rectOne.height) + ((int)rectTwo.height)) / 2); 

				//rect.x is the left edge afaik
				//rect.y is the top edge afaik
				int centerXOne = rectOne.x + (rectOne.width/2); //returns the center of the bounding rectangle
				int centerYOne = rectOne.y + (rectOne.height/2); //returns the center of the bounding rectangle
				int centerXTwo = rectTwo.x + (rectTwo.width/2);
				int centerYTwo = rectTwo.y + (rectTwo.height/2);
				int centerYAvg = (centerYOne + centerYTwo)/2;
				int centerXAvg = (centerXOne + centerXTwo)/2;

				System.out.println(centerXAvg);
			}
			else {
				System.out.println("NO");
			}
		}
		return boilerRects;
    }
//    public void FindBoundingRectangles(){
//   	System.out.println(pipeline.filterContoursOutput().size());
//    	if(pipeline.filterContoursOutput().size()==2){
//    		Rect rectOne = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
//    		Rect rectTwo = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
//    		System.out.println(rectOne.area());
//    		System.out.println(rectTwo.area());
//    	}
//    }
}

