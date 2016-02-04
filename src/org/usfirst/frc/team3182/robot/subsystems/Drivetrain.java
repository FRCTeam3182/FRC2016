package org.usfirst.frc.team3182.robot.subsystems;

import org.usfirst.frc.team3182.robot.Robot;
import org.usfirst.frc.team3182.robot.RobotMap;
import org.usfirst.frc.team3182.robot.commands.DriveControl;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * Complete except for encoder stuff
 * Tested with demo-bot
 */

public class Drivetrain extends Subsystem {

	
	Talon[] wheels;
	Talon leftWheel;
	Talon rightWheel;
	Encoder rightEncoder, leftEncoder;
	PIDController positionControllerR,
				positionControllerL, 
				velocityStabilizerR, 
				velocityStabilizerL, 
				driftStabilizerR, 
				driftStabilizerL;
	
	double speedR, speedL;
	private PIDWrapper controlledPositionR,
					controlledPositionL,
					stabilizedDriftR,
					stabilizedDriftL;	

	public Drivetrain() {
		leftWheel = new Talon(RobotMap.leftWheel);
		rightWheel = new Talon(RobotMap.rightWheel);
		wheels = new Talon[2];
		wheels[0] = leftWheel;
		wheels[1] = rightWheel;
		
		leftWheel.setInverted(true);
		
		rightEncoder = new Encoder(RobotMap.rightEncoder_A, RobotMap.rightEncoder_B);
		leftEncoder = new Encoder(RobotMap.leftEncoder_A, RobotMap.leftEncoder_B, true);
		
		rightEncoder.setDistancePerPulse(.002909); //TODO change for big wheels
		leftEncoder.setDistancePerPulse(.002909);
		
		
		controlledPositionR = new PIDWrapper();
		positionControllerR = new PIDController(0.1, 0.001, 0, new PIDDistanceEncoder(rightEncoder), controlledPositionR);
		positionControllerR.startLiveWindowMode();
		positionControllerR.enable();
		LiveWindow.addSensor("Drivetrain", "PosConR", positionControllerR);
		
		controlledPositionL = new PIDWrapper();
		positionControllerL = new PIDController(0.1, 0.001, 0, new PIDDistanceEncoder(leftEncoder), controlledPositionL);		
		positionControllerL.startLiveWindowMode();
		positionControllerL.enable();
		positionControllerL.enable();
		LiveWindow.addSensor("Drivetrain", "PosConL", positionControllerL);
		
		
		stabilizedDriftR = new PIDWrapper();
		driftStabilizerR = new PIDController(0.1, 0.001, 0, controlledPositionR, stabilizedDriftR);
		//driftStabilizerR.setSetpoint(controlledPositionL.pidGet());
		driftStabilizerR.startLiveWindowMode();
		driftStabilizerR.enable();
		LiveWindow.addSensor("Drivetrain", "DriftStabR", driftStabilizerR);
		
		stabilizedDriftL = new PIDWrapper();
		driftStabilizerL = new PIDController(0.1, 0.001, 0, controlledPositionL, stabilizedDriftL);
		//driftStabilizerL.setSetpoint(controlledPositionR.pidGet());
		driftStabilizerL.startLiveWindowMode();
		driftStabilizerL.enable();
		LiveWindow.addSensor("Drivetrain", "DriftStabL", driftStabilizerL);
		
		velocityStabilizerR = new PIDController(.1, 0.001, 0, new PIDRateEncoder(rightEncoder), rightWheel);
		velocityStabilizerR.startLiveWindowMode();
		velocityStabilizerR.enable();
		LiveWindow.addSensor("Drivetrain", "VelStabR", velocityStabilizerR);
		
		velocityStabilizerL = new PIDController(.1, 0.001, 0, new PIDRateEncoder(leftEncoder), leftWheel);
		velocityStabilizerL.startLiveWindowMode();
		velocityStabilizerL.enable();
		LiveWindow.addSensor("Drivetrain", "VelStabL", velocityStabilizerL);
		
		System.out.println("Drivetrain init");
		

	}
	
	public void reset() {
		rightEncoder.reset();
		leftEncoder.reset();
		
	}
	
	
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
		drive(speed, speed);
//		SmartDashboard.putNumber("Drive Speed", speed);
//		SmartDashboard.putNumber("Drive Right Encoder", rightEncoder.getDistance());
//		SmartDashboard.putNumber("Drive Left Encoder", leftEncoder.getDistance());

	}
	public void driveRaw(double speedL, double speedR) {
		rightWheel.set(speedR);
		leftWheel.set(speedL);
	}
	
	public void stop() {
		drive(0);
	}
		
	public void drive(double speedL, double speedR) { //Needs to be called constantly (in a loop)
		   //velocityStabilizerL.setPID(velocityStabilizerL.getP(), velocityStabilizerL.getI(), velocityStabilizerL.getD(), speedL);
		   speedR *= 10;
		   speedL *= 10;
		
			velocityStabilizerL.setSetpoint(speedL);

		   velocityStabilizerR.setSetpoint(speedR); //setpoint is in FPS, joystick isn't

			SmartDashboard.putNumber("Drive Speed L", speedL);
			SmartDashboard.putNumber("Drive Speed R", speedR);
			SmartDashboard.putNumber("Drive Right Encoder", rightEncoder.getRate());
			SmartDashboard.putNumber("Drive Left Encoder", leftEncoder.getRate());
	}
	
	public void driveToDistance(double feet) {
		positionControllerL.setSetpoint(feet);
		positionControllerR.setSetpoint(feet);
	}
	
	public void updatePID() {
		driftStabilizerR.setSetpoint(controlledPositionL.pidGet());
		driftStabilizerL.setSetpoint(controlledPositionR.pidGet());
		velocityStabilizerR.setSetpoint(stabilizedDriftR.pidGet());
		velocityStabilizerL.setSetpoint(stabilizedDriftL.pidGet());
	}
	
	public void driveFPS(double speedL, double speedR) {
		velocityStabilizerL.setSetpoint(speedL);
		velocityStabilizerR.setSetpoint(speedR);
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