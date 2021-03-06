package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class DriveToDistance extends Command {
	private double distance;
	protected long timeStart;
	public int t;
	
	public DriveToDistance(double distance) {
		requires(Robot.drivetrain);
		this.distance = distance;
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
		Robot.drivetrain.driveToDistancePID(distance);
	}
	
	@Override
	protected void execute() {
		Robot.drivetrain.updatePID();
	}

	@Override
	protected boolean isFinished() {
		return Robot.drivetrain.getDistance() - distance < 0.5;
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
		