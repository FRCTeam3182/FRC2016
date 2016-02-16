package org.usfirst.frc.team3182.robot.subsystems;

import org.usfirst.frc.team3182.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3182.robot.commands.InfraredControl;

/**
 * Complete but needs testing
 */

public class Collector extends Subsystem {

	Talon collectorMotor = new Talon(RobotMap.collectorMotor);
	AnalogInput irBallSensor;// = new AnalogInput(RobotMap.irBallSensor);
	
	AnalogInput ir1;// = new AnalogInput(RobotMap.leftTriangleIR); // Check channel input
	AnalogInput ir2;// = new AnalogInput(RobotMap.rightTriangleIR);
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
		//setDefaultCommand(new CollectorControl());
	}
	
	public void collect(double speed) {
		collectorMotor.set(speed);
	}
	
	public boolean isInCollector() {
		return false;//irBallSensor.getVoltage() < InfraredControl.inToVoltage(5); // TODO Check length to end of frame
	}
	
	public void expel(double speed) {
		collectorMotor.set(-speed);
	}
	
	public void stop() {
		collectorMotor.set(0);		
	}
	
	public AnalogInput[] getExternalIRArray() {
		return new AnalogInput[]{ir1, ir2};
	}

	
	

}
