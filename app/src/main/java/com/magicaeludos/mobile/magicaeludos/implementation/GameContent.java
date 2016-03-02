package com.magicaeludos.mobile.magicaeludos.implementation;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.framework.Content;
import com.magicaeludos.mobile.magicaeludos.framework.Layout;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
import com.magicaeludos.mobile.magicaeludos.framework.Probability;

/**
 * Created by Anders on 10.02.2016.
 */
public class GameContent implements Content{

    private MotherActivity activity;
    private Layout layout;
    private Probability prop;

    public GameContent(MotherActivity activity, Layout layout) {
        this.activity = activity;
        this.layout = layout;
        prop = new Probability();
    }
    /**
     * Updates the game status. Update is ran every tick of the thread.
     * (How many ticks are based on the AndroidThread class current FPS setting)
     * Update should contain everything that needs to be updated in the game (movement, calculations.. everything!)
     */
    @Override
    public void update() {
        double test = prop.probExp(0.5,1.0/30.0);
        Log.w("GameContent", "Dette er test variabelen2: "+ test);
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
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawText("Its working! :D", 100, 100, paint);
        canvas.drawText("Its not working! :D", 200, 200, paint);
    }

}
