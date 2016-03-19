
package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;
import org.usfirst.frc.team3182.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Complete, needs tuning and testing
 */
public class LowerArm extends Command {

	boolean isFinished = false;
	private long startTime;
    public LowerArm() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("Executed LowerArm");
    	Robot.arm.runRaw(0.24);
    	if (System.currentTimeMillis() - startTime >= 200){
    		double angle = Robot.arm.getPmeter().getVoltage();
    		System.out.println(angle);
    		if (angle <= Arm.potentiometerLowerLim){
    			Robot.arm.runRaw(0);
    			isFinished = true;
    			return;
    		}
    	}
    	else{
    		startTime = System.currentTimeMillis();
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.arm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
