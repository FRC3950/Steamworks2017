package org.usfirst.frc.team3950.robot;

public class AutonomousUtil {

	public enum ProfileType {
		Linear,
		Trigonometric
	};
	
	public static double VoltageProfile(double currentDistance, double totDistance, double maxVoltage, ProfileType profileType, double voltageInit)
	{
		double voltage = 0.0;

		if(currentDistance == totDistance)
			voltage = voltageInit;
		else {
			if(profileType == ProfileType.Linear)
			{
				double slope = -maxVoltage / (totDistance / 2.0);
				double intercept = 2 * maxVoltage;
				
				if(currentDistance <= totDistance / 2.0)
				{
					slope = -slope;
					intercept = 0;
				}
				voltage = slope * currentDistance + intercept;
			}
			else if(profileType == ProfileType.Trigonometric)
				voltage = Math.sin(Math.PI * (totDistance - currentDistance) / totDistance);
		}
		
		return voltage;
	}
}
