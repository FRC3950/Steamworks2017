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
    public static CANTalon leftFrontDriveMotor = new CANTalon(0);
    public static CANTalon leftBackDriveMotor = new CANTalon(1);
    public static CANTalon rightFrontDriveMotor = new CANTalon(2);
    public static CANTalon rightBackDriveMotor = new CANTalon(3);
    public static CANTalon ballIntakeMotor = new CANTalon(4);
    public static CANTalon gearIntakeMotor = new CANTalon(5);
    public static CANTalon shooterIndexerMotor = new CANTalon(6);
    public static CANTalon shooterMotor = new CANTalon(7);
    public static CANTalon climberMotor = new CANTalon(8);
    public static Solenoid driveGearShiftSolenoid = new Solenoid(0);
    public static Solenoid gearIntakeSolenoid1 = new Solenoid(1);
    public static Solenoid gearIntakeSolenoid2 = new Solenoid(2);
    public static Solenoid shooterAngleShiftSolenoid = new Solenoid(3);
    public static DigitalInput shooterIndexerSwitch = new DigitalInput(0);
    public static DigitalInput gearBumperSwitch = new DigitalInput(1);
    public static AHRS ahrs = new AHRS(SerialPort.Port.kMXP);
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
