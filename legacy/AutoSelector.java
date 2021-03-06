package org.usfirst.frc.team3182.robot;

import org.usfirst.frc.team3182.robot.commands.AutoCheval;
import org.usfirst.frc.team3182.robot.commands.AutoCollect;
import org.usfirst.frc.team3182.robot.commands.DriveToDistance;
import org.usfirst.frc.team3182.robot.commands.AutoPortcullis;
import org.usfirst.frc.team3182.robot.commands.DefaultAuto;
import org.usfirst.frc.team3182.robot.commands.DriveToAngle;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSelector extends CommandGroup
{
	// Positions are from 0 - 4, inclusive
	
	public static final int AUTODRIVE = 0;
	public static final int PORTCULLIS = 1;
	public static final int CHEVAL = 2;
	public static final int COLLECT = 3;
	
	private int position;
	private int defense;
	private Command command;
	private Command path;
	
	public AutoSelector(int position, int defense)
	{
		this.position = position;
		this.defense = defense;
		
		command = new DefaultAuto();
		path = null;
		
		setPath();
		findSuitableCommand();
		
		addSequential(command);
		addSequential(path);
		
	}
	
	public Command getCommand() 
	{
		return command;
	}
	
	public Command getPath()
	{
		return path;
	}
	
	/** Gets the appropriate command for the defense the robot is facing */
	private void findSuitableCommand() 
	{
		switch(defense)
		{
		case AUTODRIVE:
			command = new DriveToDistance(5);
		case PORTCULLIS:
			command = new AutoPortcullis(position);
		case CHEVAL:
			command = new AutoCheval(position);
		case COLLECT:
			command = new AutoCollect();
		}
	}
	
	/** Creates a path for the robot to follow to the tower based on its position */
	public void setPath()
	{
		switch(position)
		{
		case 1:
			path = new Path() {
				void addCommands() {
					addSequential(new DriveToDistance(6.8333));
					addSequential(new DriveToAngle(56));
					addSequential(new DriveToDistance(4.7982));
					addSequential(new DriveToAngle(-56));
					addSequential(new DriveToDistance(2.65));
					addSequential(new DriveToAngle(60));
					addSequential(new DriveToDistance(1.4142));
				}
			};
			break;
			
		case 2:
			path = new Path() {
				void addCommands() {
					addSequential(new DriveToDistance(12.1333));
					addSequential(new DriveToAngle(60));
					addSequential(new DriveToDistance(1.4142));
				}
			};
			break;
			
		case 3: 
			path = new Path() {
				void addCommands() {
					addSequential(new DriveToDistance(6.8333));
					addSequential(new DriveToAngle(-56));
					addSequential(new DriveToDistance(4.7982));
					addSequential(new DriveToAngle(56));
					addSequential(new DriveToDistance(2.65));
					addSequential(new DriveToAngle(60));
					addSequential(new DriveToDistance(1.4142));
				}
			};
			break;
			
		case 4:
			path = new Path() {
				void addCommands() {
					addSequential(new DriveToDistance(6.8333));
					addSequential(new DriveToAngle(56));
					addSequential(new DriveToDistance(4.7982));
					addSequential(new DriveToAngle(-56));
					addSequential(new DriveToDistance(5.484));
					addSequential(new DriveToAngle(60));
				}
			};
			break;
			
		case 5:
			path = new Path() {
				void addCommands() {
					addSequential(new DriveToDistance(14.9673));
					addSequential(new DriveToAngle(60));
				}
			};
			break;
		}	
	}
	
	private abstract class Path extends CommandGroup
	{
		public Path() {
	        requires(Robot.drivetrain);
	        addCommands();
		}
		abstract void addCommands();
	}
}
