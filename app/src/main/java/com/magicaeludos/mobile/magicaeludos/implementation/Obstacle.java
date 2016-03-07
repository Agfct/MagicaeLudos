package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.magicaeludos.mobile.magicaeludos.framework.Grid;

public class Obstacle extends GameObject {
    private Bitmap image;
    private GameContent content;
    private int x, y;
    private int dy;
    private Rect srcRect;
    private Rect destRect;
    private Grid grid;
    private int nr;


    public Obstacle(GameContent content, Bitmap spriteSheet, int nr){
//        super( content, point, width, height, spriteSheet);
        image = spriteSheet;
        this.content = content;
        grid = content.getGrid();
        x = grid.getLane(nr).x;
        y = grid.getLane(nr).y;
        this.nr = nr;
        srcRect = new Rect(0, 0, image.getHeight(), image.getWidth());
        destRect = new Rect(x, y, x+image.getWidth(), y+image.getHeight());
        dy = content.dy*4;;
    }

//    public void update(){
//        destRect.offset(0,dy);
//    }
//
//    public void draw(Canvas canvas){
//        Paint paint = new Paint();
//        canvas.drawBitmap(image, srcRect, destRect, paint);
//    }

    public Bitmap getImage(){return image;}

    public Rect getScrRect(){return srcRect;}

    public Rect getDestRect(){return destRect;}

    public void destRectOffset(){destRect.offset(0,dy);}

    public int getDy(){return dy;}
}
