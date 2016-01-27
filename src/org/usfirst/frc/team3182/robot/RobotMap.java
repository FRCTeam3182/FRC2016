package org.usfirst.frc.team3182.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
	
	//Controller(s)
	public static final int joystick = 0;
	
	//Drivetrain
	public static final int rightWheel = 0;
	public static final int leftWheel = 1;
	
	//Collector
	public static final int collectorMotor = 2; 
	
	//Arm
	public static final int armMotor1 = 3;
	
	//Drivetrain sensors
    
	//Collector sensors
	public static final int ultraSonic1_ping = 5;
	public static final int ultraSonic1_receive = 6;
	public static final int ultraSonic2_ping = 7;
	public static final int ultraSonic2_receive = 8;

	public static final int collectorLimitSwitch = 9;
	
	//Arm sensors
	
	
	//Camera??
}
