package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoDrive extends Command {

	public AutoDrive() {
		requires(Robot.arm);
		requires(Robot.drivetrain);
	}
	@Override
	protected void initialize() {
		Robot.drivetrain.reset();
		Robot.arm.setToAngle(0);
		Robot.drivetrain.drive(1);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return Robot.drivetrain.getDistanceTraveled() > 3;
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
