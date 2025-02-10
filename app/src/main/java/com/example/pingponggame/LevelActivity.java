package com.example.pingponggame;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pingponggame.R;

public class LevelActivity extends AppCompatActivity {
    private ImageButton btn1, btn2, btn3, btn4, btn5, btn6, btn_start, btn_back;
    private ImageView lock1, lock2, lock3, lock4, lock5, lock6;
    private final int pos_animal=1;
    private String src_animal_icon="";
    int level = Variable.level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        btn1 = (ImageButton) findViewById(R.id.btn_animal1);
        btn2 = (ImageButton)findViewById(R.id.btn_animal2);
        btn3 = (ImageButton) findViewById(R.id.btn_animal3);
        btn4 = (ImageButton) findViewById(R.id.btn_animal4);
        btn5 = (ImageButton) findViewById(R.id.btn_animal5);
        btn6 = (ImageButton) findViewById(R.id.btn_animal6);
        btn_start = (ImageButton)findViewById(R.id.btn_start_level);
        lock1 = (ImageView)findViewById(R.id.lock1);
        lock2 = (ImageView)findViewById(R.id.lock2);
        lock3 = (ImageView)findViewById(R.id.lock3);
        lock4 = (ImageView)findViewById(R.id.lock4);
        lock5 = (ImageView)findViewById(R.id.lock5);
        lock6 = (ImageView)findViewById(R.id.lock6);
        btn_back = (ImageButton)findViewById(R.id.btn_backLevel);

        load_level_activity();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn1.isEnabled()) {
                    src_animal_icon = "yellowbirdup";
                    btn1.setBackgroundResource(R.drawable.shape_btn_choose);
                    btn2.setBackgroundResource(R.drawable.shape_btn_level2);
                    btn3.setBackgroundResource(R.drawable.shape_btn_level);
                    btn4.setBackgroundResource(R.drawable.shape_btn_level2);
                    btn5.setBackgroundResource(R.drawable.shape_btn_level);
                    btn6.setBackgroundResource(R.drawable.shape_btn_level2);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn2.isEnabled()) {
                    src_animal_icon = "a5";
                    btn2.setBackgroundResource(R.drawable.shape_btn_choose);
                    btn1.setBackgroundResource(R.drawable.shape_btn_level);
                    btn3.setBackgroundResource(R.drawable.shape_btn_level);
                    btn4.setBackgroundResource(R.drawable.shape_btn_level2);
                    btn5.setBackgroundResource(R.drawable.shape_btn_level);
                    btn6.setBackgroundResource(R.drawable.shape_btn_level2);
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn3.isEnabled()) {
                    src_animal_icon = "a3";
                    btn3.setBackgroundResource(R.drawable.shape_btn_choose);
                    btn2.setBackgroundResource(R.drawable.shape_btn_level2);
                    btn1.setBackgroundResource(R.drawable.shape_btn_level);
                    btn4.setBackgroundResource(R.drawable.shape_btn_level2);
                    btn5.setBackgroundResource(R.drawable.shape_btn_level);
                    btn6.setBackgroundResource(R.drawable.shape_btn_level2);
                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn4.isEnabled()) {
                    src_animal_icon = "a1";
                    btn4.setBackgroundResource(R.drawable.shape_btn_choose);
                    btn2.setBackgroundResource(R.drawable.shape_btn_level2);
                    btn3.setBackgroundResource(R.drawable.shape_btn_level);
                    btn1.setBackgroundResource(R.drawable.shape_btn_level);
                    btn5.setBackgroundResource(R.drawable.shape_btn_level);
                    btn6.setBackgroundResource(R.drawable.shape_btn_level2);
                }
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn5.isEnabled()) {
                    src_animal_icon = "a4";
                    btn5.setBackgroundResource(R.drawable.shape_btn_choose);
                    btn2.setBackgroundResource(R.drawable.shape_btn_level2);
                    btn3.setBackgroundResource(R.drawable.shape_btn_level);
                    btn4.setBackgroundResource(R.drawable.shape_btn_level2);
                    btn1.setBackgroundResource(R.drawable.shape_btn_level);
                    btn6.setBackgroundResource(R.drawable.shape_btn_level2);
                }
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn6.isEnabled()) {
                    src_animal_icon = "a2";
                    btn6.setBackgroundResource(R.drawable.shape_btn_choose);
                    btn2.setBackgroundResource(R.drawable.shape_btn_level2);
                    btn3.setBackgroundResource(R.drawable.shape_btn_level);
                    btn4.setBackgroundResource(R.drawable.shape_btn_level2);
                    btn1.setBackgroundResource(R.drawable.shape_btn_level);
                    btn5.setBackgroundResource(R.drawable.shape_btn_level);
                }
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variable.src_animal_icon = src_animal_icon;
                Intent intent = new Intent(LevelActivity.this, StartGameActivity.class);
                startActivity(intent);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LevelActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void load_level_activity() {
        btn1.setEnabled(true);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        btn5.setEnabled(false);
        btn6.setEnabled(false);

        lock1.setImageResource(R.drawable.icon_unlock);

        if (level >= 2) {
            btn2.setEnabled(true);
            lock2.setImageResource(R.drawable.icon_unlock);
        }
        if (level >= 3) {
            btn3.setEnabled(true);
            lock3.setImageResource(R.drawable.icon_unlock);
        }
        if (level >= 4) {
            btn4.setEnabled(true);
            lock4.setImageResource(R.drawable.icon_unlock);
        }
        if (level >= 5) {
            btn5.setEnabled(true);
            lock5.setImageResource(R.drawable.icon_unlock);
        }
        if (level >= 6) {
            btn6.setEnabled(true);
            lock6.setImageResource(R.drawable.icon_unlock);
        }
    }
}