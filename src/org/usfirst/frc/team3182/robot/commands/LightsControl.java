package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.Robot;
import org.usfirst.frc.team3182.robot.subsystems.Lights;

import edu.wpi.first.wpilibj.command.Command;

public class LightsControl extends Command {

    public LightsControl() {
 //       requires(Robot.lights);
    }


    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        //FIXME: shouldn't be interrupted
    }

}
