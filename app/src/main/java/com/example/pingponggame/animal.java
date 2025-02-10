package com.example.pingponggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import java.util.ArrayList;

public class animal extends BaseObject{
    private ArrayList<Bitmap> arrBM = new ArrayList<>();
    private  Bitmap bitmap;
    private int idCurrentBitmap;
    private float drop;
    public ArrayList<Bitmap> getArrBM() {
        return arrBM;
    }

    /*public void setArrBM(ArrayList<Bitmap> arrBM) {
        this.arrBM = arrBM;
        for(int i=0; i<arrBM.size(); i++){
            arrBM.set(i, Bitmap.createScaledBitmap(arrBM.get(i), this.width, this.height, true));
        }
    }*/

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = Bitmap.createScaledBitmap(bitmap, this.width, this.height, true);
    }

    public float getDrop() {
        return drop;
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }

    public animal(){
        this.drop = 0;
        //this.idCurrentBitmap = 0;
    }
    public void draw(Canvas canvas){
        drop();
        canvas.drawBitmap(this.getBitmap(), this.x, this.y, null);
    }
    public Bitmap getBitmap(){
        /*Bitmap currentBitmap =arrBM.get(idCurrentBitmap);*/
        if(this.drop < 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(-25);
            /*return Bitmap.createBitmap(currentBitmap, 0, 0, currentBitmap.getWidth(), currentBitmap.getHeight(), matrix, true);*/
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } else {
            Matrix matrix = new Matrix();
            if(this.drop < 70) {
                matrix.postRotate(-25 + (drop * 2));
            } else {
                matrix.postRotate(45);
            }
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
    }
    public void drop(){
        this.drop+=0.6;
        this.y+=this.drop;
    }
}
