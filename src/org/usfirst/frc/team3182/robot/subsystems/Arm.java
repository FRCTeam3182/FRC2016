package org.usfirst.frc.team3182.robot.subsystems;

import org.usfirst.frc.team3182.robot.RobotMap;
import org.usfirst.frc.team3182.robot.commands.RaiseArm;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDController;
/**
 * Complete but needs tuning and testing
 * 
 */

public class Arm extends Subsystem {
	
	Talon armMotor = new Talon(RobotMap.armMotor);
	AnalogInput pmeter = new AnalogInput(RobotMap.armPotentiometer);
	double initAngle;
	PIDController armControl;
	
	public Arm() {
		System.out.println("Arm init");
		//armControl = new PIDController(.1, 0, 0, pmeter, armMotor); //PID needs tuning
	}
	public void initDefaultCommand() {
		//this.setDefaultCommand(new ArmControl());
    }
	
	public AnalogInput getPmeter(){
		return pmeter;
	}
	
	public void runRaw(double speed) {
		armMotor.set(speed);
	}
	
	public void set(double theta) {
//		armControl.setSetpoint(theta);
		
	}
	
	public double getAngle() {
//		return pmeter.get(); TODO change this back
		return 0;
	}
	
	public void stop() {
		armMotor.set(0);
	}

}
