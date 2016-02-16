package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class InfraredControl extends Command {

	double estDis1 = 0;
	double estDis2 = 0;
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		requires(Robot.collector);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		double i1Voltage = Robot.collector.getExternalIRArray()[0].getVoltage();
		double i2Voltage = Robot.collector.getExternalIRArray()[1].getVoltage();
		
		estDis1 = voltageToIn(i1Voltage);
		estDis2 = voltageToIn(i2Voltage);
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

	public static double inToVoltage(double inches){
		return 57.653*(Math.pow(inches, -0.9891));
	}

	public static double voltageToIn(double voltage){
		return (60.2874 / (Math.pow(voltage, 1.011020119300374)));
	}

}
