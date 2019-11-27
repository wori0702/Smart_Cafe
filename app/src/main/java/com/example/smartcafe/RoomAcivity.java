package com.example.smartcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class RoomAcivity extends AppCompatActivity implements View.OnClickListener {

    SeekBar seekBar;
    Button btn;
    String seekdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_acivity);

        seekBar= findViewById(R.id.seek);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekdata = Integer.toString(i);
                Log.d("makchar",Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btn = findViewById(R.id.BTS);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.BTS:
                Log.d("makchar",seekdata);
        }
    }

}
