
package org.usfirst.frc.team3182.robot;

import org.usfirst.frc.team3182.robot.commands.DriveToDistance;
import org.usfirst.frc.team3182.robot.commands.TimedDrive;
import org.usfirst.frc.team3182.robot.subsystems.Arm;
import org.usfirst.frc.team3182.robot.subsystems.Collector;
import org.usfirst.frc.team3182.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3182.robot.subsystems.Lights;

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
    public static Lights lights;

    public static boolean usesPowerGlove = true, hasPot = true;

    Command autonomousCommand;
    SendableChooser chooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	lights = new Lights();
    	drivetrain = new Drivetrain();
    	arm = new Arm();
    	collector = new Collector();
    	oi = new OI();
        drivetrain.stop();
    	

//    	long ms = (long)SmartDashboard.getNumber("MilliSeconds");
//    	double speed = (double)SmartDashboard.getNumber("Speed");
    	
        chooser = new SendableChooser();
//        chooser.addObject(".5 Second Fast", new TimedDrive(500, .8));
        chooser.addObject("2 Second Slow", new TimedDrive(2000, .5));
        chooser.addObject("2 Second Slow Back", new TimedDrive(2000, -.5));
        chooser.addObject("3.5 Second Fast", new TimedDrive(3500, .8));
        chooser.addObject("3.5 Second Slow", new TimedDrive(3500, .5));
        chooser.addObject("3.5 Second Back Slow", new TimedDrive(3500, -.5));
        chooser.addObject("3 Second Medium", new TimedDrive(3000, .7));
        chooser.addObject("3.5 Second Fast", new TimedDrive(3500, .9));
        chooser.addObject("4 Second Fast", new TimedDrive(4000, .8));
        chooser.addObject("4 Second Slow", new TimedDrive(4000, .5));
        chooser.addObject("4 Second Back slow", new TimedDrive(4000, -.5));
        chooser.addObject("4 Second Medium", new TimedDrive(4000, .7));
        chooser.addObject("8 Second Medium", new TimedDrive(8000, .7));
        
        // TODO Variable auto
 //       chooser.addObject("Variable", new TimedDrive(ms, speed));
        
        //chooser.addDefault("AutoSelecter", new AutoSelector(position, defense));
        chooser.addDefault("Null", null);
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
    	// TODO: Add something to smartdashboard that displays warning at inputted time
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
