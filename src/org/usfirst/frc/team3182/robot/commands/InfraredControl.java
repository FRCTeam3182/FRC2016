package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class InfraredControl extends Command {

	double estDis1 = 0;
	double estDis2 = 0;
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		requires(Robot.infrared);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		double i1Voltage = Robot.infrared.getIr1().getVoltage();
		double i2Voltage = Robot.infrared.getIr2().getVoltage();
		
		estDis1 = 57.653*(Math.pow(i1Voltage, -0.9891));
		estDis2 = 57.653*(Math.pow(i2Voltage, -0.9891));


	}


	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
