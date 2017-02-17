package org.usfirst.frc.team3950.robot.config;

public class LinearEquationParameters {
	public double m;
	public double b;
	
	public LinearEquationParameters(){
		m = 0;
		b = 0;
	}
	
	public double getM() {
		return this.m;
	}

	public void setM(double m) {
		this.m = m;
	}
	
	public double getB() {
		return this.b;
	}

	public void setB(double b) {
		this.b = b;
	}
	
}
