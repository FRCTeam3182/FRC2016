package org.usfirst.frc.team3182.robot.subsystems;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

		rightEncoder.setDistancePerPulse(.002909); //TODO change for big wheels to .06981317
		leftEncoder.setDistancePerPulse(.002909);



		controlledPositionR = new PIDWrapper();
		positionControllerR = new PIDController(0.1, 0.000, 0.001, new PIDDistanceEncoder(rightEncoder), controlledPositionR);
		positionControllerR.startLiveWindowMode();
		positionControllerR.setAbsoluteTolerance(0.1);
		//positionControllerR.enable();
		LiveWindow.addSensor("Drivetrain", "PosConR", positionControllerR);

		controlledPositionL = new PIDWrapper();
		positionControllerL = new PIDController(0.1, 0.000, 0.001, new PIDDistanceEncoder(leftEncoder), controlledPositionL);		
		positionControllerL.startLiveWindowMode();
		positionControllerL.setAbsoluteTolerance(0.1);
		//positionControllerL.enable();
		LiveWindow.addSensor("Drivetrain", "PosConL", positionControllerL);


		stabilizedDriftR = new PIDWrapper();
		driftStabilizerR = new PIDController(0.1, 0.001, 0, controlledPositionL, stabilizedDriftR);
		driftStabilizerR.startLiveWindowMode();
		//driftStabilizerR.enable();
		LiveWindow.addSensor("Drivetrain2", "DriftStabR", driftStabilizerR);

		stabilizedDriftL = new PIDWrapper();
		driftStabilizerL = new PIDController(0.1, 0.001, 0, controlledPositionR, stabilizedDriftL);
		driftStabilizerL.startLiveWindowMode();
		//driftStabilizerL.enable();
		LiveWindow.addSensor("Drivetrain2", "DriftStabL", driftStabilizerL);

		velocityStabilizerR = new PIDController(.05, 0.01, 0.00000, new PIDRateEncoder(rightEncoder), rightWheel);
		velocityStabilizerR.startLiveWindowMode();
		velocityStabilizerR.enable();
		LiveWindow.addSensor("Drivetrain3", "VelStabR", velocityStabilizerR);

		velocityStabilizerL = new PIDController(.05, 0.01, 0.00000, new PIDRateEncoder(leftEncoder), leftWheel);
		velocityStabilizerL.startLiveWindowMode();
		velocityStabilizerL.enable();
		LiveWindow.addSensor("Drivetrain3", "VelStabL", velocityStabilizerL);

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



	public void drive(double speed) {
		drive(speed, speed);

	}
	public void driveRaw(double speedL, double speedR) {
		rightWheel.set(speedR);
		leftWheel.set(speedL);
	}

	public void stop() {
		drive(0);
		velocityStabilizerL.disable();
		velocityStabilizerR.disable();
	}

	public void drive(double speedL, double speedR) { 
		speedR *= 10;
		speedL *= 10;
		velocityStabilizerL.enable();
		velocityStabilizerR.enable();
		velocityStabilizerL.setSetpoint(speedL);
		velocityStabilizerR.setSetpoint(speedR); 

		SmartDashboard.putNumber("Drive Speed L", speedL);
		SmartDashboard.putNumber("Drive Speed R", speedR);
		SmartDashboard.putNumber("Drive Right Encoder", rightEncoder.getRate());
		SmartDashboard.putNumber("Drive Left Encoder", leftEncoder.getRate());
	}

	public void driveToDistance(double feet) {
		positionControllerL.setSetpoint(leftEncoder.getDistance() + feet);
		positionControllerR.setSetpoint(rightEncoder.getDistance() + feet);
	}
	
	public void driveToAngleAndForward(double theta, double inches) {
		
		
	}

	public void enablePID() {

		positionControllerR.enable();
		positionControllerL.enable();
		driftStabilizerR.enable();
		driftStabilizerL.enable();
	}

	public void disablePID() {
		driftStabilizerR.disable();
		driftStabilizerL.disable();
		positionControllerR.disable();
		positionControllerL.disable();
	}

	public void updatePID() {
		enablePID();
		
		driftStabilizerR.setSetpoint(controlledPositionR.pidGet());
		driftStabilizerL.setSetpoint(controlledPositionL.pidGet());
		//velocityStabilizerL.setSetpoint(stabilizedDriftL.pidGet());
		//velocityStabilizerR.setSetpoint(stabilizedDriftR.pidGet());
		drive(controlledPositionL.pidGet(), controlledPositionR.pidGet());
		//drive(stabilizedDriftL.pidGet(), stabilizedDriftR.pidGet());


		SmartDashboard.putNumber("PosConR", positionControllerR.getSetpoint());
		SmartDashboard.putNumber("PosConL", positionControllerL.getSetpoint());
		SmartDashboard.putNumber("PosR", getPosR());
		SmartDashboard.putNumber("PosL", getPosL());
		SmartDashboard.putNumber("PosConR.O", positionControllerR.get());
		SmartDashboard.putNumber("PosConL.O", positionControllerL.get());
		SmartDashboard.putNumber("ConPosR", controlledPositionR.pidGet());
		SmartDashboard.putNumber("ConPosL", controlledPositionL.pidGet());
		SmartDashboard.putNumber("driftStabR", driftStabilizerR.getSetpoint());
		SmartDashboard.putNumber("driftStabL", driftStabilizerL.getSetpoint());
		SmartDashboard.putNumber("stabDriftR", driftStabilizerR.get());
		SmartDashboard.putNumber("stabDriftL", driftStabilizerL.get());
		SmartDashboard.putNumber("VelStabR", velocityStabilizerR.getSetpoint());
		SmartDashboard.putNumber("VelStabL", velocityStabilizerL.getSetpoint());
	}

	public void driveFPS(double speedL, double speedR) {
		velocityStabilizerL.setSetpoint(speedL);
		velocityStabilizerR.setSetpoint(speedR);
	}
	
	public static void main(String... args) throws IOException {
		TrapezoidalMotionProfile.test(20);
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
class TrapezoidalMotionProfile {
	double maxV;      // ft/ms
	double acc;       // ft/ms^2
	double distance;  // ft
	int dt;           // ms 
	ArrayList<Double> timePos = new ArrayList<Double>();
	ArrayList<Double> timeVel = new ArrayList<Double>();
	public TrapezoidalMotionProfile(double maxV, double acc, double distance, int dt) {
		this.maxV     = maxV / 1000;
		this.acc      = acc / 1000000; //from ft/s^2 to ft/ms^2
		this.distance = distance;
		this.dt       = dt;
		generateProfile();
	}
	public void generateProfile() {
		int t = 0;              // ms
		double pos = 0;         // ft
		double curV = 0;        // ft/ms
		while (pos != distance) {
			
		double t1 = maxV / acc; // stops accelerating
		double t2 = (distance / maxV); //fun math to get this number, stops decelerating
		if(distance <= maxV * maxV / acc) {
			System.out.println("Triangle "+maxV * maxV / acc);
			t1 = maxV / acc;
			t2 = maxV / acc;
		}
		double t3 = t2 + t1; //stops
		if(t < t1) {
			pos = .5 * acc * t * t;
			curV = acc * t;
		}
		else if(t < t2) {
			pos = .5 * acc * t1 * t1 + maxV * (t-t1);
			curV = maxV;
		}
		else if(t < t3) {
			pos = maxV * (t - t2) - .5 * acc * (t - t2) * (t - t2) + maxV * (t2 - t1) + .5 * acc * t1 * t1; 
			curV = maxV - acc * (t - t2);
		}
		else{
			pos = distance;
			curV = 0;
		}
			
		t += dt;
			timePos.add(pos);
			timeVel.add(curV);
			System.out.println("Entry: " + t + " " + curV + " " + pos);
		}

	}
	public double posAtTime(int t) {
		return timePos.get(t/dt);
	}
	public double velAtTime(int t) {
		return timeVel.get(t/dt);
	}
	public void exportProfile() throws IOException {
		FileWriter out = new FileWriter(new File("C:\\Users\\AW\\FRC2016\\3182 FRC 2016\\TrapProfile_" + distance + ".csv"));
		for(int i = 0; i < timePos.size(); i++) {
			out.write(String.format("%d, %f.5, %f.5 %n", i * dt, timePos.get(i), timeVel.get(i)));

		}
		out.close();
	}
	public static void test(double dist) throws IOException {
		TrapezoidalMotionProfile tmp = new TrapezoidalMotionProfile(10, 5, 20, 20); //TODO change maxV and acc
		tmp.exportProfile();
	}
}
