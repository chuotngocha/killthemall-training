/**
 * 
 */
package com.edu4java.android.killthemall;

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
	private GameView gameView;
	private Bitmap bmp;
	private int currentFrame = 0;
	private int width;
	private int height;

	public Sprite(GameView gameView, Bitmap bmp) {
		this.gameView = gameView;
		this.bmp = bmp;
		this.width = bmp.getWidth() / BMP_COLUMNS;
		this.height = bmp.getHeight() / BMP_ROWS;
	}

	private void update() {
		if (x > gameView.getWidth() - this.width - xSpeed) {
			xSpeed = -10;
		}
		if (x + xSpeed < 0) {
			xSpeed = 10;
		}
		x = x + xSpeed;
		currentFrame = ++currentFrame % BMP_COLUMNS;
	}

	public void draw(Canvas canvas) {
		update();
		int srcX = currentFrame * this.width;
		int srcY = 1 * this.height;
		Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
		Rect dst = new Rect(x, y, x + this.width, y + this.height);
		canvas.drawBitmap(bmp, src, dst, null);
	}
}
