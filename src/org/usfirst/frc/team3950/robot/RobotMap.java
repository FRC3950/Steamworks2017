package org.usfirst.frc.team3950.robot;

import org.usfirst.frc.team3950.robot.subsystems.USBCameraSubsystem;

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
    public static CANTalon ballIntakeMotor = new CANTalon(12);
    public static CANTalon gearIntakeMotor = new CANTalon(14);
    public static CANTalon shooterIndexerMotor = new CANTalon(10);
    public static CANTalon shooterMotor = new CANTalon(13);
    public static CANTalon climberMotor = new CANTalon(15);
    public static CANTalon agitatorMotor = new CANTalon(11);
    public static Solenoid driveGearShiftSolenoid = new Solenoid(0);
    public static Solenoid gearIntakeSolenoid1 = new Solenoid(1);
    public static Solenoid gearIntakeSolenoid2 = new Solenoid(2);
    public static DoubleSolenoid gearIntakeDoubleSolenoid1 = new DoubleSolenoid(3, 4);
    public static DoubleSolenoid gearIntakeDoubleSolenoid2 = new DoubleSolenoid(5, 6);
    public static DigitalInput shooterIndexerSwitch = new DigitalInput(0);
    public static DigitalInput gearBumperSwitch = new DigitalInput(1);
    public static AHRS ahrs = new AHRS(SerialPort.Port.kMXP);
//    public static USBCameraSubsystem usbCameraSubsystem = new USBCameraSubsystem(); 
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}