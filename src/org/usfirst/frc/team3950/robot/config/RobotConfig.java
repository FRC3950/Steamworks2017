package org.usfirst.frc.team3950.robot.config;

public class RobotConfig {

	public ShooterConfig shooterConfig;
	public GearConfig gearConfig;
	public DrivetrainConfig drivetrainConfig;
	
	public RobotConfig() {
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
