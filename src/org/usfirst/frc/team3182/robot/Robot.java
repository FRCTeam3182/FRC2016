
package org.usfirst.frc.team3182.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3182.robot.commands.TimedDrive;
import org.usfirst.frc.team3182.robot.commands.TimedVariableDrive;
import org.usfirst.frc.team3182.robot.subsystems.Arm;
import org.usfirst.frc.team3182.robot.subsystems.Collector;
import org.usfirst.frc.team3182.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3182.robot.subsystems.Lights;
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
    
    CameraServer server;

    DriverStation ds;
    private double warningTime=0;

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
        
        server = CameraServer.getInstance();
        server.setQuality(50);
        server.startAutomaticCapture("cam1");

        SmartDashboard.putNumber("Auto Speed (0.0 - 1.0)", .7);
        SmartDashboard.putNumber("Auto Time (ms)",        3000);
        
        chooser = new SendableChooser();
        chooser.addObject("3 second .7 speed", new TimedDrive(3000, .7));
        chooser.addDefault("Null", null);

        chooser.addObject("Variable", new TimedVariableDrive(this)); //i was a good boy and made a real class for this instead of an anonymous class (don't think i didn't want to)
        SmartDashboard.putData("Auto mode", chooser);

        System.out.println(Scheduler.getInstance().getName());

        ds = DriverStation.getInstance();
        
        CameraServer server = CameraServer.getInstance();
        server.setQuality(50);
        server.startAutomaticCapture("cam1");
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
//    	double autoSpeed_pct = SmartDashboard.getNumber("Auto Speed (0.0 - 1.0)", 1.0);
//        double autoTime_ms   = SmartDashboard.getNumber("Auto Time (ms)", 3000);
//        
//        TimedDrive td = new TimedDrive((long) autoTime_ms, autoSpeed_pct);
//        td.start();
        
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
        
        warningTime=SmartDashboard.getNumber("Warning Time (sec)", 45);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        // Warns Drivers at time
        if (ds.getMatchTime()<=warningTime) SmartDashboard.putString("Match Warning", ds.getMatchTime() + " second warning!!");
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
        LiveWindow.run();
        //Scheduler.getInstance().run();
    }
    
    public long getDSms() {
    	return (long)SmartDashboard.getNumber("Seconds", 0);
    }
    public double getDSspeed() {
    	return (double)SmartDashboard.getNumber("Speed", 0);
    }
}
