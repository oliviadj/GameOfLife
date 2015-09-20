package com.olivia.task.gameoflife;

import android.content.Context;
import android.test.AndroidTestCase;

public class UnitTest extends AndroidTestCase {

    Context context;
    GameView gameView;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = this.getContext();
        gameView = new GameView(context);
    }

    public void testCreateBoard() throws Exception {
        int[] result = gameView.createBoard(300, 450, 30); // width, height, pin size
        assertEquals(15, result[0]);
        assertEquals(10, result[1]);
    }

    public void testChangePinAndNextState() throws Exception {
        // have to create board first
        gameView.createBoard(600, 600, 30);

        // *********** CHANGE PIN test ******************* //

        // changePinColor (x, y), becoming alive
        gameView.changePinColor(47, 25); // row 1 col 2
        gameView.changePinColor(51, 52); // row 2 col 2
        gameView.changePinColor(55, 88); // row 3 col 22

        // now, after changing pin color, check whether the cells become alive
        gameView.state[1-1][2-1].setNextState();
        int result = gameView.state[1-1][2-1].getState();
        assertEquals(1, result);

        gameView.state[2-1][2-1].setNextState();
        result = gameView.state[2-1][2-1].getState();
        assertEquals(1, result);

        gameView.state[3-1][2-1].setNextState();
        result = gameView.state[3-1][2-1].getState();
        assertEquals(1, result);


        // now check the neighbors state
        // *********** NEXT STATE test ******************* //

        // check cell in row 2 col 1, should become alive
        gameView.state[2-1][1-1].setNextState();
        result = gameView.state[2-1][1-1].getNextState();
        assertEquals(1, result);

        // check cell in row 2 col 3, should become alive
        gameView.state[2-1][3-1].setNextState();
        result = gameView.state[2-1][3-1].getNextState();
        assertEquals(1, result);

        // check cell in row 1 col 1, should be still dead
        gameView.state[1-1][1-1].setNextState();
        result = gameView.state[1-1][1-1].getNextState();
        assertEquals(0, result);
    }


}
