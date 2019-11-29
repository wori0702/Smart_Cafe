package com.example.smartcafe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private long lastTimeBackPressed;
    private TextView T1;
    public OkHttpClient client;
    public  Request request;
    public String save_temp="23";
    public String save_light="127";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final RequestBody requestBody = new FormBody.Builder()
                .add("str[0]","20")
                .add("str[1]","30")
                .build();
        Log.d("error","error is " +requestBody);

        client = new OkHttpClient();

        String url = "http://192.168.0.80/receive_data";                                 //a여기서는 데이터 받아오는거만 하는거야
        request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Button RoomButton = findViewById(R.id.RoomButton);
        Button SettingButton = findViewById(R.id.MySettingButton);

        T1 = findViewById(R.id.BTnum);
        final TextView T2 = findViewById(R.id.TPnum);
        final TextView T3 = findViewById(R.id.Co2num);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myRespone = response.body().string();
//                    final String[] splittest = myRespone.split("\"");
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            T1.setText(myRespone);
//                            T2.setText(splittest[1]);
//                            T3.setText(splittest[2]);
                        }
                    });
                }
            }
        });
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
                save_temp = bundle.getString("Temperture");
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