package org.usfirst.frc.team3950.robot.commands;

import java.util.ArrayList;

import org.opencv.core.Rect;
import org.usfirst.frc.team3950.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GearDistanceCommand extends Command {

    public GearDistanceCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.usbCameraSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("hello from the geardistance command");
//    	ArrayList<Rect> gearRects = Robot.usbCameraSubsystem.getGearRectangles();
    	System.out.println("ArrayList of gearRect made");
//    	Rect totalRect = Robot.usbCameraSubsystem.getRectContainer(gearRects, 639, 359);
//    	System.out.println("Total Rect: " + totalRect.x + " " + totalRect.y + " " + totalRect.width + " " + totalRect.height);
    	
    	/*Rect zeroRect = new Rect(0,0,0,0);
    	Rect totalRect = new Rect(0,0,0,0);
    	int size = gearRects.size();
    	if(size == 2) {
    		Rect RectOne = gearRects.get(0);
    		System.out.println("RectOne Rect: " + RectOne.x + " " + RectOne.y + " " + RectOne.width + " " + RectOne.height);
    		Rect RectTwo = gearRects.get(1);
    		System.out.println("RectTwo Rect: " + RectTwo.x + " " + RectTwo.y + " " + RectTwo.width + " " + RectTwo.height);
    		totalRect = Robot.usbCameraSubsystem.getRectContainer(RectOne, RectTwo);
    		System.out.println("wowow there are two rectangles");
    	}
    	else if(size == 3){
    		Rect RectOne = gearRects.get(0);
    		System.out.println("RectOne Rect: " + RectOne.x + " " + RectOne.y + " " + RectOne.width + " " + RectOne.height);
    		Rect RectTwo = gearRects.get(1);
    		System.out.println("RectTwo Rect: " + RectTwo.x + " " + RectTwo.y + " " + RectTwo.width + " " + RectTwo.height);
    		Rect RectThree = gearRects.get(2);
    		System.out.println("RectThree Rect: " + RectThree.x + " " + RectThree.y + " " + RectThree.width + " " + RectThree.height);
    		//totalRect = Robot.usbCameraSubsystem.getGearTotalRect(RectOne, RectTwo);
    		//totalRect = Robot.usbCameraSubsystem.getGearTotalRect(totalRect, RectThree);
    		System.out.println("wowowow there are THREE rectangles");
    	}
    	else if (totalRect != zeroRect) {
    		System.out.println("Total Rect: " + totalRect.x + " " + totalRect.y + " " + totalRect.width + " " + totalRect.height);
    	}
    	// Rect totalRect = Robot.usbCameraSubsystem.getGearTotalRect(Robot.usbCameraSubsystem.testRectOne, Robot.usbCameraSubsystem.testRectTwo);
    */
    	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
