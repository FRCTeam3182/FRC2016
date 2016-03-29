package org.usfirst.frc.team3182.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3182.robot.Robot;
import org.usfirst.frc.team3182.robot.util.Animation;
import org.usfirst.frc.team3182.robot.util.Color;


public class LightsControl extends Command {


    private Animation animation;

    public LightsControl(Animation a) {
        requires(Robot.lights);
        animation = a;
        execute();
    }


    @Override
    protected void initialize() {
        switch (animation){
            case CELEBRATE:
                for (int i=0;i<Robot.lights.LENGTH;i++){ // Set the strip to random colors
                    Robot.lights.setPixel(i, new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
                }
            case COLLECT:
                for (int i=0;i<Robot.lights.LENGTH;i++) { // Set the strip to an even pattern
                    Robot.lights.setPixel(i,i%6<3 ? (new Color(255,0,200)): new Color(0,0,0));
                }
                break;
            case EXPEL:
                for (int i=0;i<Robot.lights.LENGTH;i++) { // Set the strip to an even pattern
                    Robot.lights.setPixel(i,i%6<3 ? (new Color(255,0,200)): new Color(0,0,0));
                }
                break;
            default:
                for (int i=0;i<Robot.lights.LENGTH;i++) { // Set the strip to an even pattern
                    Robot.lights.setPixel(i,i%6<3 ? (new Color(255,0,200)): new Color(0,0,0));
                }
                break;
        }

    }

    @Override
    protected void execute() {
        switch (animation){
            case CELEBRATE:
                    Robot.lights.fountain();
                    Robot.lights.displayStrip();
                    delay(.5);
            case COLLECT:
                Robot.lights.shiftLightsBackwards();
                Robot.lights.displayStrip();
                delay(.5);
                break;
            case EXPEL:
                Robot.lights.shiftLightsForward();
                Robot.lights.displayStrip();
                delay(.5);
                break;
            default:
                Robot.lights.fountain();
                Robot.lights.displayStrip();
                delay(.5);
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

    private void delay(double sec){
        for (int i = 0; i<sec*100;i++){
            if(isCanceled()) return;
            //Timer.delay(.01);
        }
    }

    /*
    // FOR TESTING
    public static void main(String args[]){

        Lights l = new Lights();
        l.setPixel(8, new Color(255, 0, 0));
        for (int i=0;i<5; i++) {
            for (Color c : l.getStrip()) System.out.print(c + " ");
            System.out.println("");
            l.fountain();
        }
    }*/
}
