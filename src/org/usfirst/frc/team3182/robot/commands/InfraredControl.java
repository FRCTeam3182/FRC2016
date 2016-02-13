package org.usfirst.frc.team3182.robot.commands;

import java.util.ArrayList;

import org.usfirst.frc.team3182.robot.Robot;
import org.usfirst.frc.team3182.robot.utils.Point;

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
		ArrayList<Point> points = Robot.infrared.getPoints();
		double i1Voltage = Robot.infrared.getIr1().getVoltage();
		double i2Voltage = Robot.infrared.getIr2().getVoltage();
		for (Point p : points){
			if (i1Voltage < p.getY()){
				Point p1 = points.get(points.indexOf(p) - 1);
				Point p2 = p;
				double slope = calcSlope(p1, p2);
				estDis1 = p1.getX() + ((i1Voltage - p1.getY()) / slope);
			}
			if (i2Voltage < p.getY()){
				Point p1 = points.get(points.indexOf(p) - 1);
				Point p2 = p;
				double slope = calcSlope(p1, p2);
				estDis2 = p1.getX() + ((i1Voltage - p1.getY()) / slope);
			}
			
			
		}

	}
	
	private double calcSlope(Point p1, Point p2){
		double deltaY = p2.getY() - p1.getY();
		double deltaX = p2.getX() - p1.getX();
		
		return (deltaY / deltaX);
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
