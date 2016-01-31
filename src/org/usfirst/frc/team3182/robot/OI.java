package org.usfirst.frc.team3182.robot;

import org.usfirst.frc.team3182.robot.commands.AutoDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Operator Interface
 * 
 * In progress
 */
public class OI {

     	
	Joystick stick = new Joystick(RobotMap.joystick);
	JoystickButton button1 = new JoystickButton(stick, 2);
	JoystickButton button2 = new JoystickButton(stick, 4);
	JoystickButton button3 = new JoystickButton(stick, 3);
	JoystickButton button4 = new JoystickButton(stick, 1);
	
	JoystickButton buttonTestAuto1 = new JoystickButton(stick, 10);
	
	Joystick powerGlove = new Joystick(RobotMap.powerGlove);
	
	double speedMult = 0.5;
	double turnMult = 0.3;
	
	public OI() {
		
		button1.whenPressed(new ChangeMultipliers() {
			@Override
			protected void end() {				
				incSpeed();
			}
		});
		button2.whenPressed(new ChangeMultipliers() {
			@Override
			protected void end() {
				incTurn();
			}
		});
		button3.whenPressed(new ChangeMultipliers() {
			@Override
			protected void end() {
				decSpeed();
			}
		});
		button4.whenPressed(new ChangeMultipliers() {
			@Override
			protected void end() {
				decTurn();
			}
		});
		
		buttonTestAuto1.toggleWhenPressed(new AutoDrive());
		System.out.println("OI init");

		
		SmartDashboard.putData("AutoDriveForward", new AutoDrive());
		SmartDashboard.putData(Robot.drivetrain);
	}
	
	
	public void incSpeed() {
		speedMult += 0.1;
	}
	
	public void decSpeed() {
		speedMult -= 0.1;
	}
	public void incTurn() {
		turnMult += 0.1;
		
	}
	
	public void decTurn() {
		turnMult -= 0.1;
	}
	public double getY() {
		return stick.getY() * speedMult;
	}
	
	public double getLeft() {
		return stick.getX() * -turnMult;
	}
	
	public double getRight() {
		return stick.getX() * turnMult;
	}
	
	public double getPowerGloveTilt() {
		return powerGlove.getX();
	}
	public double getPowerGloveClench() {
		return powerGlove.getY();
	}
	
	abstract class ChangeMultipliers extends Command {

		@Override
		protected void initialize() {}
		@Override
		protected void execute() {}
		@Override
		protected boolean isFinished() {
			return true;
		}
		@Override
		protected abstract void end();
		@Override
		protected void interrupted() {}
		
	}
}

