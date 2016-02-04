
package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Complete and tested with demo-bot
 */
public class DriveControl extends Command {

    public DriveControl() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double driveX = -Robot.oi.getY() + Robot.oi.getLeft();
    	if(driveX > 1.00) driveX = 1.00;
    	else if(driveX < -1.0) driveX = -1.0;
    	
    	double driveY = -Robot.oi.getY() + Robot.oi.getRight();
    	if(driveY > 1.0) driveY = 1.0;
    	else if(driveY < -1.0) driveY = -1.0;
    	
    	
    	Robot.drivetrain.drive(driveX, driveY);
    }


    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
