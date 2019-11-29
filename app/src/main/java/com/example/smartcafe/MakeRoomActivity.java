package com.example.smartcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MakeRoomActivity extends AppCompatActivity implements View.OnClickListener {

    String [] temp_array = {"23","24","22"};
    int [] Light_array = {100,150,200};
    String theme = "0";

    TextView temp_text;
    Button btn_spring;
    Button btn_summer;
    Button btn_winter;
    Button btn_exit;
    SeekBar seekBar;
    EditText title;
    Button btn;
    int temp;
    String seekdata;
    String default_temperature;
    String title_name;
    String [] data;
    int idx;

//

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_room);
        temp_text=findViewById(R.id.text_temp);
        seekBar= findViewById(R.id.seek);
        btn_exit = findViewById(R.id.btn_exit);
        btn_spring=findViewById(R.id.btn_spring);
        btn_summer=findViewById(R.id.btn_summer);
        btn_winter=findViewById(R.id.btn_winter);
        title=findViewById(R.id.name);
        Intent intent = getIntent();

        seekdata = intent.getExtras().getString("lux");
        default_temperature = intent.getExtras().getString("temp");
        data = intent.getExtras().getStringArray("data");
        idx = intent.getExtras().getInt("num");
        title_name = intent.getExtras().getString("name");

        temp_text.setText(default_temperature +"℃");
        seekBar.setProgress(Integer.parseInt(seekdata));
        title.setText(title_name);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekdata = Integer.toString(i);
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
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "test.txt"));
                    data[idx] = theme + "," + title.getText() + "," + default_temperature + "," + seekdata;
                    System.out.println("input data : " + data[idx]);
                    for(int i=0; i<4; i++) {
                        if(data[i] != null) bw.write(data[i] + "\n");
                    }
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intents = new Intent(this, MySettingActivity.class);
                intents.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intents);
                break;
            case R.id.temp_up_btn:
                temp = Integer.parseInt(default_temperature);
                default_temperature = Integer.toString(temp +1);
                temp_text.setText(default_temperature+"℃");
                break;
            case R.id.temp_down_btn:
                temp = Integer.parseInt(default_temperature);
                default_temperature= Integer.toString(temp -1 );
                temp_text.setText(default_temperature+"℃");
                break;
            case R.id.btn_spring:
                default_temperature=temp_array[0];
                temp_text.setText(default_temperature+"℃");
                break;
            case R.id.btn_summer:
                default_temperature=temp_array[1];
                temp_text.setText(default_temperature+"℃");
                break;
            case R.id.btn_winter:
                default_temperature=temp_array[2];
                temp_text.setText(default_temperature+"℃");
                break;
            case R.id.laptop_btn:
                theme = "0";
                seekBar.setProgress(Light_array[0]);
                break;
            case R.id.discuss_btn:
                theme = "1";
                seekBar.setProgress(Light_array[1]);
                break;
            case R.id.study_btn:
                theme = "2";
                seekBar.setProgress(Light_array[2]);
                break;
            case R.id.btn_exit:
                finish();
                break;
        }
    }

}