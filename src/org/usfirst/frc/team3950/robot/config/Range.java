package org.usfirst.frc.team3950.robot.config;

public class Range {
	public int lower = 1;
	public int upper = 255;
	
	public Range() {
		lower = 1;
		upper = 255;
	}

	public void setLower(int l) {
		lower = l;
	}
	public void setUpper(int u) {
		upper = u;
	}
	public int getLower() {
		return lower;
	}
	public int getUpper() {
		return upper;
	}
}
