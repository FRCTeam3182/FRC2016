package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class Path extends CommandGroup
	{
		public Path() {
	        addCommands();
		}
		public abstract void addCommands();
	}