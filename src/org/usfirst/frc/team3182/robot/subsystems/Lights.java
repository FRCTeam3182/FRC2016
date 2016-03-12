package org.usfirst.frc.team3182.robot.subsystems;

import java.awt.Color;
import java.io.*;
import java.util.Scanner;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Lights extends Subsystem {
	Animation currentAnimation;
	int currentFrame;
	Color[][] lights;
	@Override
	protected void initDefaultCommand() {
		
	}
	
	public void startAnimation(Animation animation) {
		currentAnimation = animation;
		currentFrame = 0;
	}
	
	public void display() {
		//put some lights on and some lights off
		for(int i = 0; i < lights.length; i++) {
			for(int j = 0; j < lights[i].length; j++){
				lights[i][j] = currentAnimation.get(i, j, currentFrame);
				//or somehow set the light color idk FIXME
			}
		}
	}
	
	public void setFrame(int frame) {
		currentFrame = frame;
	}
	
	public void update() {
		display();
		currentFrame++;
	}

}

enum Animation {
	exampleAnimation1("example1.anim");
	
	private int length;//top to bottom, y
	private int width; //left to right, x
	private int frames;//beginning to end
	private Color[][][] lights;
	
	Animation(String file)  {
		
		
		
		File f = new File("file");
		//time to make a format for the file
		try {
			Scanner fin = new Scanner(f);
			fin.nextLine();
			if(!fin.hasNextInt()) fin.next();
			this.frames = fin.nextInt();
			fin.nextLine();
			if(!fin.hasNextInt()) fin.next();
			this.width = fin.nextInt();
			fin.nextLine();
			if(!fin.hasNextInt()) fin.next();
			this.length = fin.nextInt();
			fin.nextLine();
			fin.nextLine();

			lights = new Color[frames][width][length];
			for(int i = 0; i < frames; i++) {
				for(int j = 0; j < length; j++) {
					for(int k = 0; k < width; k++) {
						int r = fin.nextInt();
						if(r < 0) r = (int)(Math.random() * 256);
						int g = fin.nextInt();
						if(r < 0) g = (int)(Math.random() * 256);
						int b = fin.nextInt();
						if(r < 0) b = (int)(Math.random() * 256);
						lights[i][j][k] = new Color(r, g, b);
						
						fin.next();
					}
					fin.nextLine();
				}
				fin.nextLine();
			}
			fin.close();
			
		} catch (FileNotFoundException e) {
			this.length = 1;
			this.width = 1;
			this.frames = 1;
			e.printStackTrace();

			lights = new Color[frames][width][length];
		}
	}
	public Color get(int x, int y, int currentFrame) {
		return lights[currentFrame][x][y];
	}
}


