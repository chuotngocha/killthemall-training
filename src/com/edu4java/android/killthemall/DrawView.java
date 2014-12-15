package com.edu4java.android.killthemall;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

class DrawView extends View {
	Paint paint = new Paint();

	public DrawView(Context context) {
		super(context);
		paint.setColor(Color.BLUE);
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawLine(10, 20, 30, 40, paint);
		canvas.drawLine(20, 10, 50, 20, paint);

	}
}
