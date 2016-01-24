package org.usfirst.frc.team3182.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team3182.robot.commands.AutoCollect;
import org.usfirst.frc.team3182.robot.commands.DriveControl;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	/**
     CREATING BUTTONS
     One type of button is a joystick button which is any button on a joystick.
     You create one by telling it which joystick it's on and which button
     number it is.
     Joystick stick = new Joystick(port);
     Button button = new JoystickButton(stick, buttonNumber);
     There are a few additional built in buttons you can use. Additionally,
     by subclassing Button you can create custom triggers and bind those to
     commands the same as any other Button.
    
     TRIGGERING COMMANDS WITH BUTTONS
     Once you have a button, it's trivial to bind it to a button in one of
     three ways:
    
     Start the command when the button is pressed and let it run the command
     until it is finished as determined by it's isFinished method.
     button.whenPressed(new ExampleCommand());
    
     Run the command while the button is being held down and interrupt it once
     the button is released.
     button.whileHeld(new ExampleCommand());
    
     Start the command when the button is released  and let it run the command
     until it is finished as determined by it's isFinished method.
     button.whenReleased(new ExampleCommand()); 
     */
	
	Joystick stick = new Joystick(RobotMap.joystick);
	JoystickButton button1 = new JoystickButton(stick, 13);
	JoystickButton button2 = new JoystickButton(stick, 14);
	JoystickButton button3 = new JoystickButton(stick, 15);
	JoystickButton button4 = new JoystickButton(stick, 16);
	
	double speedMult = 0.5;
	double turnMult = 0.3;
	
	public OI() {
		
		button1.whenPressed(null);
		
		
	}
	
	
	public void incSpeed() {
		speedMult += 0.1;
	}
	
	public void decSpeed() {
		speedMult -= 0.1;
	}
	public void incTurn {
		turnMult += 0.1;
		
	}
	
	public void decTurn {
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
	
	
}

