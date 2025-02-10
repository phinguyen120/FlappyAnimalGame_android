package com.example.pingponggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class pipe extends BaseObject{
    public static int speed;
    private Boolean hide;
    public pipe(float x, float y, int width, int height){
        super(x, y, width, height);
        speed = 10*Screen.SCREEN_WIDTH/1080;
        hide =false;
    }
    public void draw(Canvas canvas){
        this.x -= speed;
        if(!hide){
            canvas.drawBitmap(this.bitmap, this.x, this.y, null);
        }
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        pipe.speed = speed;
    }

    public void randomY(){
        Random r = new Random();
        this.y = r.nextInt((0+this.height/4)+1)-this.height/4;
    }

    @Override
    public void setBitmap(Bitmap bm) {
        this.bitmap = Bitmap.createScaledBitmap(bm, width, height, true);
    }

}
