package org.usfirst.frc.team3950.robot.config;

public class DrivetrainConfig {
	public PIDF pidf;
	
	public void setPidf(PIDF pidf) {
		this.pidf = pidf;
	}
	
	public PIDF getPidf() {
		return this.pidf;
	}
}
