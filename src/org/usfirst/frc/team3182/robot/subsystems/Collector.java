package org.usfirst.frc.team3182.robot.subsystems;

import org.usfirst.frc.team3182.robot.RobotMap;
import org.usfirst.frc.team3182.robot.commands.CollectorControl;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * Complete but needs testing
 */

public class Collector extends Subsystem {

	Talon collectorMotor = new Talon(RobotMap.collectorMotor);
	DigitalInput irBallSensor = new DigitalInput(RobotMap.irBallSensor);
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
		setDefaultCommand(new CollectorControl());
	}
	
	public void collect(double speed) {
		collectorMotor.set(speed);
	}
	
	public boolean isInRange() {
        return irBallSensor.get();
	}
	
	public void expel(double speed) {
		collectorMotor.set(-speed);
	}
	
	public void stop() {
		collectorMotor.set(0);		
	}
	

}
