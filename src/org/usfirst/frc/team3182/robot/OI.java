package org.usfirst.frc.team3182.robot;

import org.usfirst.frc.team3182.robot.commands.RaiseArm;
import org.usfirst.frc.team3182.robot.commands.AutoCollect;
import org.usfirst.frc.team3182.robot.commands.DriveToDistance;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3182.robot.commands.InfraredControl;
import org.usfirst.frc.team3182.robot.commands.LowerArm;

/**
 * Operator Interface
 * 
 * In progress
 */
public class OI {

     	
	Joystick stick = new Joystick(RobotMap.joystick);
	JoystickButton button1 = new JoystickButton(stick, 1);
	JoystickButton button2 = new JoystickButton(stick, 2);
	JoystickButton button3 = new JoystickButton(stick, 3);
	JoystickButton button4 = new JoystickButton(stick, 1);
	JoystickButton button5 = new JoystickButton(stick, 5);
	JoystickButton button6 = new JoystickButton(stick, 6);
	JoystickButton button7 = new JoystickButton(stick, 7);
	
	JoystickButton buttonTestAuto1 = new JoystickButton(stick, 10);
	
	Joystick powerGlove = new Joystick(RobotMap.powerGlove);
	JoystickButton pgButton1 = new JoystickButton(powerGlove, 1);
	JoystickButton pgButton2 = new JoystickButton(powerGlove, 2);
	
	
	double speedMult = .4;
	double turnMult = 0.2;
	
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

		button7.whileHeld(new AutoCollect());
		//pgButton2.whenPressed(new InfraredControl());
		
		buttonTestAuto1.toggleWhenPressed(new DriveToDistance(3));
		System.out.println("OI init");
		
		button5.whenPressed(new RaiseArm());
		button6.whenPressed(new LowerArm());
		
		SmartDashboard.putData("AutoDriveForward", new DriveToDistance(5)); 
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

