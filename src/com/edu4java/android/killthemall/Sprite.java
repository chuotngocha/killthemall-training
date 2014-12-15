/**
 * 
 */
package com.edu4java.android.killthemall;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author ngocha
 *
 */
public class Sprite {

	private static final int BMP_ROWS = 4;
	private static final int BMP_COLUMNS = 3;
	private int x = 0;
	private int y = 0;
	private int xSpeed = 5;
	private int ySpeed;
	private GameView gameView;
	private Bitmap bmp;
	private int currentFrame = 0;
	private int width;
	private int height;
	private static final int MAX_SPEED = 5;

	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public Sprite(GameView gameView, Bitmap bmp) {
		this.gameView = gameView;
		this.bmp = bmp;
		this.width = bmp.getWidth() / BMP_COLUMNS;
		this.height = bmp.getHeight() / BMP_ROWS;
		Random rnd = new Random();
		x = rnd.nextInt(gameView.getWidth() - width);
		y = rnd.nextInt(gameView.getHeight() - height);
		xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
		ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
	}

	private void update() {
		if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0) {
			xSpeed = -xSpeed;
		}
		x = x + xSpeed;
		if (y >= gameView.getHeight() - height - ySpeed || y + ySpeed <= 0) {
			ySpeed = -ySpeed;
		}
		y = y + ySpeed;
		currentFrame = ++currentFrame % BMP_COLUMNS;

	}

	public void draw(Canvas canvas) {
		update();
		int srcX = currentFrame * this.width;
		int srcY = getAnimationRow() * height;
		Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
		Rect dst = new Rect(x, y, x + this.width, y + this.height);
		canvas.drawBitmap(bmp, src, dst, null);
		shoot();

	}

	// direction = 0 up, 1 left, 2 down, 3 right,
	// animation = 3 up, 1 left, 0 down, 2 right
	int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };

	private int getAnimationRow() {
		double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
		int direction = (int) Math.round(dirDouble) % BMP_ROWS;
		return DIRECTION_TO_ANIMATION_MAP[direction];
	}

	public boolean isCollition(float x2, float y2) {
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
	}

	public void shoot() {
		Projectile p = new Projectile(this.width + x - 10, this.height + y - 10);
		projectiles.add(p);
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

}
