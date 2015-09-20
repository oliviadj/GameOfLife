package com.olivia.task.gameoflife;

import android.util.Log;

public class Cell {

	public static final String TAG = "Cell";

	private int state = 0; // 0 means dead , 1 means alive;
	private int nextState = 0;
	private int row = 0;
	private int column = 0;

	int numRow;
	int numCol;

	public static int[][] stateArray;

	public Cell(){}

	public Cell(int row, int column, int numRow, int numCol){

		this.row = row;
		this.column = column;
		this.numRow = numRow;
		this.numCol = numCol;

		stateArray = new int[numRow][numCol];

		for(int i = 0; i < numRow; ++i){
			for(int j = 0; j < numCol; ++j){
				stateArray[i][j] = 0;
			}
		}

	}

	// THE RULE is defined in this method
	public void setNextState(){
		int neighbor = 0;

		//left top
		if (row > 0) {
			if (column > 0) if (stateArray[(row - 1)][(column - 1)] == 1) ++neighbor;
			//top
			if (stateArray[(row - 1)][column] == 1) ++neighbor;
			//right top
			if (column < numCol - 1) if (stateArray[(row - 1)][(column + 1)] == 1) ++neighbor;
		}

		//left
		if (column > 0)
			if(stateArray[row][(column-1)] == 1) ++neighbor;

		//right
		if (column < numCol - 1)
			if(stateArray[row][(column+1)] == 1) ++neighbor;

		if (row < numRow - 1) {
			//left bottom
			if (column > 0) if (stateArray[(row + 1)][(column - 1)] == 1) ++neighbor;
			//bottom
			if (stateArray[(row + 1)][column] == 1) ++neighbor;
			//right bottom
			if (column < numCol - 1) if (stateArray[(row + 1)][(column + 1)] == 1) ++neighbor;
		}

		if(neighbor == 3)
			this.nextState = 1;

		else if(neighbor == 2)
			this.nextState = this.state;

		else
			this.nextState = 0;

	}

	public int getNextState(){
		return this.nextState;
	}

	public int getState(){
		return state;
	}

	public void setState(int state){
		this.state = state;
		stateArray[row][column] = state;
	}
	
	public void changeState(int state){
		if (state == 1) setState(0);
		else setState(1);
	}

}
