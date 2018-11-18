import java.util.ArrayList;

import java.awt.*;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Game extends PApplet {

	float X_BORDER = 50;
	float Y_BORDER = 125;
	int WIN_X_LENGTH = 550;
	int WIN_Y_HEIGHT= 700;
	float GRID = (WIN_Y_HEIGHT - 2*Y_BORDER)/3;
	
	float time = 0;
	float lastChange = 0;
	float timeInt = 60;
	
	int score = 0;
	int state = 0; 	// 0 is home page, 1 is game loop, 2 is end screen
	
	PFont myFont, titleFont, endFont, bigMessageFont;
	
	PImage backState0, backState1, endgame, bruin;
	
	ArrayList<Trojan> trojanList = new ArrayList<Trojan>();
	
	public static boolean wasClicked = false;
	public static Point mousePoint;
	
	public static void main(String[] args) {
		PApplet.main(new String[] { "Game" });
	}
	
	public void setup() {
		size(WIN_X_LENGTH, WIN_Y_HEIGHT);
		
		super.addMouseListener(new MyMouseListener());
		
		float y = Y_BORDER;
		for (int i = 0; i < 9; i++) {
			if (i < 3) {		// COLUMN 1
				Trojan timmy = new Trojan(this, X_BORDER, y, false);
				trojanList.add(timmy);
				y = y + GRID;
			} 
			else if (i < 6) {	// COLUMN 2
				if (i == 3)
					y = Y_BORDER;
				Trojan timmy = new Trojan(this, X_BORDER + GRID, y, false);
				trojanList.add(timmy);
				y = y +  GRID;
			} 
			else if (i < 9) {	// COLUMN 3
				if (i == 6)
					y = Y_BORDER;
				Trojan timmy = new Trojan(this, X_BORDER + 2*GRID, y, false);
				trojanList.add(timmy);
				y = y + GRID;
			} 
		}
		
		titleFont = createFont("Arial Bold", 45);
		myFont = createFont("Arial Bold", 20);
		endFont = createFont("Arial Bold", 70);
		bigMessageFont = createFont("Arial Bold", 40);
		textFont(myFont);
		
		backState0 = loadImage("..\\assets\\state2Back.png");
		endgame = loadImage("..\\assets\\endgame.png");
		bruin = loadImage("..\\assets\\bruin.png");
	}
		
		
	public void draw() {
		if (state == 0) 
		{
			background(backState0);
			
			textFont(titleFont);
			text("SPLAT-THE-TROJAN", 50, 200);
			
			textFont(myFont);
			text("click on the trojans to disperse of those pests.", 50, 250);
			text("click on the screen to start.", 50, 300);
			
			if (wasClicked)
			{
				state = 1; 
				wasClicked = false;
			}
		
		}
		
		else if (state == 1)
		{
			background(backState0);
			
			text("score: " + score, 430, 30);
			
			for (int i = 0; i < 9; i++) {
				Trojan drawTroj = trojanList.get(i);
				
				if (drawTroj.getDisplayState())
					drawTroj.draw();
				
				// Reset splattered Trojans and stop displaying them
				if (drawTroj.isHit && time - drawTroj.getLastHit() > 5)
				{
					drawTroj.setIsHit(false);
					drawTroj.setLastHit(time);
					drawTroj.setDisplayState(false);
					trojanList.remove(i);
					trojanList.add(drawTroj);
				}
				
			}

			if (time % timeInt == 0) {
				for (int i = 0; i < 9; i++) {
					Trojan drawTroj = trojanList.get(i);

					// Remove old Trojan and possible replace with another one who is not being displayed
					trojanList.remove(i);
					drawTroj.randomDisplayState();
					trojanList.add(drawTroj);
				}
			}
			
			if (wasClicked == true)
				ifMouseClicked();
			
			time++;
			
			// Make game speed faster
			if (lastChange - time > 100) {
				timeInt /= 9;
				lastChange = time;
			}

			wasClicked = false;
			
			// Change states
			if (time > 1900)
			{
				state = 2;
			}
			
		}

		else if (state == 2)
		{
			background(endgame);
			
			image(bruin, 190, 365);
			
			textFont(endFont);
			text("score: " + score, 30, 90);
			
			String message;
			if (score <= 2000)
				message = "what are you, a trojan? \npathetic.";
			else if (score < 3000)
				message = "average.";
			else
				message = "a true bruin. respect.";
			
			textFont(bigMessageFont);
			text(message, 30, 175);
		}
	}
	
	public void ifMouseClicked() {
		double mouseX, mouseY;
		float x, y;
		
		mouseX = mousePoint.getX();
		mouseY = mousePoint.getY();
		
		for (int i = 0; i < 9; i++)
		{
			// Retrieve Trojan
			Trojan timmy = trojanList.get(i);
		
			// If Trojan is currently displayed
			if (timmy.getDisplayState())
			{
				x = timmy.getX();
				y = timmy.getY();
				
				// Check if Mouse is located on Trojan of display state true
				if (mouseX >= x && mouseX <= x + 149 && mouseY >= y && mouseY <= y + 153) // CHANGE TO USING TROJAN DIMENSIONS
				{
					// Switch status of Trojan and image
					timmy.setIsHit(true);
					timmy.setLastHit(time);
					trojanList.remove(i);
					trojanList.add(timmy);
					
					/**
					// Change display state of Trojan
					timmy.changeDisplayState();
					trojanList.remove(i);
					trojanList.add(timmy);
					**/
					
					// Increase score
					score = score + 50;
				}
			}
		}
	}
}
