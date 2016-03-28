package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

public class TimedVariableDrive extends TimedDrive {

	private Robot robot;
	public TimedVariableDrive(Robot robot) {		
		super(robot.getDSms(), robot.getDSspeed());
		this.robot = robot;
	}
	
	protected void initialize() {
		this.ms = robot.getDSms();
		this.speed = robot.getDSspeed();
		super.initialize();
		}

}
