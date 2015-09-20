package com.olivia.task.gameoflife;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity implements View.OnTouchListener{

	public static final String TAG = "Board.GameActivity";

	GameView gameView;
	Button buttonPlay;
	TextView textInstruction;
	boolean playing;
	Thread thread = new Thread(new GameThread());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_game);

		gameView = (GameView) findViewById(R.id.game_view);
		gameView.setOnTouchListener(this);
		createBoard();

		buttonPlay = (Button) findViewById(R.id.button_play);
		buttonPlay.setOnClickListener(buttonPlayClickListener);

		textInstruction = (TextView) findViewById(R.id.text_instruction);
		textInstruction.setVisibility(View.INVISIBLE);

		// if layout orientation change, should initialize vars based on saved instance, to maintain its state.
		SavedInstanceObj obj = SavedInstanceObj.getInstance();
		if (obj != null ) {
			if (obj.isGameSaved) {

				gameView.state = obj.gameState;
				gameView.deployPins(gameView.state);

			}
		}
	}

	View.OnClickListener buttonPlayClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			//  use 1 button to start and stop
			if (!playing) { // start playing

				// while playing, cannot change layout orientation
				lockScreenRotation();

				//synch first before playing, as during touch event may happen inconsistency
				gameView.synch();

				playing = true;
				buttonPlay.setText("STOP");
				textInstruction.setVisibility(View.VISIBLE); // show instruction

				if (thread == null) {
					thread = new Thread(new GameThread());
					thread.start();
				} else if (!thread.isAlive()) {
					thread = new Thread(new GameThread());
					thread.start();
				}
			} else {	// stop playing

				if(thread != null){
					thread.interrupt();
					thread = null;
				}

				playing = false;
				buttonPlay.setText("PLAY");
				textInstruction.setVisibility(View.INVISIBLE);

				//  release back the screen orientation
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
			}

		}
	};

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		// user can only select/deselect when game is not playing
		if (!playing) {
			int x = (int) event.getX() ;
			int y = (int) event.getY() ;

			if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
				//Log.d(TAG, "Action MOVE - x : " + x + ", y : " + y);
				gameView.changePinColor(x, y);
				return true;
			}
		}
		return false;

	}

	private void createBoard() {

		int cellSize = Config.CELL_SIZE;
		int width = Config.NUMBER_OF_COLUMN * cellSize;
		int height = Config.NUMBER_OF_ROW * cellSize;
		Log.d(TAG, "cell size: " + cellSize + ", width: " + width + ", height " + height);

		int[] vals = gameView.createBoard(width, height, cellSize);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstance");

		// synch first. very important! as touch event may cause inconsistency.
		if(!playing) gameView.synch();

		// save obj to singleton obj. easier than save it to bundle.
		SavedInstanceObj obj = SavedInstanceObj.getInstance();
		obj.gameState = gameView.state;
		obj.isGameSaved = true;

	}

	private void lockScreenRotation() {
		// Stop the screen orientation changing during an event
		switch (this.getResources().getConfiguration().orientation){
			case Configuration.ORIENTATION_PORTRAIT:
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				break;
			case Configuration.ORIENTATION_LANDSCAPE:
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (playing) {
			if(thread != null) {
				Thread pause = thread;
				thread = null;
				pause.interrupt();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (playing) {
			if(thread == null){
				thread = new Thread(new GameThread());
			}
			thread.start();
		}
	}

	// thread inner class
	class GameThread implements Runnable{

		public void run(){

			while(!Thread.currentThread().isInterrupted()){

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						gameView.changeState();
					}
				});

				try{
					Thread.sleep(Config.GAME_SPEED);
				} catch(InterruptedException e){
					Thread.currentThread().interrupt();
				}
			}
		}

	}


}
