/**
 * 
 */
package com.edu4java.android.killthemall;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

/**
 * @author ngocha
 *
 */
public class Main extends Activity {
	/** Called when the activity is first created. */
	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// requestWindowFeature(Window.FEATURE_NO_TITLE);
	// setContentView(new GameView(this));
	// }

	/** Called when the activity is first created. */
	DrawView drawView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		drawView = new DrawView(this);
		drawView.setBackgroundColor(Color.WHITE);
		setContentView(drawView);
	}
}
