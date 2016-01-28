package org.usfirst.frc.team3182.robot.subsystems;

import org.usfirst.frc.team3182.robot.RobotMap;

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
	DigitalInput limitSwitch = new DigitalInput(RobotMap.collectorLimitSwitch);
	Counter counter = new Counter(limitSwitch);
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	
	public void collect(double speed) {
		collectorMotor.set(speed);
	}
	
	public void initializeCounter() {
		counter.reset();
	}
	
	public boolean isSwitchSet() {
        return counter.get() > 0;
	}
	
	public void expel(double speed) {
		collectorMotor.set(-speed);
	}
	
	public void stop() {
		collectorMotor.set(0);		
	}
	
	/** Returns true if ball is in front of the collector */
	public boolean inRange() {
		return irBallSensor.get();
	}
}
