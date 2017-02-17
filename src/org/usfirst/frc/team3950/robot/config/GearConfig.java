package org.usfirst.frc.team3950.robot.config;

public class GearConfig {
	public CameraPipeline gearPipeline;
	public LinearEquationParameters gearDistanceParameters;
	public int whiteBalance;
	public int brightness;
	public int exposure;
	
	
	public GearConfig() {

		gearPipeline = new CameraPipeline();
		gearDistanceParameters = new LinearEquationParameters();
		whiteBalance = 4500;
		brightness = 10;
		exposure = -10;
	}
	
	
	public void setGearPipeline(CameraPipeline gearPipeline) {
		this.gearPipeline = gearPipeline;
	}
	
	public CameraPipeline getGearPipeline() {
		return this.gearPipeline;
	}
	
	public void setGearDistanceParameters(LinearEquationParameters gearDistance) {
		this.gearDistanceParameters = gearDistance;
	}
	
	public LinearEquationParameters getGearDistanceParameters() {
		return this.gearDistanceParameters;
	}
	
	public void setExposure(int exposure) {
		this.exposure = exposure;
	}
	
	public int getExposure() {
		return this.exposure;
	}
	
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
	
	public int getBrightness() {
		return this.brightness;
	}
	
	public void setWhiteBalance(int whiteBalance) {
		this.whiteBalance = whiteBalance;
	}
	
	public int getWhiteBalance() {
		return this.whiteBalance;
	}
	
}
