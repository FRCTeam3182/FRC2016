package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.RobotMap;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Incomplete
 */
@SuppressWarnings("unused")
public class AutoCollect extends Command {


	
	long startTime;
	
    public AutoCollect(int position) {
        // Use requires() here to declare subsystem dependencies
//        requires(Robot.drivetrain);
//        requires(Robot.collector);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = System.currentTimeMillis();
//    	collector.collect();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//    	long finalTime = System.currentTimeMillis();
    	
//        return collector.isSwitchSet() || (finalTime - startTime > 2000);
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
//    	collector.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
