package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.magicaeludos.mobile.magicaeludos.implementation.activities.GameContent;
/**
 * Created by MortenAlver on 02.03.2016.
 */
public class Background {
    private Bitmap image;
    private GameContent content;
    private int x, y;
    private int dy;
    private Rect rect;

    public Background(GameContent content, Bitmap res){
        image = res;
        this.content = content;
        rect = new Rect(0,image.getHeight()/2,image.getWidth(), image.getHeight());
    }

    public void update(){
        rect.offset(0,-dy);
        if (rect.top <= 0){
            rect.offsetTo(0,image.getHeight()/2);
        }
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        canvas.drawBitmap(image, rect, content.getGrid().getScreenBorders(),paint);
    }

    private  Rect getDrect(){
        return  new Rect(0,0,image.getWidth(), image.getHeight()/2);
    }

    public int getBackgroundHeight(){return image.getHeight();}

    public void setDy(int dy){
        this.dy = dy;
    }
}
