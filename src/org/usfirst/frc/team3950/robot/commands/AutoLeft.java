package org.usfirst.frc.team3950.robot.commands;

import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.RobotMap;

public class AutoLeft extends AutoDriveTimeFSMCommand {

	protected void initialize() {
		super.initialize();
		this.driveStraight1_useNavX = false;
		this.driveStraight1_driveVoltage = -.75;
		this.driveStraight1_forHowLong = 1900;
		this.rotate_twistVoltage = .7;
		this.rotate_targetAngle = 30;
		this.driveStraight2_driveVoltage = -.75;
		this.driveStraight2_forHowLong = 1900;
		this.driveStraight2_useNavX = false;
		this.reverse_driveVoltage = .75;
		this.reverse_forHowLong = 500;
		this.reverse_useNavX = false;
	}
}
