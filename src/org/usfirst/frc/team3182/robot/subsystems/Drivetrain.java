package org.usfirst.frc.team3182.robot.subsystems;

import org.usfirst.frc.team3182.robot.RobotMap;
import org.usfirst.frc.team3182.robot.commands.DriveControl;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Drivetrain extends Subsystem {
	private Talon leftWheel;
	private Talon rightWheel;
	
	public Drivetrain() {
		leftWheel = new Talon(RobotMap.leftWheel);
		rightWheel = new Talon(RobotMap.rightWheel);
	}
	
	public void initDefaultCommand() {
		this.setDefaultCommand(new DriveControl());
	}
	
	public void drive(double speed) { //some might need to be reversed
		leftWheel.set(speed);
		rightWheel.set(speed);
	}
		
	public void drive(double speedL, double speedR) {
	   leftWheel.set(speedL);
	   rightWheel.set(speedR);
	}
	
	public void stop() {
		drive(0);
	}
}


