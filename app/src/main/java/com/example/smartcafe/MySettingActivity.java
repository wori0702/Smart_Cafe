package com.example.smartcafe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MySettingActivity extends AppCompatActivity {
    // values
    private int[] idx;
    private int[] plusidx;
    private String[] data;
    private String[] name;
    private String[] temp;
    private String[] lux;
    private OkHttpClient client;
    private RequestBody requestBody;
    private Request request;
    private String url =  "http://192.168.0.80/send_data";
    private String default_lux = "127";
    private String default_temp = "23";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        client = new OkHttpClient();
        idx = new int[6];
        plusidx = new int[4];
        name = new String[4];
        temp = new String[4];
        lux = new String[4];

        int[] idx;
        int[] plusidx;
        data = new String[4];
        idx = new int[10];
        plusidx = new int[4];

        try {
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "test.txt"));
            for(int i = 0; i < 4; i++){
                String str = br.readLine();
                if(str != null) data[i] = str;
                else break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayout.LayoutParams mainpr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2200, 1);
        mainpr.setMargins(0,50,0,50);
        LinearLayout.LayoutParams llpr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 500, 1);
        llpr.setMargins(0, 0,0,50);
        RelativeLayout.LayoutParams rlpa = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 500);
        rlpa.setMargins(0,0,0,50);
        LinearLayout.LayoutParams subpr = new LinearLayout.LayoutParams(432, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        ViewGroup.LayoutParams ivlp = new ViewGroup.LayoutParams(288, ViewGroup.LayoutParams.MATCH_PARENT);
        ViewGroup.LayoutParams tvlp = new ViewGroup.LayoutParams(720, ViewGroup.LayoutParams.MATCH_PARENT);
        ViewGroup.LayoutParams btlp = new ViewGroup.LayoutParams(380, 180);
        LinearLayout.LayoutParams applylp = new LinearLayout.LayoutParams(700, 220,1);
        applylp.setMargins(0,0,20,0);
        LinearLayout.LayoutParams canclelp = new LinearLayout.LayoutParams(700, 220,1);
        ViewGroup.LayoutParams plus = new ViewGroup.LayoutParams(200, 200);
        LinearLayout bg = new LinearLayout(this);
        bg.setOrientation(LinearLayout.VERTICAL);

        LinearLayout mainLL = new LinearLayout(this);
        mainLL.setLayoutParams(mainpr);
        mainLL.setOrientation(LinearLayout.VERTICAL);
        mainLL.setPadding(10,0,10,0);

        for(int i =0; i < 4; i++){
            if(data[i] != null) {
                String[] splitData = data[i].split(",");
                name[i] = splitData[1];
                temp[i] = splitData[2];
                lux[i] = splitData[3];

                LinearLayout ll = new LinearLayout(this);
                ImageView iv = new ImageView(this);
                TextView tv = new TextView(this);
                LinearLayout subll = new LinearLayout(this);
                Button b1 = new Button(this);
                Button b2 = new Button(this);

                ll.setLayoutParams(llpr);
                ll.setBackground(getDrawable(R.drawable.btn_bg));
                iv.setLayoutParams(ivlp);
                iv.setPadding(30,30,30,30);
                tv.setLayoutParams(tvlp);
                subll.setLayoutParams(subpr);
                subll.setOrientation(LinearLayout.VERTICAL);
                subll.setPaddingRelative(0, 100, 0, 0);
                b1.setLayoutParams(btlp);
                b2.setLayoutParams(btlp);

                if(splitData[0].equals("0")) iv.setImageDrawable(getDrawable(R.drawable.laptop3));
                if(splitData[0].equals("1")) iv.setImageDrawable(getDrawable(R.drawable.discuss3));
                if(splitData[0].equals("2")) iv.setImageDrawable(getDrawable(R.drawable.study3));

                tv.setText(splitData[1]);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(24);

                b1.setText("적용");
                b1.setTextSize(18);
                b1.setBackground(getDrawable(R.drawable.btn2_bg));
                b2.setText("수정");
                b2.setTextSize(18);
                b2.setBackground(getDrawable(R.drawable.btn2_bg));

                b1.setId(View.generateViewId());
                b2.setId(View.generateViewId());

                idx[i*2] = b1.getId();
                idx[(i*2)+1] = b2.getId();

                subll.addView(b1);
                subll.addView(b2);

                ll.addView(iv);
                ll.addView(tv);
                ll.addView(subll);

                mainLL.addView(ll);
            }
            else {
                RelativeLayout rl = new RelativeLayout(this);
                Button plusbtn = new Button(this);

                rl.setLayoutParams(rlpa);
                rl.setBackground(getDrawable(R.drawable.btn_bg));

                rl.setGravity(Gravity.CENTER);
                plusbtn.setLayoutParams(plus);

                plusbtn.setBackground(getDrawable(R.drawable.plus));

                plusbtn.setId(View.generateViewId());
                plusidx[i] = plusbtn.getId();

                rl.addView(plusbtn);
                mainLL.addView(rl);
            }
        }

        LinearLayout btnll = new LinearLayout(this);
        Button apply = new Button(this);
        Button cancle = new Button(this);

        btnll.setOrientation(LinearLayout.HORIZONTAL);
        btnll.setPadding(20,0,20,30);

        apply.setLayoutParams(applylp);
        apply.setText("편집");
        apply.setTextSize(24);
        apply.setBackground(getDrawable(R.drawable.btn2_bg));

        cancle.setLayoutParams(canclelp);
        cancle.setText("취소");
        cancle.setTextSize(24);
        cancle.setBackground(getDrawable(R.drawable.btn2_bg));

        apply.setId(View.generateViewId());
        cancle.setId(View.generateViewId());
        idx[8] = apply.getId();
        idx[9] = cancle.getId();

        btnll.addView(apply);
        btnll.addView(cancle);

        bg.addView(mainLL);
        bg.addView(btnll);

        setContentView(bg);

        Button btn0;
        Button btn1;
        Button btn2;
        Button btn3;
        Button btn4;
        Button btn5;
        Button btn6;
        Button btn7;
        Button btn8;
        Button btn9;
        Button plus0;
        Button plus1;
        Button plus2;
        Button plus3;

        if(idx[0] != 0) {
            btn0 = findViewById(idx[0]);
            btn0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestBody = new FormBody.Builder()
                            .add("Light",lux[0])
                            .add("Temperature",temp[0])
                            .build();
                    request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("error","send Error");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d("success","send Success");
                        }
                    });
                }
            });
        }
        if(idx[1] != 0) {
            btn1 = findViewById(idx[1]);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MakeRoomActivity.class);
                    intent.putExtra("data", data);
                    intent.putExtra("name", name[0]);
                    intent.putExtra("num", 0);
                    intent.putExtra("temp", temp[0]);
                    intent.putExtra("lux", lux[0]);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
            });
        }
        if(idx[2] != 0) {
            btn2 = findViewById(idx[2]);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {requestBody = new FormBody.Builder()
                        .add("Light",lux[1])
                        .add("Temperature",temp[1])
                        .build();
                    request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("error","send Error");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d("success","send Success");
                        }
                    });
                }
            });
        }
        if(idx[3] != 0) {
            btn3 = findViewById(idx[3]);
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MakeRoomActivity.class);
                    intent.putExtra("data", data);
                    intent.putExtra("name", name[1]);
                    intent.putExtra("num", 1);
                    intent.putExtra("temp", temp[1]);
                    intent.putExtra("lux", lux[1]);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
            });
        }
        if(idx[4] != 0) {
            btn4 = findViewById(idx[4]);
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {requestBody = new FormBody.Builder()
                        .add("Light",lux[2])
                        .add("Temperature",temp[2])
                        .build();
                    request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("error","send Error");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d("success","send Success");
                        }
                    });
                }
            });
        }
        if(idx[5] != 0) {
            btn5 = findViewById(idx[5]);
            btn5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MakeRoomActivity.class);
                    intent.putExtra("data", data);
                    intent.putExtra("name", name[2]);
                    intent.putExtra("num", 2);
                    intent.putExtra("temp", temp[2]);
                    intent.putExtra("lux", lux[2]);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
            });
        }
        if(idx[6] != 0) {
            btn6 = findViewById(idx[6]);
            btn6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {requestBody = new FormBody.Builder()
                        .add("Light",lux[3])
                        .add("Temperature",temp[3])
                        .build();
                    request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("error","send Error");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d("success","send Success");
                        }
                    });
                }
            });
        }
        if(idx[7] != 0) {
            btn7 = findViewById(idx[7]);
            btn7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MakeRoomActivity.class);
                    intent.putExtra("data", data);
                    intent.putExtra("name", name[3]);
                    intent.putExtra("num", 3);
                    intent.putExtra("temp", temp[3]);
                    intent.putExtra("lux", lux[3]);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
            });
        }
        if(idx[8] != 0) {
            btn8 = findViewById(idx[8]);

            btn8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Modify.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
            });
        }
        if(idx[9] != 0) {
            btn9 = findViewById(idx[9]);
            btn9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        if(plusidx[0] != 0){
            plus0 = findViewById(plusidx[0]);
            plus0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MakeRoomActivity.class);
                    intent.putExtra("data", data);
                    intent.putExtra("name", "Enter a name");
                    intent.putExtra("num", 0);
                    intent.putExtra("temp", default_temp);
                    intent.putExtra("lux", default_lux);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
            });
        }
        if(plusidx[1] != 0){
            plus1 = findViewById(plusidx[1]);
            plus1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MakeRoomActivity.class);
                    intent.putExtra("data", data);
                    intent.putExtra("name", "Enter a name");
                    intent.putExtra("num", 1);
                    intent.putExtra("temp", default_temp);
                    intent.putExtra("lux", default_lux);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
            });
        }
        if(plusidx[2] != 0){
            plus2 = findViewById(plusidx[2]);
            plus2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MakeRoomActivity.class);
                    intent.putExtra("data", data);
                    intent.putExtra("name", "Enter a name");
                    intent.putExtra("num", 2);
                    intent.putExtra("temp", default_temp);
                    intent.putExtra("lux", default_lux);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
            });
        }
        if(plusidx[3] != 0){
            plus3 = findViewById(plusidx[3]);
            plus3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MakeRoomActivity.class);
                    intent.putExtra("data", data);
                    intent.putExtra("name", "Enter a name");
                    intent.putExtra("num", 3);
                    intent.putExtra("temp", default_temp);
                    intent.putExtra("lux", default_lux);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}