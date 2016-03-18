package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3182.robot.subsystems.Arm;

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
		Robot.arm.set(Arm.potentiometerLowerLim); //set to lower limit
		Robot.drivetrain.stop();
		Robot.drivetrain.reset();

	}

	@Override
	protected void execute() {
		Robot.drivetrain.drive(0.5);
		while(Robot.drivetrain.getDistance() < 4) {}  //FIXME Tuuuneeee distance

		Robot.arm.set(Arm.potentiometerMid);
		Robot.drivetrain.drive(0.3);

	}

	@Override
	protected boolean isFinished() {
		return Robot.drivetrain.getDistance() > 15; //FIXME distance value
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
