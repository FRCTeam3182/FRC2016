
package org.usfirst.frc.team3182.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3182.robot.Robot;
import org.usfirst.frc.team3182.robot.RobotMap;

/**
 * Complete and tested with demo-bot
 */
public class DriveControl extends Command {
	private double driveL_old = 0, driveR_old = 0;
	private double driveF_old=0;
	private double driveSide_old=0;
    
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
        // If we are in tank mode
    	if(!RobotMap.isArcade){
    	    //Max range for driveL_new is -1.00 and 1.00
	    	double driveL_new = Robot.oi.getL();
	    	if(driveL_new > 1.00) driveL_new = 1.00;
	    	else if(driveL_new < -1.0) driveL_new = -1.0;
	    	//Same as driveR_new
	    	double driveR_new = Robot.oi.getR();
	    	if(driveR_new > 1.0) driveR_new = 1.0;
	    	else if(driveR_new < -1.0) driveR_new = -1.0;
	    	
	    	// [PB, 2016-06-11, 09:18]: modified the low-pass filter factor from 0.03 to 0.3
	    	double driveL = (driveL_new - driveL_old) * 0.12 + driveL_old; //TODO: Test coeff
	    	double driveR = (driveR_new - driveR_old) * 0.12 + driveR_old;
	    	//Updates old values
	    	driveL_old = driveL;
	    	driveR_old = driveR;
	    	//Gives drivetrain driveRaw() the values of the right and left
	    	Robot.drivetrain.driveRaw(driveL, driveR);
	    	
	    	//Puts on the smart dash driveL and driveR
	    	SmartDashboard.putNumber("teleopDriveX", driveL);
	    	SmartDashboard.putNumber("teleopDriveY", driveR);
    	}
    	else{
    		double driveF_new = Robot.oi.getForwardArcade();
	    	double driveSide_new = Robot.oi.getSideArcade();
	    	
	    	double driveF = (driveF_new - driveF_old) * 0.075 + driveF_old; //TODO: Test coeff
	    	double driveSide = (driveSide_new - driveSide_old) * 0.5 + driveSide_old;
	    	
	    	driveF_old = driveF;
	    	driveSide_old = driveSide;
	    	
	    	Robot.drivetrain.driveArcade(driveF, driveSide);
    	}
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
