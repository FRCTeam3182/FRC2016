package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToAngle extends Command {
	private double arclength;
	
	public DriveToAngle(double theta) {
		requires(Robot.drivetrain);
		arclength = theta * Math.PI / 180 * 12.225; //degrees to radians to arclength
	}
	
//	@Override
//	protected void initialize() {
//		Robot.drivetrain.reset();
//		Robot.drivetrain.enablePID();
//		Robot.drivetrain.initD2D(distance);
//		timeStart = System.currentTimeMillis();
//
//	}


	
//	@Override
//	protected void execute() {
//		t = (int)(System.currentTimeMillis()-timeStart);
//		Robot.drivetrain.updateD2D(t);
//		Robot.drivetrain.updatePID();
//		
//	}
	
	@Override
	protected void initialize() {
		Robot.drivetrain.reset();
		Robot.drivetrain.enablePID();
		Robot.drivetrain.driveToAnglePID(arclength);
	}
	
	@Override
	protected void execute() {
		Robot.drivetrain.updatePID();
	}

	@Override
	protected boolean isFinished() {
		return Robot.drivetrain.getPosL() - arclength < 0.25 && Robot.drivetrain.getPosR() + arclength > -0.25;
	}

	@Override
	protected void end() {
		Robot.drivetrain.disablePID();
		Robot.drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
		