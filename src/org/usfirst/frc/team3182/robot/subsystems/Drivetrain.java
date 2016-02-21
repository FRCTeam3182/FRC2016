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
	velocityStabilizerL;
//	driftStabilizerR, 
//	driftStabilizerL;

	double speedR, speedL;
	final double maxVelocity = 8.0, maxAcceleration = 1.5; //TODO: come up with useful values
	final double maxTurnVelocity = 5.0, maxTurnAcceleration = .75;
	private PIDWrapper controlledPositionR,
	controlledPositionL;
//	stabilizedDriftR,
//	stabilizedDriftL;	
	
	TrapezoidalMotionProfile tmp;
	TMPTurn tmpturn;

	public Drivetrain() {
		
		leftWheel = new Talon(RobotMap.leftWheel);
		rightWheel = new Talon(RobotMap.rightWheel);
		wheels = new Talon[2];
		wheels[0] = leftWheel;
		wheels[1] = rightWheel;
		
		leftWheel.setInverted(true);

		rightEncoder = new Encoder(RobotMap.rightEncoder_A, RobotMap.rightEncoder_B);
		leftEncoder = new Encoder(RobotMap.leftEncoder_A, RobotMap.leftEncoder_B, true);

		rightEncoder.setDistancePerPulse(.006981317); //old value .002909
		leftEncoder.setDistancePerPulse(.006981317);

		LiveWindow.addSensor(getName(), "Right Encoder", rightEncoder);
		LiveWindow.addSensor(getName(), "Left Encoder", leftEncoder);
		LiveWindow.addActuator(getName(), "Right Wheels", rightWheel);
		LiveWindow.addActuator(getName(), "Left Wheels", leftWheel);


		controlledPositionR = new PIDWrapper();
		positionControllerR = new PIDController(0.1, 0.000, 0.001, new PIDDistanceEncoder(rightEncoder), controlledPositionR);
		positionControllerR.startLiveWindowMode();
		positionControllerR.setAbsoluteTolerance(0.1);
		//positionControllerR.enable();
		//LiveWindow.addSensor("Drivetrain", "PosConR", positionControllerR);

		controlledPositionL = new PIDWrapper();
		positionControllerL = new PIDController(0.1, 0.000, 0.001, new PIDDistanceEncoder(leftEncoder), controlledPositionL);		
		positionControllerL.startLiveWindowMode();
		positionControllerL.setAbsoluteTolerance(0.1);
		//positionControllerL.enable();
		//LiveWindow.addSensor("Drivetrain", "PosConL", positionControllerL);


		velocityStabilizerR = new PIDController(.05, 0.01, 0.00000, new PIDRateEncoder(rightEncoder), rightWheel);
		velocityStabilizerR.startLiveWindowMode();
		velocityStabilizerR.enable();
		//LiveWindow.addSensor("Drivetrain2", "VelStabR", velocityStabilizerR);

		velocityStabilizerL = new PIDController(.05, 0.01, 0.00000, new PIDRateEncoder(leftEncoder), leftWheel);
		velocityStabilizerL.startLiveWindowMode();
		velocityStabilizerL.enable();
		//LiveWindow.addSensor("Drivetrain2", "VelStabL", velocityStabilizerL);

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
	
	public String getName() {
		return "Drivetrain";
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

		SmartDashboard.putNumber("Drive Right Encoder", rightEncoder.getRate());
		SmartDashboard.putNumber("Drive Left Encoder", leftEncoder.getRate());
	}

	public void stop() {
		drive(0);
		driveRaw(0, 0);
		setPIDFF(velocityStabilizerL, 0);
		setPIDFF(velocityStabilizerR, 0);
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
	}

	public void driveToDistancePID(double distance) {
		positionControllerL.setSetpoint(distance);
		positionControllerR.setSetpoint(distance);
	}
	public void initD2D(double distance) {
		tmp = new TrapezoidalMotionProfile(maxVelocity, maxAcceleration, distance, 20);
	}
	public void updateD2D(int t) {
		positionControllerL.setSetpoint(tmp.posAtTime(t));
		positionControllerR.setSetpoint(tmp.posAtTime(t));
		
		setPIDFF(velocityStabilizerL, tmp.velAtTime(t));
		setPIDFF(velocityStabilizerR, tmp.velAtTime(t));
		
	}
	
	public void setPIDFF(PIDController pid, double ff) {
		pid.setPID(pid.getP(), pid.getI(), pid.getD(), ff);
	}
	
	public void initD2A(double theta) { //theta is bearing
		tmpturn = new TMPTurn(maxTurnVelocity, maxTurnAcceleration, theta, 20);
	}
	public void updateD2A(int t) {
		positionControllerL.setSetpoint(tmpturn.posAtTimeL(t));
		positionControllerR.setSetpoint(tmpturn.posAtTimeR(t));
		
		setPIDFF(velocityStabilizerL, tmpturn.velAtTimeL(t));
		setPIDFF(velocityStabilizerR, tmpturn.velAtTimeR(t));
		
	}

	public void enablePID() {

		positionControllerR.enable();
		positionControllerL.enable();
//		driftStabilizerR.enable();
//		driftStabilizerL.enable();
	}

	public void disablePID() {
//		driftStabilizerR.disable();
//		driftStabilizerL.disable();
		positionControllerR.disable();
		positionControllerL.disable();
	}

	public void updatePID() {
		enablePID();
		
//		driftStabilizerR.setSetpoint(controlledPositionR.pidGet());
//		driftStabilizerL.setSetpoint(controlledPositionL.pidGet());
		drive(controlledPositionL.pidGet(), controlledPositionR.pidGet());
		

		SmartDashboard.putNumber("PosConR", positionControllerR.getSetpoint());
		SmartDashboard.putNumber("PosConL", positionControllerL.getSetpoint());
		SmartDashboard.putNumber("PosR", getPosR());
		SmartDashboard.putNumber("PosL", getPosL());
		SmartDashboard.putNumber("PosConR.O", positionControllerR.get());
		SmartDashboard.putNumber("PosConL.O", positionControllerL.get());
		SmartDashboard.putNumber("ConPosR", controlledPositionR.pidGet());
		SmartDashboard.putNumber("ConPosL", controlledPositionL.pidGet());
		SmartDashboard.putNumber("VelStabR", velocityStabilizerR.getSetpoint());
		SmartDashboard.putNumber("VelStabL", velocityStabilizerL.getSetpoint());
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
			t1 = Math.sqrt(distance / acc); //tbh not sure why it's not 2 times this but this works
			t2 = t1;
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
			//System.out.println("Entry: " + t + " " + curV + " " + pos);
		}

	}
	public double posAtTime(int t) {
		return timePos.get(t/dt);
	}
	public double velAtTime(int t) {
		return timeVel.get(t/dt) * 1000; //back to ft/sec
	}
	public void exportProfile() throws IOException {
		FileWriter out = new FileWriter(new File("C:\\Users\\AW\\FRC2016\\3182 FRC 2016\\TrapProfile_" + distance + ".csv"));
		for(int i = 0; i < timePos.size(); i++) {
			out.write(String.format("%d, %f, %f %n", i * dt, timePos.get(i), timeVel.get(i)));

		}
		out.close();
	}
	public static void test(double dist) throws IOException {
		TrapezoidalMotionProfile tmp = new TrapezoidalMotionProfile(10, 5, 5, 20); //TODO change maxV and acc
		tmp.exportProfile();
	}
}
class TMPTurn extends TrapezoidalMotionProfile {
	static final double radius = 12.125; // inches
	
	/**
	 * theta is bearing - degrees from the forward direction going right (negative should work)
	 */
	public TMPTurn(double maxV, double acc, double theta, int dt) {
		super(maxV, acc, theta * Math.PI / 180 * radius, dt); //convert to radians and divide by radius
	}
	
	public double posAtTimeR(int t) {
		return posAtTime(t);
	}
	public double posAtTimeL(int t) {
		return -posAtTime(t);
	}
	public double velAtTimeR(int t) {
		return velAtTime(t);
	}
	public double velAtTimeL(int t) {
		return -velAtTime(t);
	}
}
