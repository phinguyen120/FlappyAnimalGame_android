package com.example.pingponggame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.pingponggame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {
    private ArrayList<gift> arrGifts;
    private ArrayList<heart> arrHearts;
    private animal animal;
    private Handler handler, handlergift, handlerheart, handler_stop_mp3 = new Handler();
    private Runnable r, r_gift, r_heart;
    private ArrayList<pipe> arrPipes;
    private int sumpipe, distance;
    private int score, heart_count;
    private boolean start;
    private int[] arrSeconds = {7000, 9000, 13000, 18000};
    private int lastCollidedPipeIndex = -1;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        score = 0;
        heart_count=0;
        start = false;
        arrHearts = new ArrayList<>();
        arrGifts = new ArrayList<>();
        initAnimal();
        initPipe();
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        init_icon();

    }
    private int getRandomTime() {
        Random random = new Random();
        int index = random.nextInt(arrSeconds.length);
        return arrSeconds[index];
    }
    private void init_icon(){
        handlergift = new Handler();
        handlerheart = new Handler();
        r_gift = new Runnable() {
            @Override
            public void run() {
                if (start) {
                   createGift();
                }
                handlergift.postDelayed(this, getRandomTime());
            }
        };
        handlergift.postDelayed(r_gift, getRandomTime());
        r_heart = new Runnable() {
            @Override
            public void run() {
                if (start){
                    init_heart();
                }
                handlerheart.postDelayed(r_heart, 15000);
            }
        };
        handlerheart.postDelayed(r_heart, 15000);
    }

    private void initPipe() {
        sumpipe = 4;
        distance = 500*Screen.SCREEN_HEIGHT/1920;
        arrPipes = new ArrayList<>();
        for(int i = 0; i < sumpipe; i++){
            if(i < sumpipe/2){
                this.arrPipes.add(new pipe(Screen.SCREEN_WIDTH+i*((Screen.SCREEN_WIDTH+200*Screen.SCREEN_WIDTH/1080)/(sumpipe/2)),
                        0, 200*Screen.SCREEN_WIDTH/1080, Screen.SCREEN_HEIGHT/2));
                this.arrPipes.get(this.arrPipes.size()-1).setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe_red_top));
                this.arrPipes.get(this.arrPipes.size()-1).randomY();
            }else{
                this.arrPipes.add(new pipe(this.arrPipes.get(i-sumpipe/2).getX(), this.arrPipes.get(i-sumpipe/2).getY()
                        +this.arrPipes.get(i-sumpipe/2).getHeight() + this.distance,
                        200*Screen.SCREEN_WIDTH/1080, Screen.SCREEN_HEIGHT/2));
                this.arrPipes.get(this.arrPipes.size()-1).setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe_red));
            }
        }
    }
    public void init_heart() {
        boolean check = true;
        while (check) {
            float heartY = new Random().nextInt(Screen.SCREEN_HEIGHT / 2) + Screen.SCREEN_HEIGHT / 4;
            heart heart = new heart(Screen.SCREEN_WIDTH, heartY, 110 * Screen.SCREEN_WIDTH / 1080, 110 * Screen.SCREEN_HEIGHT / 1920);
            heart.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_heart));

            boolean intersects = false, intersects2 = false;
            Rect heartRect = heart.getRect();
            for (int i = 0; i < arrPipes.size(); i++) {
                Rect pipeRect = arrPipes.get(i).getRect();
                if (Rect.intersects(heartRect, pipeRect)) {
                    intersects = true;
                    break;
                }
            }
            if (arrGifts != null) {
                for (int i = 0; i < arrGifts.size(); i++) {
                    Rect giftRect = arrGifts.get(i).getRect();
                    if (Rect.intersects(heartRect, giftRect)) {
                        intersects2 = true;
                        break;
                    }
                }
            }

            if (!intersects && !intersects2) {
                arrHearts.add(heart);
                check = false;
            }
        }
    }
    private void createGift() {
        arrGifts = new ArrayList<>();
        boolean check = true;
        while (check) {
            float giftY = new Random().nextInt(Screen.SCREEN_HEIGHT / 2) + Screen.SCREEN_HEIGHT / 4;
            gift gift = new gift(Screen.SCREEN_WIDTH, giftY, 150 * Screen.SCREEN_WIDTH / 1080, 150 * Screen.SCREEN_HEIGHT / 1920);
            gift.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_gift));

            boolean intersects = false;
            Rect giftRect = gift.getRect();
            for (int i = 0; i < arrPipes.size(); i++) {
                Rect pipeRect = arrPipes.get(i).getRect();
                if (Rect.intersects(giftRect, pipeRect)) {
                    intersects = true;
                    break;
                }
            }
            if (!intersects) {
                arrGifts.add(gift);
                check = false;
            }
        }
    }
    private void initAnimal(){
        animal = new animal();
        animal.setWidth(100*Screen.SCREEN_WIDTH/1080);
        animal.setHeight(100*Screen.SCREEN_HEIGHT/1920);
        animal.setX(100*Screen.SCREEN_WIDTH/1080);
        animal.setY(Screen.SCREEN_HEIGHT/2-animal.getHeight()/2);
        int resourceID = getResources().getIdentifier(Variable.src_animal_icon, "drawable", getContext().getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), resourceID);
        animal.setBitmap(bitmap);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (start) {
            animal.draw(canvas);
            for (int i = 0; i < arrPipes.size(); i++) {
                if ((animal.getRect().intersect(arrPipes.get(i).getRect()) ||
                        animal.getY() - animal.getHeight() < 0 ||
                        animal.getY() > Screen.SCREEN_HEIGHT) && i!=lastCollidedPipeIndex){
                    lastCollidedPipeIndex = i;
                    /*
                    MediaPlayer mediaPlayer;
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.hit);
                    stop_MediaPlayer(mediaPlayer);*/
                    if (heart_count > 0) {
                        heart_count--;
                        StartGameActivity.icon_heart_die.setVisibility(VISIBLE);
                        StartGameActivity.hide_icon_eat_die();
                        StartGameActivity.lives.setText("" + heart_count);
                        arrPipes.get(lastCollidedPipeIndex).setHide(true);
                    } else {
                        pipe.speed = 0;
                        StartGameActivity.rl_gameover.setVisibility(VISIBLE);
                        StartGameActivity.end_score.setText(StartGameActivity.txt_score.getText());
                        StartGameActivity.lives.setVisibility(INVISIBLE);
                        StartGameActivity.lives_icon.setVisibility(INVISIBLE);
                        StartGameActivity.txt_score.setVisibility(INVISIBLE);
                        StartGameActivity.gameview.setVisibility(INVISIBLE);
                        StartGameActivity.icon_eat_gift.setVisibility(INVISIBLE);
                        StartGameActivity.icon_heart_die.setVisibility(INVISIBLE);
                        StartGameActivity.icon_eat_heart.setVisibility(INVISIBLE);
                        /*
                        Variable.mediaPlayer = MediaPlayer.create(getContext(), R.raw.bgr_music);
                        Variable.mediaPlayer.start();
                        Variable.mediaPlayer.setLooping(true);
                        StartGameActivity.mediaPlayer.stop();*/
                        if (score > Variable.best_score) {
                            Variable.best_score = score;
                            if (Variable.login) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference scoresRef = database.getReference("scores");
                                String playerId = Variable.user.getUserID();
                                String playerName = Variable.user.getUserName();
                                scoresRef.child(playerId).runTransaction(new Transaction.Handler() {
                                    @NonNull
                                    @Override
                                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                                        score playerScore = mutableData.getValue(score.class);
                                        if (playerScore == null) {
                                            playerScore = new score(playerId, playerName, score);
                                            mutableData.setValue(playerScore);
                                        } else {
                                            playerScore.setScore(score);
                                            playerScore.setUserName(playerName);
                                            mutableData.setValue(playerScore);
                                        }
                                        return Transaction.success(mutableData);
                                    }

                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                                    }

                                });
                            }
                        }
                    }
                }
                if (lastCollidedPipeIndex == i && this.animal.getX() > arrPipes.get(i).getX() + arrPipes.get(i).getWidth()) {
                    lastCollidedPipeIndex = -1;
                }

                if (this.animal.getX() + this.animal.getWidth() > arrPipes.get(i).getX() + arrPipes.get(i).getWidth() / 2 &&
                        this.animal.getX() + this.animal.getWidth() <= arrPipes.get(i).getX() + arrPipes.get(i).getWidth() / 2 + pipe.speed &&
                        i < sumpipe / 2) {
                    score++;
                    StartGameActivity.txt_score.setText("" + score);
                }

                if (this.arrPipes.get(i).getX() < -arrPipes.get(i).getWidth()) {
                    this.arrPipes.get(i).setX(Screen.SCREEN_WIDTH);
                    if(this.arrPipes.get(i).getHide()==true){
                        this.arrPipes.get(i).setHide(false);
                    }
                    if (i < sumpipe / 2) {
                        arrPipes.get(i).randomY();
                    } else {
                        arrPipes.get(i).setY(this.arrPipes.get(i - sumpipe / 2).getY()
                                + this.arrPipes.get(i - sumpipe / 2).getHeight() + this.distance);
                    }
                }
                this.arrPipes.get(i).draw(canvas);
            }

            if (arrGifts != null) {
                for (int i = 0; i < arrGifts.size(); i++) {
                    gift gift = arrGifts.get(i);
                    gift.draw(canvas);
                    if (animal.getRect().intersect(gift.getRect())) {
                        score += 5;
                        /*
                        MediaPlayer mediaPlayer;
                        mediaPlayer = MediaPlayer.create(getContext(), R.raw.point);
                        stop_MediaPlayer(mediaPlayer);*/
                        StartGameActivity.txt_score.setText("" + score);
                        StartGameActivity.icon_eat_gift.setVisibility(VISIBLE);
                        StartGameActivity.hide_icon_eat_gift();
                        arrGifts.remove(i);
                        i--;
                    }
                    if (gift.getX() < -gift.getWidth()) {
                        arrGifts.remove(i);
                        i--;
                    }
                }
            }
            if (arrHearts != null) {
                for (int i = 0; i < arrHearts.size(); i++) {
                    heart heart = arrHearts.get(i);
                    heart.draw(canvas);
                    if (animal.getRect().intersect(heart.getRect())) {
                        heart_count += 1;
                        /*
                        MediaPlayer mediaPlayer;
                        mediaPlayer = MediaPlayer.create(getContext(), R.raw.point);
                        stop_MediaPlayer(mediaPlayer);*/
                        StartGameActivity.icon_eat_heart.setVisibility(VISIBLE);
                        StartGameActivity.hide_icon_eat_heart();
                        StartGameActivity.lives.setText("" + heart_count);
                        arrHearts.remove(i);
                        i--;
                    }
                    if (heart.getX() < -heart.getWidth()) {
                        arrHearts.remove(i);
                        i--;
                    }
                }
            }
        } else {
            if (animal.getY() > Screen.SCREEN_HEIGHT / 2) {
                animal.setDrop(-15 * Screen.SCREEN_HEIGHT / 1920);
            }
            animal.draw(canvas);
        }
        handler.postDelayed(r, 10);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            animal.setDrop(-15);
        }
        return true;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void reset() {
        StartGameActivity.txt_score.setText("0");
        score = 0;
        heart_count=0;
        arrHearts = new ArrayList<>();
        arrGifts = new ArrayList<>();
        initPipe();
        initAnimal();
    }
    private void stop_MediaPlayer(MediaPlayer mediaPlayer){
        mediaPlayer.start();
        handler_stop_mp3.postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
            }
        },1000);
    }
}
