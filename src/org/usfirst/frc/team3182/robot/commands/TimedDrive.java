package org.usfirst.frc.team3182.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3182.robot.Robot;

import java.util.Timer;
import java.util.TimerTask;

public class TimedDrive extends Command {

	Timer timer;
	long ms;
	boolean isFinished = false;
	double speed;
	
	final double kP = 0.1;
	final double kD = 0.01;
	
	public TimedDrive(long ms, double speed)  {
		requires(Robot.drivetrain);
		this.ms = ms;
		this.speed = speed;
	}
	
	protected void initialize() {
		Robot.drivetrain.reset();
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				end();
				isFinished = true;
			}
		}, ms);
	}

	@Override
	protected void execute() {
		double theta = Robot.drivetrain.getGyroAngle();
		double omega = Robot.drivetrain.getGyroRate();

		// This is actually pretty damn cool
		//double corrector =  1 + kP * theta + kD * omega; //actually just a makeshift PD controller.  TODO tuning
		Robot.drivetrain.driveRaw(-speed, -speed );
		
		//Robot.drivetrain.driveRaw(-speed, -speed);
	}

	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	@Override
	protected void end() {
		Robot.drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
