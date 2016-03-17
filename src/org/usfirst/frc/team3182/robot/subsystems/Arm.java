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
	/*
	 * alright so the PID doesn't work
	 * the problem is in the pot or in the PID
	 * test by printing out armControl.get() to see if it's actually outputting anything
	 * if this doesn't work mess with the PID to make sure everything is enabled (it should be)
	 * if this doesn't work copy and paste everything relevant from drivetrain
	 * if this doesn't work write your own feedback loop (it's not hard to write out a shitty PI controller,
	 * 		you don't need a D for this application
	 * if that doesn't work tell me and i'll write my own feedback loop
	 * if that doesn't work we'll go back to normal joystick control but with the powerglove
	 * 
	 * also maybe test the motor
	 * and possibly check armControl.getSetpoint() but i don't think that's the issue
	 */
	
	Talon armMotor = new Talon(RobotMap.armMotor);
	AnalogInput pmeter = new AnalogInput(RobotMap.armPotentiometer);
	AnalogPotentiometer potentiometer = new AnalogPotentiometer(pmeter, 737.7, 164.06);
	double initAngle;
	PIDController armControl;	
	public static final double potentiometerLowerLim = 0.600; //TODO change this to be lower
	public static final double potentiometerUpperLim = 2; 
	
	
	public Arm() {
		System.out.println("Arm init");
		armControl = new PIDController(.1, .001, 0, pmeter, armMotor); //PID needs tuning
		LiveWindow.addSensor("Arm", "Potentiometer(AI)", pmeter);
		LiveWindow.addSensor("Arm", "Potentiometer(AP)", potentiometer);
		LiveWindow.addActuator("Arm", "Arm Motor", armMotor);
		SmartDashboard.putNumber("ArmPot", pmeter.getVoltage());
		SmartDashboard.putNumber("ArmPotentiometer", potentiometer.get());
		armControl.setContinuous(false);
		
	
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
	//don't be dumb and set this too low or high
	public void set(double theta) {
		System.out.println("Setting: "+theta + " "+armControl.get());
		armControl.enable();
		armControl.setSetpoint(theta);
		
	}
	
	public double getAngle() {
		return potentiometer.get(); 
		//return 0;
	}
	
	public void stop() {
		armMotor.set(0);
		armControl.disable();
	}

}
