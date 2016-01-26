package org.usfirst.frc.team3182.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3182.robot.RobotMap;
import org.usfirst.frc.team3182.robot.commands.ArmControl;
/**
 * Complete but needs tuning and testing
 * 
 */

public class Arm extends Subsystem {
	
	Talon armMotor = new Talon(RobotMap.armMotor);
	Encoder encoder = new Encoder(RobotMap.armEncoder_A, RobotMap.armEncoder_B);
	double initAngle;
	
	public Arm() {
		encoder.reset();
	}
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
	
	public void setToAngle(double theta) {
		double currTheta = this.getAngle();
		while(currTheta != theta) {
			if(theta < currTheta) lower();
			if(theta > currTheta) raise();
			if(Math.abs(theta - currTheta) < 0.05) break;
		}
		stop();
	}
	
	public double getAngle() {
		return encoder.getDistance(); //TODO some scalar to get this to a useful angle
	}
	
	public void stop() {
		this.setSpeed(0);
	}

}
