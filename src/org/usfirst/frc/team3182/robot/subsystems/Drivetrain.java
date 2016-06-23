package org.usfirst.frc.team3182.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3182.robot.RobotMap;
import org.usfirst.frc.team3182.robot.commands.DriveControl;
import java.io.IOException;

/**
 * Complete except for encoder stuff
 * Tested with demo-bot
 */

public class Drivetrain extends Subsystem {

RobotDrive drive;
	Talon[] wheels;
	Talon leftWheel;
	Talon rightWheel;
	Encoder rightEncoder, leftEncoder;
	PIDController positionControllerR,
	positionControllerL, 
	velocityStabilizerR, 
	velocityStabilizerL;
	Gyro gyro = new AnalogGyro(RobotMap.gyro);

	private PIDWrapper controlledPositionR, controlledPositionL;

	double speedR, speedL;

	public Drivetrain() {
		if(RobotMap.isArcade) drive= new RobotDrive(RobotMap.leftWheel,RobotMap.rightWheel);
		else{
			leftWheel = new Talon(RobotMap.leftWheel);
			rightWheel = new Talon(RobotMap.rightWheel);
			leftWheel.setInverted(true);
		
		wheels = new Talon[2];
		wheels[0] = leftWheel;
		wheels[1] = rightWheel;
		}
		
		
		gyro.calibrate();
		SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
		SmartDashboard.putNumber("Gyro Rate", gyro.getRate());
		
		

		rightEncoder = new Encoder(RobotMap.rightEncoder_A, RobotMap.rightEncoder_B);
		leftEncoder = new Encoder(RobotMap.leftEncoder_A, RobotMap.leftEncoder_B, true);

		rightEncoder.setDistancePerPulse(.00581776417); //old value .002909
		leftEncoder.setDistancePerPulse(.00581776417);
//
//		LiveWindow.addSensor(getName(), "Right Encoder", rightEncoder);
//		LiveWindow.addSensor(getName(), "Left Encoder", leftEncoder);
//		LiveWindow.addActuator(getName(), "Right Wheels", rightWheel);
//		LiveWindow.addActuator(getName(), "Left Wheels", leftWheel);

//
//		controlledPositionR = new PIDWrapper();
//		positionControllerR = new PIDController(0.1, 0.000, 0.001, new PIDDistanceEncoder(rightEncoder), controlledPositionR);
//		positionControllerR.startLiveWindowMode();
//		positionControllerR.setAbsoluteTolerance(0.1);
//		//positionControllerR.enable();
//		//LiveWindow.addSensor("Drivetrain", "PosConR", positionControllerR);
//
//		controlledPositionL = new PIDWrapper();
//		positionControllerL = new PIDController(0.1, 0.000, 0.001, new PIDDistanceEncoder(leftEncoder), controlledPositionL);		
//		positionControllerL.startLiveWindowMode();
//		positionControllerL.setAbsoluteTolerance(0.1);
//		//positionControllerL.enable();
//		//LiveWindow.addSensor("Drivetrain", "PosConL", positionControllerL);
//
//
//		velocityStabilizerR = new PIDController(.05, 0.01, 0.00000, new PIDRateEncoder(rightEncoder), rightWheel);
//		velocityStabilizerR.startLiveWindowMode();
//		velocityStabilizerR.enable();
//		//LiveWindow.addSensor("Drivetrain2", "VelStabR", velocityStabilizerR);
//
//		velocityStabilizerL = new PIDController(.05, 0.01, 0.00000, new PIDRateEncoder(leftEncoder), leftWheel);
//		velocityStabilizerL.startLiveWindowMode();
//		velocityStabilizerL.enable();
		//LiveWindow.addSensor("Drivetrain2", "VelStabL", velocityStabilizerL);

		System.out.println("Drivetrain init");
	}

	public void reset() {
//		rightEncoder.reset();
//		leftEncoder.reset();
		gyro.reset();

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
	public double getGyroAngle() { //useful units would be helpful but not necessary
		return gyro.getAngle();
	}
	public double getGyroRate() {
		return gyro.getRate();
	}
	public void driveRaw(double speedL, double speedR) {
		if(!RobotMap.isArcade){
		rightWheel.set(speedR); 
		leftWheel.set(speedL);
		}
		
		SmartDashboard.putNumber("speedL", speedL);
		SmartDashboard.putNumber("speedR", speedR);

//		SmartDashboard.putNumber("Drive Right Encoder", rightEncoder.getRate());
//		SmartDashboard.putNumber("Drive Left Encoder", leftEncoder.getRate());
	}
	public void driveArcade(double speedF, double speedSide) {
		drive.arcadeDrive(speedF,speedSide);
	}

	public void stop() {
		if(!RobotMap.isArcade){
			drive(0);
		
			driveRaw(0, 0);
		}
		else {
			drive.arcadeDrive(0,0);
		}
//		setPIDFF(velocityStabilizerL, 0);
//		setPIDFF(velocityStabilizerR, 0);
//		velocityStabilizerL.disable();
//		velocityStabilizerR.disable();
	}

	public void drive(double speedL, double speedR) { 
		speedR *= 10;
		speedL *= 10;
//		velocityStabilizerL.enable();
//		velocityStabilizerR.enable();
//		velocityStabilizerL.setSetpoint(speedL);
//		velocityStabilizerR.setSetpoint(speedR); 

	}

	public void driveToDistancePID(double distance) {
//		positionControllerL.setSetpoint(distance);
//		positionControllerR.setSetpoint(distance);
	}
	public void driveToAnglePID(double theta) {
//		positionControllerL.setSetpoint(theta);
//		positionControllerR.setSetpoint(-theta);
	}
	
	public void setPIDFF(PIDController pid, double ff) {
//		pid.setPID(pid.getP(), pid.getI(), pid.getD(), ff);
	}
	

	public void enablePID() {

//		positionControllerR.enable();
//		positionControllerL.enable();
//		driftStabilizerR.enable();
//		driftStabilizerL.enable();
	}

	public void disablePID() {
//		driftStabilizerR.disable();
//		driftStabilizerL.disable();
//		positionControllerR.disable();
//		positionControllerL.disable();
	}

	public void updatePID() {
//		enablePID();
//		
////		driftStabilizerR.setSetpoint(controlledPositionR.pidGet());
////		driftStabilizerL.setSetpoint(controlledPositionL.pidGet());
//		drive(controlledPositionL.pidGet(), controlledPositionR.pidGet());
//		
//
//		SmartDashboard.putNumber("PosConR", positionControllerR.getSetpoint());
//		SmartDashboard.putNumber("PosConL", positionControllerL.getSetpoint());
//		SmartDashboard.putNumber("PosR", getPosR());
//		SmartDashboard.putNumber("PosL", getPosL());
//		SmartDashboard.putNumber("PosConR.O", positionControllerR.get());
//		SmartDashboard.putNumber("PosConL.O", positionControllerL.get());
//		SmartDashboard.putNumber("ConPosR", controlledPositionR.pidGet());
//		SmartDashboard.putNumber("ConPosL", controlledPositionL.pidGet());
//		SmartDashboard.putNumber("VelStabR", velocityStabilizerR.getSetpoint());
//		SmartDashboard.putNumber("VelStabL", velocityStabilizerL.getSetpoint());
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
