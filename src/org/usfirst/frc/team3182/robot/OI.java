package org.usfirst.frc.team3182.robot;

import org.usfirst.frc.team3182.robot.commands.RaiseArm;
import org.usfirst.frc.team3182.robot.commands.AutoCollect;
import org.usfirst.frc.team3182.robot.commands.CollectorControl;
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

     	
	Joystick driveStickR = new Joystick(RobotMap.driveStick1);
	JoystickButton button1 = new JoystickButton(driveStickR, 2);
	JoystickButton button2 = new JoystickButton(driveStickR, 1);
	JoystickButton button3 = new JoystickButton(driveStickR, 3);
	JoystickButton button4 = new JoystickButton(driveStickR, 4);
	JoystickButton button5 = new JoystickButton(driveStickR, 5);
	JoystickButton button6 = new JoystickButton(driveStickR, 6);
	JoystickButton button7 = new JoystickButton(driveStickR, 7);
	
	JoystickButton buttonTestAuto1 = new JoystickButton(driveStickR, 10);
	
	Joystick driveStickL = new Joystick(RobotMap.driveStickL);
	JoystickButton pgButton1 = new JoystickButton(driveStickL, 1);
	JoystickButton pgButton2 = new JoystickButton(driveStickL, 2);
	JoystickButton pgButton4 = new JoystickButton(driveStickL, 4);
	JoystickButton pgButton5 = new JoystickButton(driveStickL, 5);
	
	
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
		
		pgButton1.toggleWhenActive(new CollectorControl(-.5));
		pgButton2.toggleWhenActive(new CollectorControl(1));
		
//		pgButton4.whenPressed(new RaiseArm());
//		pgButton5.whenPressed(new LowerArm());
		
		
		SmartDashboard.putData("AutoDriveForward", new DriveToDistance(5)); 
		SmartDashboard.putData(Robot.drivetrain);
		
		
	}
	
	
	public void incSpeed(double inc) {
		speedMult += inc;
	}
	
	public double getL() {
		return driveStickL.getY();
	}
	

	public double getR() {
		return driveStickR.getY();
	}

	public double getLExp() { //"ramps up" so 1/2 = 1/4, 3/4 = 9/16 (just over 1/2), 1 = 1 (just squares the value)
		if(getL() > 0) return getL() * getL();
		else return getL() * -getL();
	}
	
	public double getRExp() { 
		if(getR() > 0) return getR() * getR();
		else return getR() * -getR();
	}
	
	public double getPowerGloveTilt() {
		return driveStickL.getY();
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

