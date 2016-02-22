package org.usfirst.frc.team3182.robot;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	//Controller(s)
	public static final int driveStick1 = 0;
	public static final int driveStickL = 1;
	
	//Drivetrain
	public static final int rightWheel = 0;
	public static final int leftWheel = 1;
	
	//Collector
	public static final int collectorMotor = 4; 
	
	//Arm
	public static final int armMotor = 3;
	
	//Drivetrain sensors
	public static final int rightEncoder_A = 11;
	public static final int rightEncoder_B = 10;
	public static final int leftEncoder_A = 13;
	public static final int leftEncoder_B = 12;

	//Collector sensors
	public static final int irBallSensor = 7;
	public static final int leftTriangleIR = 6;
	public static final int rightTriangleIR = 5;
	
	//Arm sensors
	public static final int armPotentiometer = 2;
	
}
