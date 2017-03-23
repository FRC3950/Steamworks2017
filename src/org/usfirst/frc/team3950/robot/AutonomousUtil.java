package org.usfirst.frc.team3950.robot;

import org.usfirst.frc.team3950.robot.commands.AutoDriveCommand;
import org.usfirst.frc.team3950.robot.subsystems.DrivetrainSubsystem;

public class AutonomousUtil {
	private static RobotLogger logger = new RobotLogger(AutonomousUtil.class);

    public enum ProfileType {
        Linear,
        Trigonometric
    };
    
    public static double VoltageProfile1(double currentDistance, double totDistance, double maxVoltage, ProfileType profileType, double minVoltage, double transitionDistance)
    {
        double voltage = maxVoltage;

        if(currentDistance <= transitionDistance) {
        	voltage = minVoltage; //(minVoltage / transitionDistance) * currentDistance;
        	voltage = Math.max(voltage, minVoltage);
        }
        return Math.min(voltage,  maxVoltage);
    }

    public static double VoltageProfile(double currentDistance, double totDistance, double maxVoltage, ProfileType profileType, double minVoltage, double transitionDistance)
    {
        double voltage = 0.0;

        // if Linear voltage = mx + b else voltage = sin(...)
        if(profileType == ProfileType.Linear)
        {
            double slope = 0.0;
            double intercept = 0.0;

            if(currentDistance <= transitionDistance) {
                intercept = 0;
                slope = (0 - maxVoltage) / (0 - transitionDistance);
                
            } else {
                slope = (0 - maxVoltage) / (totDistance - transitionDistance);
                intercept = slope * -totDistance;
            }
            voltage = slope * currentDistance + intercept;
        } else if(profileType == ProfileType.Trigonometric)
            voltage = Math.sin(Math.PI * (totDistance - currentDistance) / totDistance);

        if(currentDistance > transitionDistance) {
            voltage = Math.max(voltage, minVoltage);
        }
        voltage = Math.min(voltage, maxVoltage);

        return voltage;
    }
}