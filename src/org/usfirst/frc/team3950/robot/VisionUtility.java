 package org.usfirst.frc.team3950.robot;

import java.util.ArrayList;

import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc.team3950.robot.subsystems.USBCameraSubsystem;

public class VisionUtility {
	private static RobotLogger logger = new RobotLogger(VisionUtility.class);
	
	  //Axis Camera Offset in Pixels
	  static int AxisCameraOffset = 0;
	  
	  //Boiler Distance Constants for Axis Camera
	  static double BoilerDistanceSlope = Robot.robotConfig.shooterConfig.distanceParameters.m;  //a
	  static double BoilerDistanceYInt = Robot.robotConfig.shooterConfig.distanceParameters.b;  //b
	  
	  //Gear Distance Constants for USB Camera
	  static double GearDistanceSlope;	//a
	  static double GearDistanceYInt;	//b
	  
	  //RPM Distance Constants
	  static double RPMSlope = Robot.robotConfig.shooterConfig.shooterRPMParameters.m;
	  static double RPMYInt = Robot.robotConfig.shooterConfig.shooterRPMParameters.b;
	
	  public static double getBoilerDistance(int width) {
	    	double distance = BoilerDistanceSlope/(width - BoilerDistanceYInt);
	    	return distance;
	    }
	  
	  public static double getGearDistance(int width) {
		  double distance = GearDistanceSlope/(width - GearDistanceYInt);
		  return distance;
	  }
	    
	    public static double getBoilerRPM60(double distance){
	    	double RPM = (RPMSlope*distance) + RPMYInt;
	    	return RPM;
	    }
	    
	    public static Rect getBoilerTotalRect(Rect rectOne, Rect rectTwo){
	    	Rect rectHigh = rectOne;
	    	Rect rectLow = rectTwo;
	    	if(rectOne.y < rectTwo.y){
	    		 rectHigh = rectTwo;
	    		 rectLow = rectOne;
	    	}
	    	Rect rectTotal = new Rect(rectHigh.tl(), rectLow.br());
	    	return rectTotal;
	    }
	    
	    public static double getBoilerAngle(int boilerRectCenterX, int boilerRectWidth, double cameraAngle, int cameraXRes, double boilerDistance){
	    	logger.log(RobotLogger.LoggerLevel.debug, "Boiler rect width: " + boilerRectWidth);
	    	double width = boilerRectWidth;
	    	double inch = (15/width);
	    	logger.log(RobotLogger.LoggerLevel.debug, "inches: " + inch);
	    	double feet = inch/12;
	    	logger.log(RobotLogger.LoggerLevel.debug, "Inch: " + inch);
	    	logger.log(RobotLogger.LoggerLevel.debug, "Feet: " + feet);
	    	int pixelDistance = boilerRectCenterX - ((cameraXRes/2) - AxisCameraOffset);
	    	double feetDistance = pixelDistance * feet;
	    	double angle = Math.asin(feetDistance/boilerDistance);
	    	double degreeAngle = angle * (180/Math.PI);
	    	return degreeAngle;
	    	
	    }
	    
	    public static Rect getRectContainer(ArrayList<Rect> rectList, int cameraWidth, int cameraHeight) {
	    	Rect rect = new Rect(0, 0, 0, 0);
	    	Point tl = new Point(cameraWidth - 1, cameraHeight - 1);
	    	Point br = new Point(0, 0);
	    	    	
	    	for(Rect x: rectList) {
//	    		System.out.println(x.toString());
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
	    }
}
