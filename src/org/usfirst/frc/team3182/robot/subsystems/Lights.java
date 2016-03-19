package org.usfirst.frc.team3182.robot.subsystems;


import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3182.robot.commands.LightsControl;
import org.usfirst.frc.team3182.robot.util.Animation;

import java.awt.*;

public class Lights extends Subsystem {
	
	SPI spi;
	Color[] pixels;

	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new LightsControl(Animation.DEFAULT));
	}
	
	public Lights() {
		spi = new SPI(SPI.Port.kMXP);
		spi.setMSBFirst(); // Transfers each bit backwards
	}

	public void displayStrip() {
		// Send pixles array to strip
		for(int i = 0; i < pixels.length; i++) {
			byte[] buffer = {0,0,0};
			buffer[0] = (byte)pixels[i].getRed(); //FIXME change order
			buffer[1] = (byte)pixels[i].getGreen();
			buffer[2] = (byte)pixels[i].getRed();
			spi.write(buffer, 3);
		}
		Timer.delay(.001); // Marks next frame
	}

	public void setColor(int index , Color c) {
		// Sets color of pixel without sending it to the strip
		pixels[index] = c;
	}
	public void setColor(int index , int r, int g, int b) {
		// Sets color of pixel without sending it to the strip
		// Colors must be 0-255
		pixels[index] = new Color(r,g,b);
	}
}


