package org.usfirst.frc.team3950.robot.config;

public class PIDF {
	public double p = 0;
	public double i = 0;
	public double d = 0;
	public double f = 0;
	
	public PIDF() {
	}

	public void setP(double p) {
		this.p = p;
	}
	
	public void setI(double i) {
		this.i = i;
	}
	
	public void setD(double d) {
		this.d = d;
	}
	
	public void setF(double f) {
		this.f = f;
	}
	
	public double getP() {
		return p;
	}

	public double getI() {
		return i;
	}

	public double getD() {
		return d;
	}

	public double getF() {
		return f;
	}

}
