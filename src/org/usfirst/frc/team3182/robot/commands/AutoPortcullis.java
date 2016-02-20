package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * To get through the portcullis in teleop or autonomous
 * Need to tune angles and stuff
 */

public class AutoPortcullis extends Command {

	public AutoPortcullis(int position) {
		requires(Robot.arm);
		requires(Robot.drivetrain);
	}
	@Override
	protected void initialize() {
		Robot.arm.set(-10); //10 degrees down (hopefully)  TODO set this angle
		Robot.drivetrain.stop();
		Robot.drivetrain.reset();

	}

	@Override
	protected void execute() {
		if(Robot.arm.getAngle() < -5) {
			Robot.drivetrain.drive(0.5);
			if(Robot.drivetrain.getDistance() > 1.5) {
				//Robot.arm.raise();
			}
		}
		else Robot.arm.set(-10);

	}

	@Override
	//TODO:  Encoder stuff
	protected boolean isFinished() {
		return Robot.drivetrain.getDistance() > 3; //Hopefully less than 3 meters.
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();
		Robot.arm.set(0);

	}

	@Override
	protected void interrupted() {

	}

}
