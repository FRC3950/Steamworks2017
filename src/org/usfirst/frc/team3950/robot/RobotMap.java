package org.usfirst.frc.team3950.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.*;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    public static Victor leftFrontDriveMotor = new Victor(0);
    public static Victor leftBackDriveMotor = new Victor(1);
    public static Victor rightFrontDriveMotor = new Victor(2);
    public static Victor rightBackDriveMotor = new Victor(3);
    public static Solenoid driveGearShiftSolenoid = new Solenoid(0);
    public static CANTalon shooterMotor = new CANTalon(0);
    public static AHRS ahrs = new AHRS(SerialPort.Port.kMXP);
    public static DigitalInput shooterIndexerSwitch = new DigitalInput(0);
    public static CANTalon shooterIndexerMotor = new CANTalon(0);
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
