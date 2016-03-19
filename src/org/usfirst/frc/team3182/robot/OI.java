package org.usfirst.frc.team3182.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3182.robot.commands.CollectorControl;
import org.usfirst.frc.team3182.robot.commands.DriveToDistance;
import org.usfirst.frc.team3182.robot.commands.LightsControl;
import org.usfirst.frc.team3182.robot.util.Animation;

/**
 * Operator Interface
 *
 */
public class OI {


    Joystick driveStickR = new Joystick(RobotMap.driveStickR);
    JoystickButton buttonTestAuto1 = new JoystickButton(driveStickR, 10); //FIXME: Remove before competition

    Joystick driveStickL = new Joystick(RobotMap.driveStickL);

    Joystick powerGlove = new Joystick(RobotMap.powerGlove);
    JoystickButton pgButton1 = new JoystickButton(powerGlove, 1);
    JoystickButton pgButton2 = new JoystickButton(powerGlove, 2);
    JoystickButton pgButton3 = new JoystickButton(powerGlove, 3);
    JoystickButton pgButton4 = new JoystickButton(powerGlove, 4);

    public OI() {

        buttonTestAuto1.toggleWhenPressed(new DriveToDistance(3));

        System.out.println("OI init");
        if (powerGlove.getName().equals("Pro Micro")) { //TODO: Test this
            pgButton2.whenPressed(new LightsControl(Animation.CELEBRATE));
            Robot.usesPowerGlove = true;
            System.out.println("Using PowerGlove");
        } else {
            Robot.usesPowerGlove = false;
            System.out.println("Not Using PowerGlove");
            pgButton1.whenPressed(new CollectorControl(-1)); //Button 1, expel
            pgButton2.whenPressed(new CollectorControl(1)); //Button 2, intake
            pgButton3.whenPressed(new CollectorControl(0)); //Button 3, turn off (just in case, shouldn't be necessesary)
            pgButton4.whenPressed(new LightsControl(Animation.CELEBRATE));
        }

        SmartDashboard.putData("AutoDriveForward", new DriveToDistance(5));
        SmartDashboard.putData(Robot.drivetrain);

    }

    public int getCollectValue() {
        if (!pgButton4.get())
            return 0;
        else
            return pgButton3.get() ? -1 : 1;
    }

    public double getL() {
        return driveStickL.getY();
    }


    public double getR() {
        return driveStickR.getY();
    }

    public double getLExp() { //"ramps up" so 1/2 = 1/4, 3/4 = 9/16 (just over 1/2), 1 = 1 (just squares the value)
        if (getL() > 0) return getL() * getL();
        else return getL() * -getL();
    }

    public double getRExp() {
        if (getR() > 0) return getR() * getR();
        else return getR() * -getR();
    }

    public double getPowerGloveTilt() {
        return powerGlove.getY();
    }

}

	