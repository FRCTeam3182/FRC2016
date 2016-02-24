package org.usfirst.frc.team3182.robot.subsystems;

import org.usfirst.frc.team3182.robot.RobotMap;
import org.usfirst.frc.team3182.robot.commands.ArmControl;
import org.usfirst.frc.team3182.robot.commands.RaiseArm;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	AnalogPotentiometer potentiometer = new AnalogPotentiometer(pmeter, 737.7, 164.06);
	double initAngle;
	PIDController armControl;
	public static final double potentiometerLowerLim = 0.856; //TODO change this to be lower
	public static final double potentiometerUpperLim = 2; 
	
	
	public Arm() {
		System.out.println("Arm init");
		//armControl = new PIDController(.1, .001, 0, pmeter, armMotor); //PID needs tuning
		LiveWindow.addSensor("Arm", "Potentiometer(AI)", pmeter);
		LiveWindow.addSensor("Arm", "Potentiometer(AP)", potentiometer);
		LiveWindow.addActuator("Arm", "Arm Motor", armMotor);
		SmartDashboard.putNumber("ArmPot", pmeter.getVoltage());
		SmartDashboard.putNumber("ArmPotentiometer", potentiometer.get());
	}
	public void initDefaultCommand() {
		this.setDefaultCommand(new ArmControl());
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
		//return potentiometer.get(); 
		return 0;
	}
	
	public void stop() {
		armMotor.set(0);
	}

}
