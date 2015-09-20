package com.olivia.task.gameoflife;

import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InstrumentationTest extends ActivityInstrumentationTestCase2<GameActivity>{

    GameActivity activity;

    public InstrumentationTest() {
        super(GameActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void testInstructionTestVisibility(){

        // test the instruction text, as it is made invisible during onCreate
        TextView textInstruction = (TextView) activity.findViewById(R.id.text_instruction);
        assertEquals(View.INVISIBLE, textInstruction.getVisibility());
    }

    public void testButtonText(){

        // test the text on the button
        Button button = (Button) activity.findViewById(R.id.button_play);
        assertEquals("PLAY", button.getText());
    }

}
