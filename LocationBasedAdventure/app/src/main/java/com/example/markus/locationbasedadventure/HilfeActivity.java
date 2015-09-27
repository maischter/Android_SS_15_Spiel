package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Markus on 27.09.2015.
 */
public class HilfeActivity extends Activity {

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hilfe);
        initButtons();
    }

    //initialises back button
    //buttonListener

    private void initButtons() {
        Button back = (Button) findViewById(R.id.buttonBackHilfe);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
