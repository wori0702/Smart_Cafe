package com.example.smartcafe;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class RoomAcivity extends AppCompatActivity implements View.OnClickListener {



    String [] temp_array = {"23","24","22"};
    int [] Light_array = {100,150,200};

    TextView temp_text;
    Button btn_spring;
    Button btn_summer;
    Button btn_winter;
    Button btn_exit;
    SeekBar seekBar;
    Button btn;
    int temp;
    String seekdata;
    private OkHttpClient client;
    RequestBody RequestBody;
    String url = "http://192.168.0.80/send_data";
    String default_temperature;
    Request request;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_acivity);
        temp_text=findViewById(R.id.text_temp);
        seekBar= findViewById(R.id.seek);
        btn_exit = findViewById(R.id.btn_exit);
        btn_spring=findViewById(R.id.btn_spring);
        btn_summer=findViewById(R.id.btn_summer);
        btn_winter=findViewById(R.id.btn_winter);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        seekdata = extras.getString("Light");
        default_temperature = extras.getString("Temperature");

        client = new OkHttpClient();

        temp_text.setText(default_temperature +"℃");
        seekBar.setProgress(Integer.parseInt(seekdata));

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
                RequestBody = new FormBody.Builder()
                        .add("Light",seekdata)
                        .add("temperture",default_temperature)
                        .build();
                request = new Request.Builder()
                        .url(url)
                        .post(RequestBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("error","send Error");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("Success","Send Success");
                    }
                });

                Intent intent = new Intent(this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Temperture",default_temperature);
                bundle.putString("Light",seekdata);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK,intent);
                finish();
                break;
            case R.id.temp_up_btn:
                temp = Integer.parseInt(default_temperature);
                default_temperature = Integer.toString(temp +1);
                temp_text.setText(default_temperature+"℃");
                break;
            case R.id.temp_down_btn:
                temp = Integer.parseInt(default_temperature);
                default_temperature= Integer.toString(temp -1 );
                temp_text.setText(default_temperature+"℃"
                );
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

            case R.id.btn_exit:
                finish();
                break;
        }
    }

}
