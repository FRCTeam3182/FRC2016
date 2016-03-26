
package org.usfirst.frc.team3182.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3182.robot.Robot;

/**
 * Complete and tested with demo-bot
 */
public class DriveControl extends Command {
	private double driveL_old = 0, driveR_old = 0;
    public DriveControl() {
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	driveL_old = 0;
    	driveR_old = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() { //low pass filtering; driveX = (driveX - driveX_old) * coeff + driveX_old
    	
    	double driveL_new = Robot.oi.getLExp();
    	if(driveL_new > 1.00) driveL_new = 1.00;
    	else if(driveL_new < -1.0) driveL_new = -1.0;
    	
    	double driveR_new = Robot.oi.getRExp();
    	if(driveR_new > 1.0) driveR_new = 1.0;
    	else if(driveR_new < -1.0) driveR_new = -1.0;
    	
    	double driveL = (driveL_new - driveL_old) * 0.7 + driveL_old; //TODO: Test coeff
    	double driveR = (driveR_new - driveR_old) * 0.7 + driveR_old;
    	
    	driveL_old = driveL;
    	driveR_old = driveR;
    	
    	Robot.drivetrain.driveRaw(driveL, driveR);
    	
    	SmartDashboard.putNumber("teleopDriveX", driveL);
    	SmartDashboard.putNumber("teleopDriveY", driveR);
    }


    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveL_old = 0;
    	driveR_old = 0;
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
