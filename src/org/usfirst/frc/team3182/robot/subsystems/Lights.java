package org.usfirst.frc.team3182.robot.subsystems;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3182.robot.commands.LightsControl;
import org.usfirst.frc.team3182.robot.util.Animation;

import java.awt.*;

public class Lights extends Subsystem {

    public final int LENGTH = 10; // Length of one strip FIXME: Change with actual size
    private final int BRIGHTNESS = 1; // 1=full, 0=none
    private SPI spi;

    // Collector is the front of the robot
    // rightStrip[0] and leftStrip[0] are the two front strips
    private Color[] strip; // Two parallel strips are always set to the same thing

    public Lights() {
        System.out.println("Lights Init");
        spi = new SPI(SPI.Port.kMXP);
        spi.setMSBFirst(); // Transfers each byte backwards
        strip = new Color[LENGTH];
    }

   @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new LightsControl(Animation.DEFAULT));
    }

    public void displayStrip() {
        // Send pixles array to strip
        for (int i = LENGTH - 1; i > 0; i--) {
            byte[] buffer = {0, 0, 0};
            buffer[0] = (byte) strip[i].getRed();
            buffer[1] = (byte) strip[i].getGreen();
            buffer[2] = (byte) strip[i].getBlue();
            spi.write(buffer, 3);
        }
        for (int i = 0; i < LENGTH; i++) {
            byte[] buffer = {0, 0, 0};
            buffer[0] = (byte) (strip[i].getRed() * BRIGHTNESS);
            buffer[1] = (byte) (strip[i].getGreen() * BRIGHTNESS);
            buffer[2] = (byte) (strip[i].getBlue() * BRIGHTNESS);
            spi.write(buffer, 3);
        }
        Timer.delay(.001); // Marks next frame
    }

    public void setPixel(int index, Color c) {
        // Sets a specific pixel on both strips
        if (index >= LENGTH) {
            System.out.println("INVALID PIXEL INDEX");
            return;
        }
        strip[index] = c;
        strip[index] = c;
    }

    public Color[] getStrip() {
        return strip;
    }

    public void setStrip(Color[] c) {
        // Sets color of pixel without sending it to the strip
        System.arraycopy(c, 0, strip, 0, c.length);
    }

    public void clear() {
        for (int i = 0; i < LENGTH; i++) {
            strip[i] = Color.black;
        }
        displayStrip();
    }

    public void shiftLightsForward() {
        Color temp = strip[0];
        System.arraycopy(strip, 1, strip, 0, LENGTH - 1);
        strip[LENGTH - 1] = temp;
    }

    public void shiftLightsBackwards() {
        Color temp = strip[LENGTH - 1];
        System.arraycopy(strip, 0, strip, 1, LENGTH - 2);
        strip[0] = temp;
    }

    public void fountain() {
        Color temp1 = strip[0];
        Color temp2 = strip[LENGTH - 1];

        if (LENGTH % 2 == 1) {
            System.arraycopy(strip, 1, strip, 0, LENGTH/2-1);
            System.arraycopy(strip, LENGTH/2+1, strip, LENGTH/2+1, LENGTH/2-1);

            strip[LENGTH / 2-1] = temp1;
            strip[LENGTH / 2 ] = temp2;
        } else {
            System.arraycopy(strip, 1, strip, 0, LENGTH / 2-1 );
            System.arraycopy(strip, LENGTH / 2 , strip, LENGTH / 2 + 1, LENGTH / 2-1 );

            strip[LENGTH / 2-1] = temp1;
            strip[LENGTH / 2] = temp2;
        }
    }
}