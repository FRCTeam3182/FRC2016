package org.usfirst.frc.team3182.robot.subsystems;

import org.usfirst.frc.team3182.robot.RobotMap;
import org.usfirst.frc.team3182.robot.commands.DriveControl;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * Complete except for encoder stuff
 * Tested with demo-bot
 */

public class Drivetrain extends PIDSubsystem {

	
	Talon[] wheels;
	Talon[] leftWheels;
	Talon[] rightWheels;
	Encoder rightEncoder, leftEncoder;
	PIDController positionControllerR,
				positionControllerL, 
				velocityStabilizerR, 
				velocityStabilizerL, 
				driftStabilizerR, 
				driftStabilizerL;
	
	double speedR, speedL;
	
	public Drivetrain() {
		super("Drivetrain", .1, .001, 0);
		leftWheels = new Talon[RobotMap.leftWheels.length];
		rightWheels = new Talon[RobotMap.rightWheels.length];
		wheels = new Talon[leftWheels.length + rightWheels.length];
		
		for(int i = 0; i <  RobotMap.leftWheels.length; i++) {
			leftWheels[i] = new Talon(RobotMap.leftWheels[i]);
			wheels[i] = leftWheels[i];
		}
		
		for(int i = 0; i <  RobotMap.rightWheels.length; i++) {
			rightWheels[i] = new Talon(RobotMap.rightWheels[i]);
			wheels[i+RobotMap.leftWheels.length] = rightWheels[i];
			wheels[i].setInverted(true);
		}
		
		rightEncoder = new Encoder(RobotMap.rightEncoder_A, RobotMap.rightEncoder_B);
		leftEncoder = new Encoder(RobotMap.leftEncoder_A, RobotMap.leftEncoder_B, true);
		
		rightEncoder.setDistancePerPulse(.002909); //TODO change for big wheels
		leftEncoder.setDistancePerPulse(.002909);
		
		
		PIDWrapper controlledPositionR = new PIDWrapper();
		positionControllerR = new PIDController(0.1, 0.001, 0, new PIDDistanceEncoder(rightEncoder), controlledPositionR);
		
		PIDWrapper controlledPositionL = new PIDWrapper();
		positionControllerL = new PIDController(0.1, 0.001, 0, new PIDDistanceEncoder(leftEncoder), controlledPositionL);		

		PIDWrapper stabilizedDriftR = new PIDWrapper();
		driftStabilizerR = new PIDController(0.1, 0.001, 0, controlledPositionL, stabilizedDriftR);

		PIDWrapper stabilizedDriftL = new PIDWrapper();
		driftStabilizerL = new PIDController(0.1, 0.001, 0, controlledPositionR, stabilizedDriftL);
		
		PIDWrapper stabilizedVelocityR = new PIDWrapper();
		velocityStabilizerR = new PIDController(0.1, 0.001, 0, new PIDRateEncoder(rightEncoder), stabilizedVelocityR);
		
		PIDWrapper stabilizedVelocityL = new PIDWrapper();
		velocityStabilizerL = new PIDController(0.1, 0.001, 0, new PIDRateEncoder(leftEncoder), stabilizedVelocityL);
		
		System.out.println("Drivetrain init");
		

	}
	
	public void reset() {
		rightEncoder.reset();
		leftEncoder.reset();
		
	}
	
	//TODO set encoder distance
	public double getDistance() {
		System.out.printf("RE: %5f  LE: %5f %n", rightEncoder.getDistance(), leftEncoder.getDistance());
		return  leftEncoder.getDistance() / 2 + rightEncoder.getDistance()/2;	
	}
	
	public double getPosR() {
		return rightEncoder.getDistance();
	}
	public double getPosL() {
		return leftEncoder.getDistance();
	}
	public double getRateR() {
		return rightEncoder.getRate();
	}
	public double getRateL() {
		return leftEncoder.getRate();
	}
	
	
	
	public void initDefaultCommand() {
		this.setDefaultCommand(new DriveControl());
	}
	
	
	
	public void drive(double speed) { //some might need to be reversed
		for (Talon w : wheels) {
			w.set(speed);
		}
		SmartDashboard.putNumber("Drive Speed", speed);
		SmartDashboard.putNumber("Drive Right Encoder", rightEncoder.getDistance());
		SmartDashboard.putNumber("Drive Left Encoder", leftEncoder.getDistance());

	}
	
	public void stop() {
		drive(0);
	}
		
	public void drive(double speedL, double speedR) {
	   for(Talon l : leftWheels) {
		   l.set(speedL);
	   }
	   for(Talon r : rightWheels) {
		   r.set(speedR);
	   }
	}
	@Override
	protected double returnPIDInput() {
		return getDistance();
	}
	@Override
	protected void usePIDOutput(double output) {
		drive(output);
		
	}
}
class PIDWrapper implements PIDOutput, PIDSource {
	double value;
	@Override
	public void pidWrite(double output) {
		value = output;
	}
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {}
	@Override
	public PIDSourceType getPIDSourceType() {
		return null;
	}
	@Override
	public double pidGet() {
		return value;
	}
	
}
class PIDRateEncoder implements PIDSource {
	Encoder e;
	public PIDRateEncoder(Encoder e) {
		this.e = e;
	}
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kRate;
	}

	@Override
	public double pidGet() {
		return e.getRate();
	}
	
}
class PIDDistanceEncoder implements PIDSource {
	Encoder e;
	public PIDDistanceEncoder(Encoder e) {
		this.e = e;
	}
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return e.getDistance();
	}
	
}