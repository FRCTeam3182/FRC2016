package org.usfirst.frc.team3182.robot.commands;
import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
public class AutoCheval extends Command {
	
	public AutoCheval(int position) {
		requires(Robot.arm);
		requires(Robot.drivetrain);
		
	}
	
	@Override
	protected void initialize() {
		Robot.drivetrain.reset();
		Robot.arm.set(30);
	}

	@Override
	protected void execute() {
		if(Robot.arm.getAngle() <= 30) {
			Robot.drivetrain.drive(.5);
			if(Robot.drivetrain.getDistance() > 1) {
				Robot.arm.set(-5);
				if(Robot.arm.getAngle() < 0) {
					Robot.drivetrain.drive(.2);
				}
			}
		}
		else Robot.arm.set(30);

	}

	@Override
	protected boolean isFinished() {
		return Robot.drivetrain.getDistance() < 3;
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();
	}

	@Override
	protected void interrupted() {}
}
