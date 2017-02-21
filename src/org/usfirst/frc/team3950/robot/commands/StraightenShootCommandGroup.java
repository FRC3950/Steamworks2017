package org.usfirst.frc.team3950.robot.commands;

import org.usfirst.frc.team3950.robot.Robot;
import org.usfirst.frc.team3950.robot.RobotLogger;
import org.usfirst.frc.team3950.robot.subsystems.AgitatorSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StraightenShootCommandGroup extends CommandGroup {
	private static RobotLogger logger =new RobotLogger(StraightenShootCommandGroup.class);
	ShooterIndexerCommand shooterIndexerCommand;
    public StraightenShootCommandGroup() {
    	requires(Robot.axisCameraSubsystem);
		requires(Robot.drivetrainsubsystem);
		requires(Robot.shooterIndexerSubsystem);
		requires(Robot.shooterSubsystem);
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	//addSequential(new BoilerStraightenCommand());
		addParallel(new AgitatorCommand());
    	addParallel(new ShooterStartCommand());
    	//addParallel(shooterIndexerCommand = new ShooterIndexerCommand());



        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	 
		
    }
    protected void execute() {

    	if (Robot.shooterIndexerSubsystem.detectLimitSwitch()) {
    		if(Robot.shooterSubsystem.speedInRange())
    			shooterIndexerCommand.startShooterIndexer();
    		
     	}
    	else {
			shooterIndexerCommand.stopShooterIndexer();
    	}  	
    }
}
