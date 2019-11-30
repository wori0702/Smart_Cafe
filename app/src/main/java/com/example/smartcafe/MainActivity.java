package com.example.smartcafe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private long lastTimeBackPressed;
    private TextView T1;
    private TextView T2;
    private TextView T3;
    public OkHttpClient client;
    public Request request;
    public String save_temp="23";
    public String save_light="127";
    public String url;
    public String myRespone;
    private static Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new OkHttpClient();

        url = "http://192.168.0.80/receive_data";                                 //a여기서는 데이터 받아오는거만 하는거야
        request = new Request.Builder()
                .url(url)
                .build();

        Button RoomButton = findViewById(R.id.RoomButton);
        Button SettingButton = findViewById(R.id.MySettingButton);

        T1 = findViewById(R.id.BTnum);
        T2 = findViewById(R.id.TPnum);
        T3 = findViewById(R.id.Co2num);

        mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("error","send_error");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String myrRespone = response.body().string();
                        String [] splitdata= myrRespone.split(" ");
                        T2.setText(splitdata[0]);
                        T3.setText(splitdata[1]);
                    }
                });

            }
        };

        class data_update implements  Runnable{

            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(0);
                }
            }
        }

        data_update update = new data_update();
        Thread t = new Thread(update);
        t.start();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.RoomButton:

                Log.d("get_data", save_light + "aaaaa"+save_temp);
                Bundle bundle = new Bundle();

                bundle.putString("Temperature",save_temp);
                bundle.putString("Light",save_light);
                Intent intent = new Intent(this, RoomAcivity.class);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivityForResult(intent,1);
                break;

            case R.id.MySettingButton:
                Intent intents = new Intent(this, MySettingActivity.class);
                intents.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intents);
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("here","im in here");

        if (requestCode ==requestCode)
        {
            if( resultCode ==RESULT_OK)
            {
                Bundle bundle = data.getExtras();
                save_light = bundle.getString("Light");
                save_temp = bundle.getString("Temperature");
                Log.d("OK_data",save_light + "aaaa"+save_temp);

            }
        }
    }
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
        Toast.makeText(this, "뒤로가기를 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}