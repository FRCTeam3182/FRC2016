package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * To get through the portcullis in teleop or autonomous
 * Need to tune angles and stuff
 */

public class AutoPortcullis extends Command {

	public AutoPortcullis() {
		requires(Robot.arm);
		requires(Robot.drivetrain);
	}
	@Override
	protected void initialize() {
		Robot.arm.setToAngle(-10); //10 degrees down (hopefully)  TODO set this angle
		Robot.drivetrain.stop();
		Robot.drivetrain.reset();

	}

	@Override
	protected void execute() {
		if(Robot.arm.getAngle() < -5) {
			Robot.drivetrain.drive(0.5);
			if(Robot.drivetrain.getDistanceTraveled() > 1.5) {
				Robot.arm.raise();
			}
		}
		else Robot.arm.setToAngle(-10);

	}

	@Override
	//TODO:  Encoder stuff
	protected boolean isFinished() {
		return Robot.drivetrain.getDistanceTraveled() > 3; //Hopefully less than 3 meters.
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();
		Robot.arm.setToAngle(0);

	}

	@Override
	protected void interrupted() {

	}

}
