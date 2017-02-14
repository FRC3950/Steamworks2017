package org.usfirst.frc.team3950.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
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
		camera = CameraServer.getInstance().startAutomaticCapture(0);
		System.out.println("camera server has started automatic capture");
		camera.setResolution(640, 360);
//		System.out.println("camera has set resolution");
		camera.setWhiteBalanceManual(4500);
//		camera.setExposureManual(-10);
		camera.setBrightness(10);
		
		cvSink = CameraServer.getInstance().getVideo(camera); //capture mats from camera
		System.out.println("system has established cvsink");
		
		setDefaultCommand(new USBCameraDoCommand());
    }
    

    private int idx = 0;
    public Mat getFrame() {
    	Mat mat = new Mat();
    	if(cvSink != null) {
    		cvSink.grabFrame(mat);
 //   		System.out.println(mat.toString());
    		org.opencv.imgcodecs.Imgcodecs.imwrite("/home/lvuser/source_" + idx++ + ".jpg", mat);
    	}
    		return mat;
    }
    
    public static void startCamera() {
/*    	if(camera == null) {
    		camera = CameraServer.getInstance().startAutomaticCapture(0);
    		System.out.println("camera server has started automatic capture");
    		camera.setResolution(640, 360);
    		System.out.println("camera has set resolution");
    		camera.setWhiteBalanceManual(4500);
    		camera.setExposureManual(-11);
    		
    		cvSink = CameraServer.getInstance().getVideo(); //capture mats from camera
    		System.out.println("system has established cvsink");
    	} */
    
    }
 
   /* public Rect testRectOne = new Rect(4, 2, 3, 6);
    public Rect testRectTwo = new Rect(5, 4, 1, 2);
    public Rect testRectThree = new Rect(8, 2, 4, 3);
    public Rect testRectFour = new Rect(6, 3, 5, 7);
    public Rect testRectFive = new Rect(1, 10, 1, 1);
   
    public static Rect getRectContainer(ArrayList<Rect> rectList, int cameraWidth, int cameraHeight) {
    	Rect rect = new Rect(0, 0, 0, 0);
    	Point tl = new Point(cameraWidth - 1, cameraHeight - 1);
    	Point br = new Point(0, 0);
    	    	
    	for(Rect x: rectList) {
    		System.out.println(x.toString());
    		tl.x = Math.min(tl.x, x.tl().x);
    		tl.y = Math.min(tl.y, x.tl().y);
    		br.x = Math.max(br.x, x.br().x);
    		br.y = Math.max(br.y, x.br().y);
    	}
    	rect.x = (int) tl.x;
    	rect.y = (int) tl.y;
    	rect.width = (int) (br.x - tl.x);
    	rect.height = (int) (br.y - tl.y);
    	
    	return rect;
    } */    
    /*
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
    */

/*    private CvSource outputStream = null;
    public ArrayList<Rect> getGearRectangles(){
		ArrayList<Rect> gearRects = new ArrayList<Rect>();
    	USBCameraSubsystem.startCamera();
    	Mat mat = new Mat();
		System.out.println("i am in get gear rectangle");
    	if(cvSink.grabFrame(mat) == 0) {
    		System.out.println(cvSink.getError());
    	} else {
    		System.out.println(mat.toString());
    		System.out.println("i am in gear rectangle else");
    		GearPipelineRGB gtbr = new GearPipelineRGB();
    		gtbr.process(mat);
    		System.out.println("processed mat");
    		System.out.println("size: " + gtbr.filterContoursOutput().size());
    		gtbr.process(mat);
    		System.out.println("processed mat");
    		System.out.println("size: " + gtbr.filterContoursOutput().size());
//    		for(MatOfPoint mop : gtbr.filterContoursOutput()) {
//				System.out.println(Imgproc.boundingRect(mop).toString());
//			}
    		//if((gtbr.filterContoursOutput().size() >= 2) && (gtbr.filterContoursOutput().size() < 4))
    		{
    			System.out.println("inside else inside if");
    			for(MatOfPoint mop : gtbr.filterContoursOutput()) {
    				Rect rect = Imgproc.boundingRect(mop);
    				System.out.println(rect.toString());
    				gearRects.add(rect);
    			}
    		}
//    		else {
//				gearRects.add(new Rect());
//				gearRects.add(new Rect());
//    		}
    		System.out.println("inside else but outisde of if");
    			
    		
    		//}
    	} 
    	
    	return gearRects;
    } */
}
   

