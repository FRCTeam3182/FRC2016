package org.usfirst.frc.team3182.robot.subsystems;

import java.util.ArrayList;

import org.usfirst.frc.team3182.robot.commands.ArmControl;
import org.usfirst.frc.team3182.robot.commands.InfraredControl;
import org.usfirst.frc.team3182.robot.utils.Point;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Infrared extends Subsystem {

	public AnalogInput ir1 = new AnalogInput(5); // Check channel input
	public AnalogInput ir2 = new AnalogInput(6);
	
	public static ArrayList<Point> points = new ArrayList<Point>(); // x is distance in cm, y is voltage
	
	public Infrared() {
		populatePointArray();
	}
	
	private void populatePointArray(){
		points.add(new Point(15, 2.75));
		points.add(new Point(20, 2.5));
		points.add(new Point(30, 2.0));
		points.add(new Point(40, 1.5));
		points.add(new Point(50, 1.25));
		points.add(new Point(60, 1.15));
		points.add(new Point(70, 0.9));
		points.add(new Point(80, 0.8));
		points.add(new Point(90, 0.75));
		
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
	
	public static ArrayList<Point> getPoints() {
		return points;
	}

}
