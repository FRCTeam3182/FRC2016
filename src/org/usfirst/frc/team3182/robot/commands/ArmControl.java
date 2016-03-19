package org.usfirst.frc.team3182.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3182.robot.Robot;
import org.usfirst.frc.team3182.robot.subsystems.Arm;

public class ArmControl extends Command {

    public ArmControl() {
        requires(Robot.arm);
    }


    @Override
    protected void initialize() {
        System.out.println("Has Potentiometer: " + Robot.hasPot);
    }

    @Override
    protected void execute() {
        if (Robot.hasPot) {
            if (!Robot.usesPowerGlove) {
                // Using Joystick
                if (Robot.arm.getPmeter().getVoltage() < Arm.potentiometerUpperLim &&
                        Robot.arm.getPmeter().getVoltage() > Arm.potentiometerLowerLim)
                    Robot.arm.runRaw(-Robot.oi.getPowerGloveTilt());
                else if (Robot.arm.getPmeter().getVoltage() >= Arm.potentiometerUpperLim) Robot.arm.runRaw(-0.2);
                else if (Robot.arm.getPmeter().getVoltage() <= Arm.potentiometerLowerLim) Robot.arm.runRaw(0.3);
                else Robot.arm.runRaw(0);
            }
            else {
                // Using Power Glove
                Robot.arm.set(Robot.oi.getPowerGloveTilt() + 1); //roughly moves it from -.25-.8 to .75-2, FIXME the +1
            }
        } else
            if(Math.abs(Robot.oi.getPowerGloveTilt())>.3)
            Robot.arm.runRaw(-Robot.oi.getPowerGloveTilt());
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
