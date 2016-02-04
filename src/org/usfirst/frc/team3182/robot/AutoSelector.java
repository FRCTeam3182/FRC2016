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
			command = new AutoDrive(position);
		case PORTCULLIS:
			command = new AutoPortcullis(position);
		case CHEVAL:
			command = new AutoCheval(position);
		case COLLECT:
			command = new AutoCollect(position);
		}
	}
	
	/** Creates a path for the robot to follow to the tower based on its position */
	public void setPath()
	{
		switch(position)
		{
		case 1:
			path = new Path() {
				protected void initialize() {
					//drive(6.8333FT)
					//turn(56deg to right)
					//drive(4.7982FT)
					//turn(56deg to left)
					//drive(2.6500FT)
					//turn(60deg to right)
					//drive(1.4142FT)
				}
			};
			break;
			
		case 2:
			path = new Path() {
				protected void initialize() {
					//drive(12.1333FT)
					//turn(60deg to right)
					//drive(1.4142FT)
				}
			};
			break;
			
		case 3: 
			path = new Path() {
				protected void initialize() {
					//drive(6.8333FT)
					//turn(56deg to left)
					//drive(4.7982FT)
					//turn(56deg to right)
					//drive(2.6500FT)
					//turn(60deg to right)
					//drive(1.4142FT)
				}
			};
			break;
			
		case 4:
			path = new Path() {
				protected void initialize() {
					//drive(6.8333FT)
					//turn(56deg to right)
					//drive(4.7982FT)
					//turn(56deg to left)
					//drive(5.484FT)
					//turn(60deg to left)
				}
			};
			break;
			
		case 5:
			path = new Path() {
				protected void initialize() {
					//drive(14.9673FT)
					//turn(60deg to right)
				}
			};
			break;
		}	
	}
	
	private abstract class Path extends Command
	{
		public Path() {
	        requires(Robot.drivetrain);
	    }
		
	    protected void initialize() {
	    	// This is what we overwrite with the path instructions
	    }

	    protected void execute() {
	    }

	    protected boolean isFinished() {
	        return true;
	    }

	    protected void end() {
	    }

	    protected void interrupted() {
	    }
	}
}
