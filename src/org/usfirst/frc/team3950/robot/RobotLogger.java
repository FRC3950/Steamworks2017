package org.usfirst.frc.team3950.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RobotLogger {
	private Logger logger; 
	public enum LoggerLevel {
		trace, 
		debug,
		info,
		warn,
		error
	}
	
	public RobotLogger(Class<?> location) {
		logger = LoggerFactory.getLogger(location);
	}
	public static LoggerLevel currentLoggerLevel = LoggerLevel.trace;
	
	public static void setLoggerLevel(LoggerLevel level) {
		currentLoggerLevel = level;
		
	}
	
	public void log(LoggerLevel level, String message) {
		if (level == LoggerLevel.error || level == LoggerLevel.warn) {
			switch (level) {
			case warn:
				logger.warn(message);
				break;
			case error: 	
				logger.error(message);
				break;
			}
		}
		if(level.compareTo(currentLoggerLevel) >= 0) {
			switch (level) {
			case trace: 
				logger.trace(message);
				break;
				
			case debug: 
				logger.debug(message);
				break;
				
			case info: 
				logger.info(message);
				break;
				
			}
		}					
	}
}
