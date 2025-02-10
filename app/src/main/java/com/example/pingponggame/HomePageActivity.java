package com.example.pingponggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pingponggame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.common.returnsreceiver.qual.This;

import pl.droidsonroids.gif.GifImageButton;

public class HomePageActivity extends AppCompatActivity {
    TextView txt_best_score;
    FirebaseAuth auth;
    String userName, userID;
    LinearLayout layout_ranking;
    GifImageButton btn_ranking;
    ImageButton btn_ranking2;
    ImageButton btn_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ImageButton btn_startPlaying = (ImageButton) findViewById(R.id.btn_startplaying);
        TextView txt_hello = (TextView)findViewById(R.id.txt_hello);
        layout_ranking = (LinearLayout)findViewById(R.id.layout_btn_ranking);
        txt_best_score = (TextView)findViewById(R.id.txt_best_score);
        btn_ranking = (GifImageButton)findViewById(R.id.btn_gif_ranking);
        btn_ranking2 = (ImageButton)findViewById(R.id.btn_Ranking);
        btn_user = findViewById(R.id.btn_user);
        auth = FirebaseAuth.getInstance();
        //kiểm tra có đăng nhập hay không
        if (Variable.login==true){
            load_user();
            Variable.user = new score(userID, userName, Variable.best_score);
            if (userName != null) {
                txt_hello.setText("Welcome, " + Variable.user.getUserName());
            }
        }
        else{
            txt_hello.setText("Welcome to FLAPPY ANIMAL!");
            txt_best_score.setText(txt_best_score.getText()+String.valueOf(Variable.best_score));
            load_level();
        }
        btn_startPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, LevelActivity.class );
                startActivity(intent);
            }
        });
        btn_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, RankingActivity.class);
                startActivity(intent);
            }
        });
        btn_ranking2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, RankingActivity.class);
                startActivity(intent);
            }
        });
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_dialog_user(Gravity.CENTER);
            }
        });
        /*
        //music background
        Variable.mediaPlayer = MediaPlayer.create(this, R.raw.bgr_music);
        Variable.mediaPlayer.start();
        Variable.mediaPlayer.setLooping(true);*/
    }
    public void load_user(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference scoresRef = database.getReference("scores");

        userID = auth.getCurrentUser().getUid() ;
        userName = auth.getCurrentUser().getDisplayName();// Replace with actual playerId

        scoresRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    score playerScore = dataSnapshot.getValue(score.class);
                    if (playerScore != null) {
                        Variable.best_score = playerScore.getScore();
                        txt_best_score.setText(txt_best_score.getText()+String.valueOf(Variable.best_score));
                        load_level();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void load_level(){
        if (Variable.best_score>=40){
            Variable.level = 2;
        } if (Variable.best_score>=80) {
            Variable.level=3;
        } if (Variable.best_score>=140) {
            Variable.level = 4;
        } if (Variable.best_score>=200) {
            Variable.level = 5;
        } if (Variable.best_score>=250) {
            Variable.level = 6;
        }
    }
    public  void open_dialog_user(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.user_dialog_fragment);
        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(true);
        TextView userID, userName,score;
        ImageButton btn_logout, btn_exit;
        userID = dialog.findViewById(R.id.txt_userid);
        userName = dialog.findViewById(R.id.txt_username);
        score = dialog.findViewById(R.id.txt_score);
        btn_exit = dialog.findViewById(R.id.btn_exit);
        btn_logout = dialog.findViewById(R.id.btn_logout);
        userID.setText(userID.getText()+Variable.user.getUserID());
        userName.setText(userName.getText()+Variable.user.getUserName());
        score.setText(score.getText()+""+Variable.best_score);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();

    }
}