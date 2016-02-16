package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class InfraredControl extends Command {

	double leftDis = 0;
	double rightDis = 0;

	boolean finished = false;

	// TODO: MORE COMMENTS EXPLAINING THIS PLEASEEE
	final double IR_DIST = 13.76; // inches
	final int IR_RANGE = 20;
	final double ANGLE_THRESHOLD = 5;

	double angleAB;
	double angleAC;
	double angleBC;
	
	public InfraredControl(){
		requires(Robot.collector);
		requires(Robot.drivetrain);
	}
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {

		double i1Voltage = getVoltage()[0];
		double i2Voltage = getVoltage()[1];

		leftDis = voltageToIn(i1Voltage);
		rightDis = voltageToIn(i2Voltage);

		if (leftDis < IR_RANGE && rightDis>= IR_RANGE){
			Robot.drivetrain.driveToAngleAndForward(-5, 12);
			return;
		}
		else if (rightDis < IR_RANGE && leftDis >= IR_RANGE){
			Robot.drivetrain.driveToAngleAndForward(5, 12);
			return;
		}
		if (leftDis >= IR_RANGE && rightDis >= IR_RANGE){
			finished = true;
			return;
		}

		calculateAngles();

		while(Math.abs(angleAC - angleBC) < ANGLE_THRESHOLD){
			if (angleAC > angleBC){
				Robot.drivetrain.driveToAngleAndForward(-5, 12); // Move left by increments of 5 degrees left and keep checking
				calculateAngles();
			}
			else if (angleBC > angleAC){
				Robot.drivetrain.driveToAngleAndForward(5, 12); // Move left by increments of 5 degrees right and keep checking
				calculateAngles();
			}
		}
		finished = true;

	}

	private double[] getVoltage(){
		double i1Voltage = Robot.collector.getExternalIRArray()[0].getAverageVoltage();
		double i2Voltage = Robot.collector.getExternalIRArray()[1].getAverageVoltage();
		return new double[]{i1Voltage, i2Voltage};
	}

	private void calculateAngles(){
		double i1Voltage = getVoltage()[0];
		double i2Voltage = getVoltage()[1];

		double a = voltageToIn(i1Voltage); // Left
		double b = voltageToIn(i2Voltage); // Right
		double c = IR_DIST;


		// Law of Cosines, needs to be tested a bunch
		angleAB = Math.acos((Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) / (2*(a*b)) ); // This angle should stay the same as the robot moves
		angleAC = Math.acos((Math.pow(a, 2) + Math.pow(c, 2) - Math.pow(b, 2)) / (2*(a*c)) );
		angleBC = Math.acos((Math.pow(b, 2) + Math.pow(c, 2) - Math.pow(a, 2)) / (2*(b*c)) );

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
		return ((60.2874 / (Math.pow(voltage, 1.011020119300374))) * 0.393701);
	}

}
