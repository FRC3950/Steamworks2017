package org.usfirst.frc.team3950.robot;

//import com.kauailabs.navx.frc.*;
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
    public static TalonSRX leftFrontDriveMotor = new TalonSRX(0);
    public static TalonSRX leftBackDriveMotor = new TalonSRX(1);
    public static TalonSRX rightFrontDriveMotor = new TalonSRX(2);
    public static TalonSRX rightBackDriveMotor = new TalonSRX(3);
    public static Solenoid driveGearShiftSolenoid = new Solenoid(0);
   // public static AHRS ahrs = new AHRS(SerialPort.Port.kMXP);
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
