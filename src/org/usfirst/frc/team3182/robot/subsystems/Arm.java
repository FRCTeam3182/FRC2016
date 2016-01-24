package org.usfirst.frc.team3182.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3182.robot.RobotMap;
import org.usfirst.frc.team3182.robot.commands.ArmControl;


public class Arm extends Subsystem {
	
	Talon armMotor = new Talon(RobotMap.armMotor);
	//SomeSensor sensor = new Sensor(RobotMap.armSensor);
	
	public void initDefaultCommand() {
		this.setDefaultCommand(new ArmControl());
    }
	
	public void raise() {
		setSpeed(0.3);
	}
	public void lower() {
		setSpeed(-0.3);
	}
	public void setSpeed(double speed) {
		armMotor.set(speed);
	}
	
	public void setToHeight(double height) {
		double currHeight = this.getHeight();
		while(currHeight != height) {
			if(height < currHeight) lower();
			if(height > currHeight) raise();
			if(Math.abs(height - currHeight) < 0.05) break;
		}
		stop();
	}
	
	public double getHeight() {
		return 1.0;
	}
	
	public void stop() {
		this.setSpeed(0);
	}

}
