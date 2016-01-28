package org.usfirst.frc.team3182.robot.subsystems;

import org.usfirst.frc.team3182.robot.RobotMap;
import org.usfirst.frc.team3182.robot.commands.DriveControl;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * Complete except for encoder stuff
 * Tested with demo-bot
 */

public class Drivetrain extends Subsystem {
	
	Talon[] wheels;
	Talon[] leftWheels;
	Talon[] rightWheels;
	Encoder rightEncoder, leftEncoder;
	
	public Drivetrain() {
		leftWheels = new Talon[RobotMap.leftWheels.length];
		rightWheels = new Talon[RobotMap.rightWheels.length];
		wheels = new Talon[leftWheels.length + rightWheels.length];
		
		for(int i = 0; i <  RobotMap.leftWheels.length; i++) {
			leftWheels[i] = new Talon(RobotMap.leftWheels[i]);
			wheels[i] = leftWheels[i];
		}
		
		for(int i = 0; i <  RobotMap.rightWheels.length; i++) {
			rightWheels[i] = new Talon(RobotMap.rightWheels[i]);
			wheels[i+RobotMap.leftWheels.length] = rightWheels[i];
			wheels[i].setInverted(true);
		}
		
		rightEncoder = new Encoder(RobotMap.rightEncoder_A, RobotMap.rightEncoder_B);
		leftEncoder = new Encoder(RobotMap.leftEncoder_A, RobotMap.leftEncoder_B, true);
	}
	
	public void reset() {
		rightEncoder.reset();
		leftEncoder.reset();
	}
	
	//TODO set encoder distance
	public double getDistanceTraveled() {
		return (rightEncoder.getDistance() + leftEncoder.getDistance()) / 2; //* multiplier
	}
	
	public void initDefaultCommand() {
		this.setDefaultCommand(new DriveControl());
	}
	
	public void drive(double speed) { //some might need to be reversed
		for (Talon w : wheels) {
			w.set(speed);
		}
	}
	
	public void stop() {
		drive(0);
	}
		
	public void drive(double speedL, double speedR) {
	   for(Talon l : leftWheels) {
		   l.set(speedL);
	   }
	   for(Talon r : rightWheels) {
		   r.set(speedR);
	   }
	}
}
