package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

public class DriveToAngle extends DriveToDistance {
	public DriveToAngle(double theta) {
		super(theta);
	}
	
	public void execute() {
		super.t = (int)(System.currentTimeMillis()-timeStart);
		Robot.drivetrain.updateD2A(t);
		Robot.drivetrain.updatePID();
	}
}
