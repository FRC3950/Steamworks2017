package org.usfirst.frc.team3950.robot.config;

public class ShooterConfig {
	public PIDF pidf;
	public CameraPipeline cameraPipeline;
	public int axisCameraOffset;
	
	public ShooterConfig() {
		pidf = new PIDF();
		cameraPipeline = new CameraPipeline();
		axisCameraOffset = 0;
	}
	
	public int getAxisCameraOffset() {
		return this.axisCameraOffset;
	}

	public void setAxisCameraOffset(int axisCameraOffset) {
		this.axisCameraOffset = axisCameraOffset;
	}
	
	public void setPidf(PIDF pidf) {
		this.pidf = pidf;
	}
	
	public void setCameraPipeline(CameraPipeline cameraPipeline) {
		this.cameraPipeline = cameraPipeline;
	}
	
	public PIDF getPidf() {
		return this.pidf;
	}
	
	public CameraPipeline getCameraPipeline() {
		return this.cameraPipeline;
	}
	
}
