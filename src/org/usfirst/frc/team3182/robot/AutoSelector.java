package org.usfirst.frc.team3182.robot;

import org.usfirst.frc.team3182.robot.commands.AutoCheval;
import org.usfirst.frc.team3182.robot.commands.AutoCollect;
import org.usfirst.frc.team3182.robot.commands.AutoDrive;
import org.usfirst.frc.team3182.robot.commands.AutoPortcullis;
import org.usfirst.frc.team3182.robot.commands.DefaultAuto;

import edu.wpi.first.wpilibj.command.Command;

public class AutoSelector 
{
	
	// Positions are from 0 - 4, inclusive
	public static final int AUTODRIVE = 0;
	public static final int PORTCULLIS = 1;
	public static final int CHEVAL = 2;
	public static final int COLLECT = 3;
	
	private int position;
	private int tactic;
	private Command command;
	
	public AutoSelector(int position, int tactic) 
	{
		this.position = position;
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
			command = new AutoDrive(position);
		case PORTCULLIS:
			command = new AutoPortcullis(position);
		case CHEVAL:
			command = new AutoCheval(position);
		case COLLECT:
			command = new AutoCollect(position);
		}
	}
}
