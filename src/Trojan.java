import processing.core.PApplet;
import processing.core.PImage;

public class Trojan {

	float x;
	float y;
	float ySpeed;
	float yAccel;
	float lastHit = 0;
	
	boolean displayState;
	boolean isHit = false;
	
	PApplet window;
	
	PImage image;
	PImage image1, image2, image3, image4;
	
	public Trojan(PApplet window, float x, float y, boolean displayState) {
		this.window = window;
		this.x = x;
		this.y = y;
		this.displayState = displayState;
		
		//image size 149 by 153
		image1 = window.loadImage("..\\assets\\trojan.png");
		image2 = window.loadImage("..\\assets\\splatteredtrojan.png");
		image3 = window.loadImage("..\\assets\\splatTroj2.png");
		image4 = window.loadImage("..\\assets\\splatTroj3.png");
		image = image1;
	}
	
	public void draw() {
		window.image(image, x, y);
	}
	
	public boolean getDisplayState() {
		return this.displayState;
	}
	
	public void setDisplayState(boolean state) {
		this.displayState = state;
	}
	
	public void setIsHit(boolean isHit) {
		this.isHit = isHit;
		if (this.isHit)
		{
			int hitIm = (int)(Math.random()*3);
			if (hitIm == 0)
				image = image2;
			if (hitIm == 1)
				image = image3;
			if (hitIm == 2)
				image = image4;
		}
		else
		{
			image = image1;
		}
	}
	
	public void setLastHit(float time) {
		lastHit = time;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public float getLastHit() {
		return lastHit;
	}
	
	public void randomDisplayState() {
		int displ = (int)(Math.random()*3);
		if (displ == 0)
			this.displayState = true;
		else
			this.displayState = false;
	}
	
	public void changeDisplayState() {
		if (this.displayState == true)
			this.displayState = false;
		else if (this.displayState == false)
			this.displayState = true;
	}
	
}
