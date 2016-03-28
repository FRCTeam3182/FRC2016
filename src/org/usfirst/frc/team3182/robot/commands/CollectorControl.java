package org.usfirst.frc.team3182.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3182.robot.Robot;
import org.usfirst.frc.team3182.robot.util.Animation;


public class CollectorControl extends Command {

    private double lastPower = 0;
    private double power = 0;
	private boolean constant;

	public CollectorControl(double power) {
        requires(Robot.collector);
       // if(power<0 && power>-1)this.power-=.05;
        this.power = power;
        constant = true;
        System.out.println("Begin collect "+power);
        if(power>0)new LightsControl(Animation.COLLECT);
        else if(power<0)new LightsControl(Animation.EXPEL);

        execute();
    }
	
	public CollectorControl() {
        // Constructor for power glove
		requires(Robot.collector);
		power = Robot.oi.getCollectValue();
		constant = false;

		execute();
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		if(!constant) {
    			this.power = Robot.oi.getCollectValue();

                // Lights stuff
                if(lastPower != power){ //detect if the power changed
                    if (power<1) new LightsControl(Animation.EXPEL);
                    else if (power>1) new LightsControl(Animation.COLLECT);
                    else new LightsControl(Animation.DEFAULT);
                }
    		}
            Robot.collector.collect(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("End collect");
        Robot.collector.stop();
        new LightsControl(Animation.DEFAULT);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
