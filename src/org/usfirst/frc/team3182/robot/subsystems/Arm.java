package org.usfirst.frc.team3182.robot.subsystems;

import org.usfirst.frc.team3182.robot.RobotMap;
import org.usfirst.frc.team3182.robot.commands.ArmControl;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
/**
 * Complete but needs tuning and testing
 * 
 */

public class Arm extends Subsystem {
	
//	Talon armMotor = new Talon(RobotMap.armMotor);
//	AnalogPotentiometer pmeter = new AnalogPotentiometer(RobotMap.armPotentiometer);
	double initAngle;
//	PIDController armControl;
	
	public Arm() {
		System.out.println("Arm init");
//		armControl = new PIDController(.1, 0, 0, pmeter, armMotor); //PID needs tuning
	}
	public void initDefaultCommand() {
		this.setDefaultCommand(new ArmControl());
    }
	
	public void raise() { //values need tuning
		setToAngle(60);
	}
	public void lower() {
		setToAngle(0);
	}
	
	
	public void setToAngle(double theta) { //vestigial method
		double currTheta = this.getAngle();
		while(currTheta != theta) {
			if(theta < currTheta) lower();
			if(theta > currTheta) raise();
			if(Math.abs(theta - currTheta) < 0.05) break;
		}
		stop();
	}
	
	public void set(double theta) {
//		armControl.setSetpoint(theta);
	}
	
	public double getAngle() {
//		return pmeter.get(); TODO change this back
		return 0;
	}
	
	public void stop() {
//		armMotor.set(0);
	}

}
