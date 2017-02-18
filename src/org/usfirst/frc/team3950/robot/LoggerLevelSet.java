package org.usfirst.frc.team3950.robot;

public class LoggerLevelSet {
	private RobotLogger.LoggerLevel level;
	
	public LoggerLevelSet(RobotLogger.LoggerLevel lv) {
		level = lv;
	}
	public RobotLogger.LoggerLevel getLevel() {
		return level;
	}
}
