package org.usfirst.frc.team3950.robot.config;

public class RobotConfig {

	public ShooterConfig shooterConfig;
	
	public RobotConfig() {
		shooterConfig = new ShooterConfig();
	}
	
	public void setShooterConfig(ShooterConfig shooterConfig) {
		this.shooterConfig = shooterConfig;
	}
	
	public ShooterConfig getShooterConfig() {
		return shooterConfig;
	}
}
