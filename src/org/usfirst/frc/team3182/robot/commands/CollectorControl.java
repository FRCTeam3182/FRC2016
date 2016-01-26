package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Complete, but needs tuning and testings
 *
 */

public class CollectorControl extends Command {
	
	public CollectorControl() {
		requires(Robot.collector);
	}

	@Override
	protected void initialize() {
		Robot.collector.stop();
	}

	@Override
	protected void execute() {
		Robot.collector.collect(Robot.oi.getPowerGloveClench());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.collector.stop();
	}

	@Override
	protected void interrupted() {
		Robot.collector.stop();
	}

}
