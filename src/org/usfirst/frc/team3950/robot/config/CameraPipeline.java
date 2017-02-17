package org.usfirst.frc.team3950.robot.config;

public class CameraPipeline {
	public HSL hsl;
	
	public FilterContours filterContours;
	
	public CameraPipeline() {
		hsl = new HSL();
		filterContours = new FilterContours();
	}
	
	public void setHsl(HSL h) {
		this.hsl = h;
	}
	
	public HSL getHsl() {
		return this.hsl;
	}
	
	public void setFilterContours(FilterContours filterContours) {
		this.filterContours = filterContours;
	}
	
	public FilterContours getFilterContours() {
		return this.filterContours;
	}
}
