package org.usfirst.frc.team3182.robot;

import org.usfirst.frc.team3182.robot.commands.DefaultAuto;

import edu.wpi.first.wpilibj.command.Command;

public class AutoSelector 
{
	
	// Positions are from 0 - 4, inclusive
	public static final int AUTODRIVE = 0;
	public static final int PORTCULLIS = 1;
	public static final int CHEVAL = 2;
	
	private int tactic;
	private Command command;
	
	public AutoSelector(int tactic) 
	{
		this.tactic = tactic;
		command = new DefaultAuto();
		
		findSuitableCommand();
	}
	
	public Command getCommand() 
	{
		return command;
	}
	
	private void findSuitableCommand() 
	{
		switch(tactic)
		{
		case AUTODRIVE:
			command = new AutoDrive();
		case PORTCULLIS:
			command = new AutoPortcullis();
		case CHEVAL:
			command = new AutoCheval();
		}
	}
}
