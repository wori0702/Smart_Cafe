package com.example.smartcafe;

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

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView T1;
    public OkHttpClient client;
    public  Request request;

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

        String url = "http://192.168.0.80/getdata";                                 //wifi 모듈에 잡아놓은 수동 IP http://192.168.0.80  https://reqres.in/api/users?page=2
        request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        System.out.println("request : " + request);

        Button RoomButton = findViewById(R.id.RoomButton);
        Button SettingButton = findViewById(R.id.MySettingButton);

        T1 = findViewById(R.id.BTnum);
        final TextView T2 = findViewById(R.id.TPnum);
        final TextView T3 = findViewById(R.id.Co2num);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d("error","Connect Sever Error is " +call +" _"+e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myRespone = response.body().string();
//                    final String[] splittest = myRespone.split("\"");
                    System.out.println("Respone : "+ response.toString());
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
                            System.out.println("Respone : "+ response.toString());
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

                Intent intent = new Intent(this, RoomAcivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intent);
                break;

            case R.id.MySettingButton:
                Intent intents = new Intent(this, MySettingActivity.class);
                intents.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intents);
                break;
        }


    }

}
