package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class InfraredControl extends Command {

	double leftDis = 0;
	double rightDis = 0;

	boolean finished = false;

	final int IR_RANGE = 20; // inches
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

		leftDis = voltageToIn(i1Voltage);
		rightDis = voltageToIn(i2Voltage);

		if (leftDis >= IR_RANGE && rightDis >= IR_RANGE){
			return;
		}

		
	}


	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return finished;
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
		return 57.653*(Math.pow(2.54 * inches, -0.9891));
	}

	public static double voltageToIn(double voltage){
		return (60.2874 / (Math.pow(voltage, 1.011020119300374)) * 0.393701);
	}

}
