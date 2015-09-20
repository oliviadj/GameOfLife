package com.olivia.task.gameoflife;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Config {
	
	public static final String TAG = "Board.Config";
	
	public static final int CELL_SIZE = 30;
	public static final int NUMBER_OF_ROW = 20;
	public static final int NUMBER_OF_COLUMN = 20;
	public static final int GAME_SPEED = 500; // in millisecond

	public static int[] pinIdList = new int[] {
		R.drawable.black_pin_img,
		R.drawable.green_pin_img,
		R.drawable.pink_pin_img,
		R.drawable.blue_pin_img
	};
	
	public static final int pinBackground = R.drawable.green_pin_img;
	public static final int pinAlive = R.drawable.black_pin_img;
	
}
