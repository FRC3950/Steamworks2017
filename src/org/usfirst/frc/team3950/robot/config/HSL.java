package org.usfirst.frc.team3950.robot.config;

public class HSL {
	public Range hue;
	public Range saturation;
	public Range luminosity;
	
	public HSL() {
		hue = new Range();
		saturation = new Range();
		luminosity = new Range();
	}
	
	public void setHue(Range h) {
		hue = h;
	}
	
	public Range getHue() {
		return hue;
	}
	public void setSaturation(Range s) {
		saturation = s;
	}
	
	public Range getSaturation() {
		return saturation;
	}
	public void setLuminosity(Range l) {
		luminosity = l;
	}
	
	public Range getLuminosity() {
		return luminosity;
	}
}
