package org.usfirst.frc.team3950.robot.config;

public class FilterContours {
	public int minArea;
	public int minPerimeter;
	public int minWidth;
	public int maxWidth;
	public int minHeight;
	public int maxHeight;
	public Range solidity;
	public int maxVertices;
	public int minVertices;
	public int minRatio;
	public int maxRatio;

	public FilterContours() {
		minArea = 50;
		minPerimeter = 0;
		minWidth = 0;
		maxWidth = 1000;
		minHeight = 0;
		maxHeight = 1000;
		solidity = new Range();
		solidity.lower = 0;
		solidity.upper = 100;
		maxVertices = 1000000;
		minVertices = 0;
		minRatio = 0;
		maxRatio = 1000;
	}
	
	public int getMinArea() {
		return this.minArea;
	}

	public void setMinArea(int minArea) {
		this.minArea = minArea;
	}


	public int getMinPerimeter() {
	return this.minPerimeter;
	}

	public void setMinPerimeter(int minPerimeter) {
		this.minPerimeter = minPerimeter;
	}
	
	public int getMinWidth() {
		return this.minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}
	
	public int getMaxWidth() {
	return this.maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}
	
	public int getMinHeight() {
		return this.minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}
	
	public int getMaxHeight() {
		return this.maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getMaxVertices() {
		return this.maxVertices;
	}

	public void setMaxVertices(int maxVertices) {
		this.maxVertices = maxVertices;
	}
	
	public int getMinVertices() {
		return this.minVertices;
	}

	public void setMinVertices(int minVertices) {
		this.minVertices = minVertices;
	}
	
	public int getMinRatio() {
		return this.minRatio;
	}

	public void setMinRatio(int minRatio) {
		this.minRatio = minRatio;
	}
	
	public int getMaxRatio() {
		return this.maxRatio;
	}

	public void setMaxRatio(int maxRatio) {
		this.maxRatio = maxRatio;
	}
	
	public Range getSolidity(){
		return this.solidity;
	}
	
	public void setSolidity(Range solidity){
		this.solidity = solidity;
	}
}