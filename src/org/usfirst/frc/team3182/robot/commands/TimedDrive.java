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
	public TimedDrive(long ms, double speed)  {
		requires(Robot.drivetrain);
		this.ms = ms;
		this.speed = speed;
	}
	
	protected void initialize() {
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
		// TODO: Gyro feedback loop
		double theta = Robot.drivetrain.getGyroAngle();
		double omega = Robot.drivetrain.getGyroRate();

		double corrector =  1+ .15 * theta + .05 * omega; //actually just a makeshift PD controller.  TODO tuning
		Robot.drivetrain.driveRaw(-speed * corrector, -speed / corrector);
		
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
