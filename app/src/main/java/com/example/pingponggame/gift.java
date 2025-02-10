package com.example.pingponggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class gift extends BaseObject {
    public static int speed;

    public gift(float x, float y, int width, int height) {
        super(x, y, width, height);
        speed = 10*Screen.SCREEN_WIDTH/1080;
    }
    public void draw(Canvas canvas){
        this.x-=speed;
        canvas.drawBitmap(this.bitmap, this.x, this.y, null);
    }

    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        gift.speed = speed;
    }

    @Override
    public void setBitmap(Bitmap bm) {
        this.bitmap = Bitmap.createScaledBitmap(bm, width, height, true);
    }
}
