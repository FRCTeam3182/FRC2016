package org.usfirst.frc.team3182.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//TODO:  A bunch of ports

	//Controller(s)
	public static final int joystick = 0;
	public static final int powerGlove = -1;
	
	//Drivetrain
	public static final int[] rightWheels = {0};
	public static final int[] leftWheels = {1};
	
	//Collector
	public static final int collectorMotor =2; 
	
	//Arm
	public static final int armMotor = 3;
	
	//Drivetrain sensors
	public static final int rightEncoder_A = 1;
	public static final int rightEncoder_B = 2;
	public static final int leftEncoder_A = 3;
	public static final int leftEncoder_B = 4;
    
	//Collector sensors
	public static final int ultraSonic1_ping = 5;
	public static final int ultraSonic1_receive = 6;
	public static final int ultraSonic2_ping = 7;
	public static final int ultraSonic2_receive = 8;

	public static final int collectorLimitSwitch = 9;
	
	//Arm sensors
	public static final int armEncoder_A = -1;
	public static final int armEncoder_B = -1;
	
	
	//Camera??
}
