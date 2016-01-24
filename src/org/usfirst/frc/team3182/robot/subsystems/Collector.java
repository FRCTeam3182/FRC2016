package org.usfirst.frc.team3182.robot.subsystems;

import org.usfirst.frc.team3182.robot.RobotMap;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Collector extends Subsystem {

	Talon collectorMotor = new Talon(RobotMap.collectorMotor);
	DigitalInput limitSwitch = new DigitalInput(RobotMap.collectorLimitSwitch);
	Counter counter = new Counter(limitSwitch);
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	
	public void collect() {
		collectorMotor.set(0.7654321);
	}
	
	public void initializeCounter() {
		counter.reset();
	}
	
	public boolean isSwitchSet() {
        return counter.get() > 0;
	}
	
	public void expel() {
		collectorMotor.set(-1.0);
	}
	
	public void stop() {
		collectorMotor.set(0);		
	}
	

}
