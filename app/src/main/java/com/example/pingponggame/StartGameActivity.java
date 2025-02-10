package com.example.pingponggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pingponggame.R;

import pl.droidsonroids.gif.GifImageView;

public class StartGameActivity extends AppCompatActivity {
    public static TextView txt_score, lives, end_score;
    public static ImageButton btn_start, btn_continue, btn_back;
    public static ImageView lives_icon, logo;
    public static RelativeLayout rl_gameover;
    public static GameView gameview;
    public static GifImageView icon_eat_gift, icon_eat_heart, icon_heart_die;
    public static MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Screen.SCREEN_WIDTH = dm.widthPixels;
        Screen.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(R.layout.activity_start_game);
        txt_score = (TextView) findViewById(R.id.score_playing);
        lives = (TextView) findViewById(R.id.lives);
        btn_start = (ImageButton) findViewById(R.id.btn_start);
        lives_icon = (ImageView)findViewById(R.id.lives_icon);
        logo = (ImageView)findViewById(R.id.logo);
        gameview = (GameView) findViewById(R.id.gameview);
        end_score = (TextView)findViewById(R.id.txt_score_end);
        rl_gameover = (RelativeLayout)findViewById(R.id.layout_gameover);
        btn_continue = (ImageButton)findViewById(R.id.btn_continue);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        icon_eat_gift = (GifImageView)findViewById(R.id.img_icon_eat_gift);
        icon_eat_heart = (GifImageView)findViewById(R.id.img_icon_eat_heart) ;
        icon_heart_die = findViewById(R.id.icon_heart_die);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameview.setStart(true);
                logo.setVisibility(View.INVISIBLE);
                txt_score.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.INVISIBLE);
                lives.setVisibility(View.VISIBLE);
                lives_icon.setVisibility(View.VISIBLE);
                /*
                mediaPlayer = MediaPlayer.create(StartGameActivity.this, R.raw.bgr_music_playgame);
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
                Variable.mediaPlayer.stop();*/
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logo.setVisibility(View.VISIBLE);
                txt_score.setVisibility(View.INVISIBLE);
                btn_start.setVisibility(View.VISIBLE);
                lives.setVisibility(View.INVISIBLE);
                lives_icon.setVisibility(View.INVISIBLE);
                rl_gameover.setVisibility(View.INVISIBLE);
                gameview.setStart(false);
                gameview.reset();
                gameview.setVisibility(View.VISIBLE);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartGameActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });

    }
    public static void hide_icon_eat_gift(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                icon_eat_gift.setVisibility(View.INVISIBLE);
            }
        },2000);
    }
    public static void hide_icon_eat_heart(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                icon_eat_heart.setVisibility(View.INVISIBLE);
            }
        },2500);
    }
    public static void hide_icon_eat_die(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {icon_heart_die.setVisibility(View.INVISIBLE);
            }
        },2000);
    }
}