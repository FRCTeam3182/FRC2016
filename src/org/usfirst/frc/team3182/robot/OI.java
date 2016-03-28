package org.usfirst.frc.team3182.robot;

import org.usfirst.frc.team3182.robot.commands.RaiseArm;
import org.usfirst.frc.team3182.robot.commands.AutoCollect;
//import org.usfirst.frc.team3182.robot.commands.CollectorControl;
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
	JoystickButton button1 = new JoystickButton(stick, 2);
	JoystickButton button2 = new JoystickButton(stick, 1);
	JoystickButton button3 = new JoystickButton(stick, 3);
	JoystickButton button4 = new JoystickButton(stick, 4);
	JoystickButton button5 = new JoystickButton(stick, 5);
	JoystickButton button6 = new JoystickButton(stick, 6);
	JoystickButton button7 = new JoystickButton(stick, 7);
	
	JoystickButton buttonTestAuto1 = new JoystickButton(stick, 10);
	
	Joystick powerGlove = new Joystick(RobotMap.powerGlove);
	JoystickButton pgButton1 = new JoystickButton(powerGlove, 1);
	JoystickButton pgButton2 = new JoystickButton(powerGlove, 2);
	JoystickButton pgButton4 = new JoystickButton(powerGlove, 4);
	JoystickButton pgButton5 = new JoystickButton(powerGlove, 5);
	
	
	double speedMult = .4;
	double turnMult = 0.3;
	
	public OI() {
		
		button1.whenPressed(new ChangeMultipliers() {
			@Override
			protected void end() {
				incSpeed(0.1);
			}
		});
		button2.whenPressed(new ChangeMultipliers() {
			@Override
			protected void end() {
				incSpeed(0.25);
			}
		});
		button3.whenPressed(new ChangeMultipliers() {
			@Override
			protected void end() {
				incSpeed(-0.1);
			}
		});
		button4.whenPressed(new ChangeMultipliers() {
			@Override
			protected void end() {
				incSpeed(-0.25);
			}
		});
		button5.whenPressed(new ChangeMultipliers() {
			protected void end() {
				incSpeed(-.15);
			}
		});
		button6.whenPressed(new ChangeMultipliers() {
			protected void end() {
				incSpeed(.15);
			}
		});
		
		buttonTestAuto1.toggleWhenPressed(new DriveToDistance(3));
		System.out.println("OI init");
		
//		pgButton1.toggleWhenActive(new CollectorControl(-.5));
//		pgButton2.toggleWhenActive(new CollectorControl(1));
		
//		pgButton4.whenPressed(new RaiseArm());
//		pgButton5.whenPressed(new LowerArm());
		
		
		SmartDashboard.putData("AutoDriveForward", new DriveToDistance(5)); 
		SmartDashboard.putData(Robot.drivetrain);
		
		
	}
	
	
	public void incSpeed(double inc) {
		speedMult += inc;
	}
	
	public double getL() {
		return stick.getY();// * speedMult;
	}
	
	public double getR() {
		return 0;//stick.getRawAxis(2) * speedMult;
	}
	public double getPowerGloveTilt() {
		return powerGlove.getY();
	}
	
	public boolean getArmButton() {
		return pgButton1.get();
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

