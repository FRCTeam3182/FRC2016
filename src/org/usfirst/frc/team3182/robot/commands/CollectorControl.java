package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class CollectorControl extends Command {

	
	private double power = 0;
	private boolean constant;
	public CollectorControl(double power) {
        requires(Robot.collector);
       // if(power<0 && power>-1)this.power-=.05;
        this.power = power;
        constant = true;
    }
	
	public CollectorControl() {
		requires(Robot.collector);
		power = Robot.oi.getCollectValue();
		constant = false;
		
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		if(!constant) {
    			this.power = Robot.oi.getCollectValue();
    		}
            Robot.collector.collect(power);
            
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.collector.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
