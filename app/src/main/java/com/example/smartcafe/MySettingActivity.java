package com.example.smartcafe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

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

    private int[] idx;
    private int[] plusidx;
    private String[] data;
    private String[] temp;
    private String[] lux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        idx = new int[6];
        plusidx = new int[4];
        temp = new String[4];
        lux = new String[4];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);

        int[] idx;
        int[] plusidx;
        data = new String[4];
        idx = new int[10];
        plusidx = new int[4];

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "test.txt"));
            bw.write("0,내일견학,24,200\n");
            bw.write("1,행복그자체,22,55\n");
            bw.write("2,정말대단해,26,11\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                tv.setLayoutParams(tvlp);
                subll.setLayoutParams(subpr);
                subll.setOrientation(LinearLayout.VERTICAL);
                subll.setPaddingRelative(0, 100, 0, 0);
                b1.setLayoutParams(btlp);
                b2.setLayoutParams(btlp);

                if(splitData[0].equals("0")) iv.setImageDrawable(getDrawable(R.drawable.spring));
                if(splitData[0].equals("1")) iv.setImageDrawable(getDrawable(R.drawable.summer2));
                if(splitData[0].equals("2")) iv.setImageDrawable(getDrawable(R.drawable.winter2));

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
                    System.out.println("Temperature : " + temp[0] + " lux : " + lux[0]);
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
                    intent.putExtra("num", 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                }
            });
        }
        if(idx[2] != 0) {
            btn2 = findViewById(idx[2]);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Temperature : " + temp[1] + " lux : " + lux[1]);
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
                    intent.putExtra("num", 1);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                }
            });
        }
        if(idx[4] != 0) {
            btn4 = findViewById(idx[4]);
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Temperature : " + temp[2] + " lux : " + lux[2]);
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
                    intent.putExtra("num", 2);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                }
            });
        }
        if(idx[6] != 0) {
            btn6 = findViewById(idx[6]);
            btn6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Temperature : " + temp[3] + " lux : " + lux[3]);
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
                    intent.putExtra("num", 3);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
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
                }
            });
        }
        if(idx[9] != 0) {
            btn9 = findViewById(idx[9]);
            btn9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
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
                    intent.putExtra("num", 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
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
                    intent.putExtra("num", 1);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
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
                    intent.putExtra("num", 2);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
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
                    intent.putExtra("num", 3);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                }
            });
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
    }
}