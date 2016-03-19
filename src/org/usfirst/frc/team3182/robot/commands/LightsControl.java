package org.usfirst.frc.team3182.robot.commands;

import org.usfirst.frc.team3182.robot.util.Animation;
import org.usfirst.frc.team3182.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LightsControl extends Command {

    private boolean isKill = false; //set to true to kill the current animation
    private Animation animation;

    public LightsControl(Animation a) {
        requires(Robot.lights);
        animation = a;
        execute();
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
        isKill = false;
    }

}
