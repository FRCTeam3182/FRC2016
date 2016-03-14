package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;
import org.usfirst.frc.team3182.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

public class ArmControl extends Command {

	public ArmControl() {
		requires(Robot.arm);
	}


	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		
		
			if(Robot.arm.getPmeter().getVoltage() < Arm.potentiometerUpperLim && 
			  Robot.arm.getPmeter().getVoltage() > Arm.potentiometerLowerLim)
			Robot.arm.runRaw(-Robot.oi.getPowerGloveTilt() * 0.4);
			else if(Robot.arm.getPmeter().getVoltage() > Arm.potentiometerUpperLim) Robot.arm.runRaw(-0.2);
			else if(Robot.arm.getPmeter().getVoltage() < Arm.potentiometerLowerLim) Robot.arm.runRaw(0.2);
			else Robot.arm.runRaw(0);
			//Robot.arm.runRaw(0);
				;
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.arm.runRaw(0);
	}

	@Override
	protected void interrupted() {
		end();

	}

}
