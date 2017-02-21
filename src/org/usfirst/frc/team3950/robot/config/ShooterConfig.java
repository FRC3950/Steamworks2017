package org.usfirst.frc.team3950.robot.config;

public class ShooterConfig {
	public PIDF pidf;
	public CameraPipeline cameraPipeline;
	public LinearEquationParameters shooterRPMParameters;
	public int axisCameraOffset;
	public int nthFrame;
	public LinearEquationParameters distanceParameters;
	public double agitatorVoltage;
	public double indexerVoltage;

	
	public ShooterConfig() {
		pidf = new PIDF();
		cameraPipeline = new CameraPipeline();
		shooterRPMParameters = new LinearEquationParameters();
		axisCameraOffset = 0;
		nthFrame = 5;
		agitatorVoltage = 0.2;
		indexerVoltage = -1;
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
	
	public LinearEquationParameters getShooterRPMParameters() {
		return this.shooterRPMParameters;
	}
	
	public void setShooterRPMParameters(LinearEquationParameters lep) {
		this.shooterRPMParameters = lep;
	}
	public LinearEquationParameters getDistanceParameters() {
		return this.distanceParameters;
	}
	
	public void setDistanceParameters(LinearEquationParameters lep) {
		this.distanceParameters = lep;
	}
	public int getNthFrame() {
		return this.nthFrame;
	}

	public void setNthFrame(int nthFrame) {
		this.nthFrame = nthFrame;
	}
	public double getAgitatorVoltage() {
		return this.agitatorVoltage;
	}

	public void setAgitatorVoltage(double agitatorVoltage) {
		this.agitatorVoltage = agitatorVoltage;
	}
	public double getIndexerVoltage() {
		return this.indexerVoltage;
	}

	public void setIndexerVoltage(double indexerVoltage) {
		this.indexerVoltage = indexerVoltage;
	}
	
}
