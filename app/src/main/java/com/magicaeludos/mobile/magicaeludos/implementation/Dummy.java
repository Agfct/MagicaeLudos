package com.magicaeludos.mobile.magicaeludos.implementation;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
/**
 * Created by Anders on 24.02.2016.
 */
public class Dummy {

    private Bitmap tear;
    private int x,y;
    private int width, height;
    private Paint paint = new Paint();
    private Rect dstBounds, srcBounds;

    public Dummy(MotherActivity activity, Point point, int width, int height, int color){
        this.x = point.x;
        this.y = point.y;
        this.width = width;
        this.height = height;
        tear = BitmapFactory.decodeResource(activity.getResources(), R.drawable.teardrop);
        this.dstBounds = new Rect(x,y,x+width,y+height);
        this.srcBounds = new Rect(0,0,tear.getWidth(),tear.getHeight());
        paint.setColor(color);
    }

    public void draw(Canvas canvas){
        setDstBounds(new Rect(x, y, x + width, y + height));
//        canvas.drawBitmap(tear, srcBounds, getDstBounds(),paint);

        canvas.drawRect(dstBounds,paint);
    }

    public void setDstBounds(Rect dstBounds) {
        this.dstBounds = dstBounds;
    }

    public Rect getDstBounds() {
        return dstBounds;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
