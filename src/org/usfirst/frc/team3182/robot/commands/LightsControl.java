package org.usfirst.frc.team3182.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3182.robot.Robot;
import org.usfirst.frc.team3182.robot.util.Animation;

import java.awt.*;

public class LightsControl extends Command {


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
        switch (animation){
            case CELEBRATE:
                for (int i=0;i<Robot.lights.LENGTH;i++){ //set the strip to random colors
                    Robot.lights.setPixel(i, new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
                }
                while(true) {
                    Robot.lights.shiftLights(true);
                    Robot.lights.displayStrip();
                    Timer.delay(.3);
                }
            case COLLECT:

                break;
            case EXPEL:

                break;
            default:

                break;
        }

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.lights.clear();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
