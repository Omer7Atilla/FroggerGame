package froggerGame;

import java.awt.Rectangle;

class Frog {
	int x, y, diameter, lives;
	
	Frog(int x, int y, int d){
		this.x = x;
		this.y = y;
		diameter = d;
		lives = 3;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, diameter, diameter);
	}
}
