package org.usfirst.frc.team3950.robot.subsystems;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class USBCameraSubsystem extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	UsbCamera cam1 = new UsbCamera("cam1", 1);
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void startCamera() {
    	//can operate at the same time as other things
    	new Thread(() -> { 
    	//creates camera object and gets the image
    	UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    	camera.setResolution(640,480);
    	
    	//vision stuff
    	CvSink cvSink = CameraServer.getInstance().getVideo();
    	CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
    	
    	//creates matrices because an image is a matrix
    	Mat source = new Mat();
    	Mat output = new Mat();
    	
    	//if the thread is not interrupted then it outputs the frame
    	while (!Thread.interrupted()){
    		cvSink.grabFrame(source);
    		Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
    		outputStream.putFrame(output);
    	}
    	}).start();
    }
    
    public void getGearRectangles(){
    	
    }
}

