package com.olivia.task.gameoflife;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class PinImageView extends ImageView  {

	public static final String TAG = "Board.PinImageView";
	private int row;
	private int col;	
	private int currentPinId;
	
	public PinImageView(Context context, int row, int col) {
		super(context);
		this.row = row;
		this.col = col;

		// Load image
		Drawable d  = getResources().getDrawable(Config.pinBackground);
		setImageDrawable(d);		
		this.currentPinId = Config.pinBackground;
		
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public void setPinColor(int resId) {
		
		this.currentPinId = resId;
		Drawable d = getResources().getDrawable(resId);

		setImageDrawable(d);

	}

	public int getPinColor(){
		return this.currentPinId;
	}

}
