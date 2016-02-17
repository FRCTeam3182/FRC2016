
package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Complete and tested with demo-bot
 */
public class DriveControl extends Command {
	double driveX_old = 0, driveY_old = 0;
    public DriveControl() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() { //TODO check out low pass filtering; driveX = (driveX - driveX_old) * coeff + driveX_old
    	
    	double driveX_new = -Robot.oi.getY() + Robot.oi.getLeft();
    	if(driveX_new > 1.00) driveX_new = 1.00;
    	else if(driveX_new < -1.0) driveX_new = -1.0;
    	
    	double driveY_new = -Robot.oi.getY() + Robot.oi.getRight();
    	if(driveY_new > 1.0) driveY_new = 1.0;
    	else if(driveY_new < -1.0) driveY_new = -1.0;
    	
    	double driveX = (driveX_new - driveX_old) * 0.1 + driveX_old;
    	double driveY = (driveY_new - driveY_old) * 0.1 + driveY_old;
    	
    	driveX_old = driveX;
    	driveY_old = driveY;
    	
    	Robot.drivetrain.drive(driveX, driveY);
    }


    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveX_old = 0;
    	driveY_old = 0;
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
