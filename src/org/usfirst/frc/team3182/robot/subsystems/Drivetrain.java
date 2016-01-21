package org.usfirst.frc.team3182.robot.subsystems;

import org.usfirst.frc.team3182.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Drivetrain extends Subsystem {

	Talon w1 = new Talon(RobotMap.wheel1);
	Talon w2 = new Talon(RobotMap.wheel2);
	Talon w3 = new Talon(RobotMap.wheel3);
	Talon w4 = new Talon(RobotMap.wheel4);
	
	Talon[] wheels = {w1, w2, w3, w4};
	Talon[] leftWheels = {w1, w2}; //will be changed eventually
	Talon[] rightWheels = {w3, w4};
	
	public void initDefaultCommand() {
		
	}
	
	public void drive(double speed) { //some might need to be reversed
		for (Talon w : wheels) {
			w.set(speed);
		}
	}
	
	public void stop() {
		drive(0);
	}
		
	public void turn(double speed) {
	   for(Talon l : leftWheels) {
		   l.set(speed);
	   }
	   for(Talon r : rightWheels) {
		   r.set(-speed);
	   }
	}
}
