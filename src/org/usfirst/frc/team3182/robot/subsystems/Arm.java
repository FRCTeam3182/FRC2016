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
	
	Talon armMotor = new Talon(RobotMap.armMotor);
	AnalogPotentiometer pmeter = new AnalogPotentiometer(RobotMap.armPotentiometer);
	double initAngle;
	PIDController armControl;
	
	public Arm() {
		System.out.println("Arm init");
		armControl = new PIDController(.1, 0, 0, pmeter, armMotor); //PID needs tuning
	}
	public void initDefaultCommand() {
		//this.setDefaultCommand(new ArmControl());
    }
	
	public void raise() { //values need tuning
		set(60);
	}
	public void lower() {
		set(0);
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
//		armMotor.set(0);
	}

}
