package org.usfirst.frc.team3182.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

//import org.usfirst.frc.team3182.robot.commands.AutoCollect;
//import org.usfirst.frc.team3182.robot.commands.DriveControl;

/**
 * Operator Interface
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

     	
	Joystick stick = new Joystick(RobotMap.joystick);
	JoystickButton button1 = new JoystickButton(stick, 2);
	JoystickButton button2 = new JoystickButton(stick, 4);
	JoystickButton button3 = new JoystickButton(stick, 3);
	JoystickButton button4 = new JoystickButton(stick, 1);
	
	double speedMult = 0.5;
	double turnMult = 0.3;
	
	public OI() {
		
		button1.whenPressed(new changeMultipliers(){
			@Override
			protected void end() {				
				incSpeed();
			}
		});
		button2.whenPressed(new changeMultipliers(){
			@Override
			protected void end() {
				incTurn();
			}
		});
		button3.whenPressed(new changeMultipliers(){
			@Override
			protected void end() {
				decSpeed();
			}
		});
		button4.whenPressed(new changeMultipliers(){
			@Override
			protected void end() {
				decTurn();
			}
		});
		
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
//		double left = stick.getRawAxis(3);
//		if(left < 0.1) return 0;
//		return left * 0.5;
		return stick.getX() * -turnMult;
	}
	
	public double getRight() {
//		double right = stick.getRawAxis(4);
//		if(right < 0.1) return 0;
//		return right * 0.5;
		return stick.getX() * turnMult;
	}
	
	public double getPowerGloveX() {
		return 1.0;
	}
	
	public double getPowerGloveY() {
		return 1.0;
	}
	public double getPowerGloveZ() {
		return 1.0;
	}
	
	abstract class changeMultipliers extends Command {

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

