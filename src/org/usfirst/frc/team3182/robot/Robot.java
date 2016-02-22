
package org.usfirst.frc.team3182.robot;

import org.usfirst.frc.team3182.robot.commands.DriveToDistance;
import org.usfirst.frc.team3182.robot.subsystems.Arm;
import org.usfirst.frc.team3182.robot.subsystems.Collector;
import org.usfirst.frc.team3182.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	
	public static OI oi;
	public static Drivetrain drivetrain;
	public static Arm arm;
    public static Collector collector;
	

    Command autonomousCommand;
    SendableChooser chooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	drivetrain = new Drivetrain();
    	arm = new Arm();
    	collector = new Collector();
    	oi = new OI();
    	drivetrain.stop();
    	

    	int position = (int)SmartDashboard.getNumber("Position");
    	int defense = (int)SmartDashboard.getNumber("Defense");
    	
        chooser = new SendableChooser();
        chooser.addObject("DriveToDistance 8", new DriveToDistance(8.0));
        chooser.addDefault("DriveForward 5", new DriveToDistance(5.0));
        chooser.addObject("DriveForward 3", new DriveToDistance(3.0));
        chooser.addObject("DriveForward 1", new DriveToDistance(1.0));
        chooser.addDefault("AutoSelecter", new AutoSelector(position, defense));
        chooser.addObject("Null", null);
        SmartDashboard.putData("Auto mode", chooser);
        //SmartDashboard.putData(Scheduler.getInstance());
        System.out.println(Scheduler.getInstance().getName());
        
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	oi = new OI();

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();       
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
        LiveWindow.run();
        //Scheduler.getInstance().run();
    }
}
