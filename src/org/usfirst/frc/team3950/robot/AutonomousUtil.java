package org.usfirst.frc.team3950.robot;

import org.usfirst.frc.team3950.robot.subsystems.DrivetrainSubsystem;

public class AutonomousUtil {

	private static RobotLogger logger = new RobotLogger(AutonomousUtil.class);
	
	public enum ProfileType {
		Linear,
		Trigonometric
	};
	
	public static double VoltageProfile(double currentDistance, double totDistance, double maxVoltage, ProfileType profileType, double voltageInit)
	{
		double voltage = 0.0;

		if(currentDistance == totDistance) {
			voltage = voltageInit;
			logger.log(RobotLogger.LoggerLevel.debug, "Voltage: " + voltage);
		}
		
		else {
			if(profileType == ProfileType.Linear)
			{
				double slope = -maxVoltage / (totDistance / 2.0);
				logger.log(RobotLogger.LoggerLevel.debug, "Slope: " + slope);
				double intercept = 2 * maxVoltage;
				logger.log(RobotLogger.LoggerLevel.debug, "Intercept: " + intercept);
				
				if(currentDistance <= totDistance / 2.0)
				{
					slope = -slope;
					logger.log(RobotLogger.LoggerLevel.debug, "Slope Neg: " + slope);
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
