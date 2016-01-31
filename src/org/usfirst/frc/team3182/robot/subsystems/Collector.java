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
	DigitalInput limitSwitch = new DigitalInput(RobotMap.collectorLimitSwitch);
	Counter counter = new Counter(limitSwitch);
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
		setDefaultCommand(new CollectorControl());
	}
	
	/** Returns true if ball is in front of the collector */
	public boolean inRange() {
		return irBallSensor.get();
	}
}
