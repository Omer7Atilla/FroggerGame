package froggerGame;

import java.awt.Rectangle;

class MovingObject {
	int x, y, width, height, speed;
	boolean isMovingRight;
	
	MovingObject(int x, int y, int w, int h, int s, boolean isMovingRight){
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		speed = s;
		this.isMovingRight = isMovingRight;
	}
	
	public void move(int boardWidth) {
		if (isMovingRight) {
			x += speed;
			if (x > boardWidth) {
				x = -width;
			}
		}else { 
			x -= speed;
			if (x < -width) {
				x = boardWidth;
			}
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
