package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

public class TimedTurn extends TimedDrive {

	public TimedTurn(long ms, double speed) {
		super(ms, speed);
	}
	protected void execute() { //turns right
		Robot.drivetrain.driveRaw(-speed, speed );
	}

}
