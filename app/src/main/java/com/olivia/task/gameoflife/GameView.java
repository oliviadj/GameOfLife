package com.olivia.task.gameoflife;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

public class GameView extends ViewGroup{

	public static final String TAG = "Board.GameView";
	private int parentWidth;
	private int parentHeight;
	private int dotSize;
	private int numRow;
	private int numCol;

	Cell state[][];

	private Context context;

	public GameView(Context context) {
		super(context);
		this.context = context;
	}

	public void changeState() {

		for (int i = 0; i < numRow; ++i) {
			for (int j = 0; j < numCol; ++j) {
				state[i][j].setNextState();
			}
		}

		for( int i = 0; i < numRow; ++i)
			for(int j = 0; j < numCol; ++j){
				state[i][j].setState(state[i][j].getNextState());

				// set pin color according to pin state
				setPinColor(i, j, state[i][j].getState());
			}
	}


	public void deployPins(Cell[][] cell){
		//Log.d(TAG, "deployPins - cell.len :" + cell.length + ", cell[0].len :" + cell[0].length);

		// now set pin colors according to the status
		for( int i = 0; i < cell.length; ++i)

			for(int j = 0; j < cell[0].length; ++j)

					setPinColor(i, j, cell[i][j].getState());
	}
	
	void setPinColor(int row, int col, int state){
		//Log.d(TAG, "setPinColor - row : " + row + ", col : " + col + ", state :" + state);
		PinImageView pinImg = (PinImageView) getChildAt(col + ( row  * numCol ));

		if (pinImg != null) {
			pinImg.setPinColor(determinePinColor(state));
		}
	}
	
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public int[] createBoard(int width, int height, int dotSize) {

		this.dotSize = dotSize;
		
		numRow = height / dotSize;
		numCol =  width / dotSize;

		state = new Cell[numRow][numCol];

		for (int r=0; r < numRow; r++) {

			for (int c=0; c < numCol ; c++) {

				PinImageView pinImg = new PinImageView(getContext(), r, c);
				this.addView(pinImg);

				// initialize cell
				state[r][c] = new Cell(r, c, numRow, numCol);
			}
		}

		return new int[]{numRow, numCol};
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {		
		//Log.d(TAG, "onMeasure");
		parentWidth  = MeasureSpec.getSize(widthMeasureSpec) ;
		parentHeight = MeasureSpec.getSize(heightMeasureSpec) ;
		
		this.setMeasuredDimension(parentWidth, parentHeight);
	}
	
	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		//Log.d(TAG, "onLayout");
		int childCount = getChildCount();

		for (int i=0; i < childCount; i++) {
			PinImageView pinImg = (PinImageView) getChildAt(i);
			
			int left = pinImg.getCol() * dotSize;
			int top = pinImg.getRow()  * dotSize;
			int right = left + dotSize ;
			int bottom = top + dotSize ;
			
			pinImg.layout(left, top, right, bottom);			
		}
		
	}

	public void changePinColor(int x, int y) {
		//Log.d(TAG, "changePinColor");
		int row = getRow(y);
		int col = getColumn(x);

		// check row and col, cannot be greater / smaller than array size
		row = (row > numRow-1) ? numRow-1 : (row < 0) ? 0 : row;
		col = (col > numCol-1) ? numCol-1 : (col < 0) ? 0 : col;

		PinImageView pinImg = (PinImageView) getChildAt(col + ( row  * numCol ));

		if (pinImg != null) {
			int pinState = (state[row][col]).getState();
			
			// chhange the state and color
			// if cell is dead, becomes alive (black)
			// if cell is alive, becomes dead (pink)
			(state[row][col]).changeState(pinState);

			pinImg.setPinColor(determinePinColor(pinState));

			//Log.d(TAG, "row : " + row + ", col : " + col + ", state : " + pinState + ", color = " + showColor(pinImg.getCol()));
		}
	}

/*
	String showColor(int x){
		if (x == Config.pinBackground) return "pink";
		else return "black";
	}
*/

	private int determinePinColor(int state){
		if (state == 0) return Config.pinBackground;
		return Config.pinAlive;
	}

	public int getRow(int y) {
		return (int) Math.ceil(y / dotSize);
	}
	
	public int getColumn(int x) {
		return (int) Math.ceil( x / dotSize);
	}

	// this method is to synch between the pin and the state (as during touch, sometime causes inconsistency)
	public void synch(){
		for( int i = 0; i < numRow; ++i)
			for(int j = 0; j < numCol; ++j){
				PinImageView pinImg = (PinImageView) getChildAt(j + ( i * numCol ));

				int color = pinImg.getPinColor();

				if (color == Config.pinAlive) {
					(state[i][j]).setState(1);
				} else {
					(state[i][j]).setState(0);
				}
			}
	}

}
