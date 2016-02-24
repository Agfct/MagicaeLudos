package com.magicaeludos.mobile.magicaeludos.implementation;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.Content;
import com.magicaeludos.mobile.magicaeludos.framework.Layout;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
/**
 * Created by Anders on 10.02.2016.
 */
public class GameContent implements Content{

    private MotherActivity activity;
    private Layout layout;
    private Background bg;
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 480;
    private int x = 100;

    public GameContent(MotherActivity activity, Layout layout) {
        this.activity = activity;
        this.layout = layout;
        bg = new Background(BitmapFactory.decodeResource(activity.getResources(), R.mipmap.road3));
        bg.setVector(-5);
    }
    /**
     * Updates the game status. Update is ran every tick of the thread.
     * (How many ticks are based on the AndroidThread class current FPS setting)
     * Update shoud contain everything that needs to be updated in the game (movement, calculations.. everything!)
     */
    @Override
    public void update() {
        bg.update();
        x += 1;
    }

    /**
     * The draw method draws all the sprites, items, text, images that should be drawn (shown) on screen.
     * Draw is triggered after update in the AndroidThread, but it is important that there is no "update" here as
     * drawing is suposed to be seperated from updating (to prevent logical errors),
     * but there is also an option to skip frames which causes draw not to be drawn while the game will still update.
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        if(canvas!=null){
            bg.draw(canvas);
        }

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawText("Its working! :D " + nextGaussian(), x, 100, paint);

    }

}
