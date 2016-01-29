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
		Robot.drivetrain.drive(0.3);
	}

	@Override
	protected void execute() {
		Robot.drivetrain.drive(0.3);
	}

	@Override
	protected boolean isFinished() {
		return Robot.drivetrain.getDistance() > 1;
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		end();

	}

}
