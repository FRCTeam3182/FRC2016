package org.usfirst.frc.team3182.robot.subsystems;


import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3182.robot.commands.LightsControl;
import org.usfirst.frc.team3182.robot.util.Animation;

import java.awt.*;

public class Lights extends Subsystem {
	
	private SPI spi;

	// Collector is the front of the robot
	// rightStrip[0] and leftStrip[0] are the two front strips
	Color[] rightStrip; // Stage right
	Color[] leftStrip;

	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new LightsControl(Animation.DEFAULT));
	}
	
	public Lights() {
		System.out.println("Lights Init");
		spi = new SPI(SPI.Port.kMXP);
		spi.setMSBFirst(); // Transfers each byte backwards

	}

	public void displayStrip() {
		// Send pixles array to strip
		for(int i = leftStrip.length-1; i > 0; i--) {
			byte[] buffer = {0,0,0};
			buffer[0] = (byte)leftStrip[i].getRed(); //FIXME change order
			buffer[1] = (byte)leftStrip[i].getGreen();
			buffer[2] = (byte)leftStrip[i].getBlue();
			spi.write(buffer, 3);
		}
		for(int i = 0; i < leftStrip.length; i++) {
			byte[] buffer = {0,0,0};
			buffer[0] = (byte)leftStrip[i].getRed(); //FIXME change order
			buffer[1] = (byte)leftStrip[i].getGreen();
			buffer[2] = (byte)leftStrip[i].getBlue();
			spi.write(buffer, 3);
		}
		Timer.delay(.001); // Marks next frame
	}

	public void setBothStrips(Color[] c) {
		// Sets color of pixel without sending it to the strip
		for(int i=0; i<c.length; i++){
			rightStrip[i]= c[i];
			leftStrip[i]= c[i];
		}
	}

	public Color[] getLeftStrip() {
		return leftStrip;
	}
	public Color[] getRightStrip() {
		return rightStrip;
	}
}


