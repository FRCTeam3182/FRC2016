package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoDriveForward extends Command {

	public AutoDriveForward() {
		requires(Robot.arm);
		requires(Robot.drivetrain);
	}
	@Override
	protected void initialize() {
		System.out.println("Starting to drive");
		Robot.drivetrain.reset();
		//Robot.arm.setToAngle(0);
		
	}

	@Override
	protected void execute() {
		Robot.drivetrain.drive(0.3);
		
	}

	@Override
	protected boolean isFinished() {
		return Robot.drivetrain.getDistance() > 1;
		//return false;
	}

	@Override
	protected void end() {
		System.out.println(Robot.drivetrain.getDistance());
		Robot.drivetrain.stop();

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
