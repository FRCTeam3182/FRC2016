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

     	
	Joystick driveStickR = new Joystick(RobotMap.driveStickR);
	JoystickButton button1 = new JoystickButton(driveStickR, 2);
	JoystickButton button2 = new JoystickButton(driveStickR, 1);
	JoystickButton button3 = new JoystickButton(driveStickR, 3);
	JoystickButton button4 = new JoystickButton(driveStickR, 4);
	JoystickButton button5 = new JoystickButton(driveStickR, 5);
	JoystickButton button6 = new JoystickButton(driveStickR, 6);
	JoystickButton button7 = new JoystickButton(driveStickR, 7);
	
	JoystickButton buttonTestAuto1 = new JoystickButton(driveStickR, 10);
	
	Joystick driveStickL = new Joystick(RobotMap.driveStickL);
	
	Joystick powerGlove = new Joystick(RobotMap.powerGlove);
	JoystickButton pgButton1 = new JoystickButton(powerGlove, 1);
	JoystickButton pgButton2 = new JoystickButton(powerGlove, 2);
	JoystickButton pgButton3 = new JoystickButton(powerGlove, 3);
	JoystickButton pgButton4 = new JoystickButton(powerGlove, 4);
	JoystickButton pgButton5 = new JoystickButton(powerGlove, 5);
	
	
	double speedMult = .4;
	double turnMult = 0.3;
	

	
	public OI() {
		
		
		buttonTestAuto1.toggleWhenPressed(new DriveToDistance(3));
		System.out.println("OI init");
		if(Robot.usesPowerGlove){
			System.out.println("Using PowerGlove");
					}
		else {
			
			System.out.println("Not Using PowerGlove");
			pgButton1.whenPressed(new CollectorControl(-1)); //Button 1, expel
			pgButton2.whenPressed(new CollectorControl(1)); //Button 2, intake
			pgButton3.whenPressed(new CollectorControl(0)); //Button 3, turn off (just in case, shouldn't be necessesary)
		}
		
		
		SmartDashboard.putData("AutoDriveForward", new DriveToDistance(5)); 
		SmartDashboard.putData(Robot.drivetrain);
		
		
	}
	
	public int getCollectValue() {
		if(!pgButton4.get()) 
			return 0;
		else 
			return pgButton3.get() ? -1 : 1; //TODO tune these values
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
		return powerGlove.getY();
	}
	
}

	