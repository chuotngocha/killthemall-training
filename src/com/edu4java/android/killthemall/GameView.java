package com.edu4java.android.killthemall;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private long lastClick;
	private List<TempSprite> temps = new ArrayList<TempSprite>();;
	private Bitmap bmpBlood;
	private Paint paint;
	private int x = 0;
	private int y = 0;
	private int xSpeed = 1;

	public GameView(Context context) {
		super(context);

		paint = new Paint();

		gameLoopThread = new GameLoopThread(this);

		holder = getHolder();

		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				gameLoopThread.setRunning(false);

				while (retry) {
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {
					}
				}
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				createSprites();
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}
		});
		bmpBlood = BitmapFactory.decodeResource(getResources(),
				R.drawable.rsz_image_skill_1_red_1);
	}

	private void createSprites() {
		sprites.add(createSprite(R.drawable.bad1));
		sprites.add(createSprite(R.drawable.bad2));
		sprites.add(createSprite(R.drawable.bad3));
		sprites.add(createSprite(R.drawable.bad4));
		sprites.add(createSprite(R.drawable.bad5));
		sprites.add(createSprite(R.drawable.bad6));
		sprites.add(createSprite(R.drawable.good1));
		sprites.add(createSprite(R.drawable.good2));
		sprites.add(createSprite(R.drawable.good3));
		sprites.add(createSprite(R.drawable.good4));
		sprites.add(createSprite(R.drawable.good5));
		sprites.add(createSprite(R.drawable.good6));
	}

	private Sprite createSprite(int resouce) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
		return new Sprite(this, bmp);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);

		for (int i = temps.size() - 1; i >= 0; i--) {
			temps.get(i).draw(canvas);
		}

		for (Sprite sprite : sprites) {
			sprite.draw(canvas);
			// bullet fire
			ArrayList<Projectile> projectiles = sprite.getProjectiles();

			for (int i = 0; i < projectiles.size(); i++) {
				// Projectile p = (Projectile) projectiles.get(i);
				paint.setColor(Color.YELLOW);
				// canvas.drawLine(p.getX(), p.getY(), p.getX() + 10,
				// p.getY() + 10, paint);
				if (x > getWidth()) {
					xSpeed = -5;
				}
				if (x == 0) {
					xSpeed = 5;
				}
				x = x + xSpeed;
				// canvas.drawRect(p.getX() + x, p.getY(), p.getX() + x + 20,
				// p.getY() + 5, paint);
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (System.currentTimeMillis() - lastClick > 500) {
			lastClick = System.currentTimeMillis();
			float x = event.getX();
			float y = event.getY();
			synchronized (getHolder()) {
				for (int i = sprites.size() - 1; i >= 0; i--) {
					Sprite sprite = sprites.get(i);
					if (sprite.isCollition(event.getX(), event.getY())) {
						sprites.remove(sprite);
						temps.add(new TempSprite(temps, this, x, y, bmpBlood));
						break;
					}
				}
			}
		}
		return true;
	}

}
