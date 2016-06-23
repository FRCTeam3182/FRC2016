package org.usfirst.frc.team3182.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3182.robot.commands.CollectorControl;
import org.usfirst.frc.team3182.robot.commands.DriveToDistance;
import org.usfirst.frc.team3182.robot.commands.LightsControl;
import org.usfirst.frc.team3182.robot.util.Animation;

/**
 * OI: Operator Interface
 * 
 * This class queries and returns information about the driver's controls such as the joysticks and the power glove.
 */
public class OI {

    // Init joystick mappings
    Joystick driveStickR = new Joystick(RobotMap.driveStickR);
    JoystickButton buttonTestAuto1 = new JoystickButton(driveStickR, 10); //FIXME: Remove before competition
    
    // [PB, 2016-06-11, 9:24]: buttons for collector at WIWI
    JoystickButton buttonExpel   = new JoystickButton(driveStickR, 11);
    JoystickButton buttonCollect = new JoystickButton(driveStickR, 10); //FIXME: Remove before competition

    Joystick driveStickL = new Joystick(RobotMap.driveStickL);
    
    // Init a test button
    JoystickButton buttonTestAuto1 = new JoystickButton(driveStickR, 10); //FIXME: Remove before competition

    // Init the power glove as a joystick and a few buttons from it as well
    Joystick powerGlove = new Joystick(RobotMap.powerGlove);
    JoystickButton pgButton1 = new JoystickButton(powerGlove, 1);
    JoystickButton pgButton2 = new JoystickButton(powerGlove, 2);
    JoystickButton pgButton3 = new JoystickButton(powerGlove, 3);
    JoystickButton pgButton4 = new JoystickButton(powerGlove, 4);

    // OI class constructor
    public OI() {

        // Toggles the DriveToDistance class when the joystick test button is pressed
        buttonTestAuto1.toggleWhenPressed(new DriveToDistance(3));
        
        // Print a little message to the console
        System.out.println("OI init");
               	
        // Operate as if we have a power glove connected
        Robot.usesPowerGlove = true;
        System.out.println("Using PowerGlove");
        pgButton2.whenPressed(new LightsControl(Animation.CELEBRATE));
        
        // Update the smart dashboard with some drivetrain info
        SmartDashboard.putData(Robot.drivetrain);
    }

    public double getCollectValue() {
    	/*if( driveStickL.getRawButton(3)) return -.65;
    	if( driveStickR.getRawButton(3)) return 1;
    	return 0;*/
    	
        //if (!pgButton4.get())
        //    return 0;
        //else
        //    return pgButton3.get() ? -.65 : 1;
    	
    	// [PB, 2016-06-11, 9:27]: button based collect and expel at WIWI
        if (buttonCollect.get()) {
            return -0.65;
        } else if (buttonExpel.get()) {
            return 1;
        } else {
        	return 0;
        }
    }

    public double getL() {
       		if(Math.abs(driveStickL.getY())<.45)return 0; // Deadzone
       		return driveStickR.getRawButton(1) || driveStickL.getRawButton(1) ? driveStickL.getY() : driveStickL.getY()*.8;
    }


    public double getR() {
    	if(Math.abs(driveStickR.getY())<.25)return 0; // Deadzone
//    	SmartDashboard.putNumber("joyR", driveStickR.getY());
        return driveStickR.getRawButton(1) || driveStickL.getRawButton(1) ? driveStickR.getY() : driveStickR.getY()*.8;
    
    }
  
    public double getLExp() { //"ramps up"
    	if(Math.abs(getL())<.1)return 0; // Deadzone
    	if (getL() > 0) return Math.pow(getL(), 2);
        else return -Math.abs(Math.pow(getL(), 2));
    }

    public double getRExp() {
    	if(Math.abs(getR())<.1) return 0; // Deadzone
        if (getR() > 0) return Math.pow(getR(), 2);
        else return -Math.abs(Math.pow(getR(), 2));
    }

    public double getPowerGloveTilt() {
        return powerGlove.getY();
    }
    public double getForwardArcade() {
    		 if (!pgButton4.get())
                return 0;
            else
                return pgButton3.get() ? -.6 : .6;
    }
    public double getSideArcade(){
    	if(Math.abs(powerGlove.getX())<.25)return 0; // Deadzone
    	return -powerGlove.getX()*.9;
    }

}

	
