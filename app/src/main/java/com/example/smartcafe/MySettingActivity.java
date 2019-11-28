package com.example.smartcafe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MySettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);

        String[] data;
        data = new String[4];

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "test.txt"));
            bw.write("0,겨울 세팅\n");
            bw.write("2,노트북 세팅\n");
            bw.write("1,고대영 세팅\n");
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

                subll.addView(b1);
                subll.addView(b2);

                ll.addView(iv);
                ll.addView(tv);
                ll.addView(subll);

                mainLL.addView(ll);
            }
            else {
                System.out.println("mycheck : " + i + data[i]);
                RelativeLayout rl = new RelativeLayout(this);
                Button plusbtn = new Button(this);

                rl.setLayoutParams(rlpa);
                rl.setBackground(getDrawable(R.drawable.btn_bg));

                rl.setGravity(Gravity.CENTER);
                plusbtn.setLayoutParams(plus);

                plusbtn.setBackground(getDrawable(R.drawable.plus));

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
        apply.setText("적용");
        apply.setTextSize(24);
        apply.setBackground(getDrawable(R.drawable.btn2_bg));
        cancle.setLayoutParams(canclelp);
        cancle.setText("취소");
        cancle.setTextSize(24);
        cancle.setBackground(getDrawable(R.drawable.btn2_bg));

        btnll.addView(apply);
        btnll.addView(cancle);

        bg.addView(mainLL);
        bg.addView(btnll);

        setContentView(bg);
    }
}