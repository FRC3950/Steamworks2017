package org.usfirst.frc.team3950.robot.config;

import java.io.File;
import java.io.IOException;

import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotLogger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RobotConfig {
	public USBCameraSettings usbCameraSettings;
	public ShooterConfig shooterConfig;
	public GearConfig gearConfig;
	public DrivetrainConfig drivetrainConfig;
	public AxisCameraSettings axisCameraSettings;
	
	private static RobotConfig instance = null;
	private static ObjectMapper mapper = null;
	
	public static RobotConfig getInstance() { 
		if (instance != null)
			return instance;
		mapper = new ObjectMapper();
		try {
			instance = mapper.readValue(new File("/home/lvuser/FRCUserProgram.cfg"), RobotConfig.class);
		} catch (JsonParseException e) {
			Robot.logger.log(RobotLogger.LoggerLevel.error,e.getMessage());
		} catch (JsonMappingException e) {
			Robot.logger.log(RobotLogger.LoggerLevel.error,e.getMessage());
		} catch (IOException e) {
			Robot.logger.log(RobotLogger.LoggerLevel.error,e.getMessage());
		}
		return instance;
	}
	
	public String toString() {
		try {
			return mapper.writeValueAsString(instance);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			Robot.logger.log(RobotLogger.LoggerLevel.error,e.getMessage());
		}
		return "";
	}
	
	private RobotConfig() {
		shooterConfig = new ShooterConfig();
	}
	
	public void setShooterConfig(ShooterConfig shooterConfig) {
		this.shooterConfig = shooterConfig;
	}
	
	public ShooterConfig getShooterConfig() {
		return shooterConfig;
	}

	public void setGearConfig(GearConfig gearConfig) {
		this.gearConfig = gearConfig;
	}
	
	public GearConfig getGearConfig() {
		return gearConfig;
	}
	
	public void setDrivetrainConfig(DrivetrainConfig drivetrainConfig) {
		this.drivetrainConfig = drivetrainConfig;
	}
	
	public DrivetrainConfig getDrivetrainConfig() {
		return drivetrainConfig;
	}
}
