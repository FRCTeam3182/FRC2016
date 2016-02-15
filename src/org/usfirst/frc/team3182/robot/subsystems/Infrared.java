package org.usfirst.frc.team3182.robot.subsystems;


import org.usfirst.frc.team3182.robot.commands.InfraredControl;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Infrared extends Subsystem {

	public AnalogInput ir1 = new AnalogInput(5); // Check channel input
	public AnalogInput ir2 = new AnalogInput(6);
	
	
	public Infrared() {
		
	}
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		this.setDefaultCommand(new InfraredControl());

	}
	
	public AnalogInput getIr1() {
		return ir1;
	}
	
	public AnalogInput getIr2() {
		return ir2;
	}
	

}
