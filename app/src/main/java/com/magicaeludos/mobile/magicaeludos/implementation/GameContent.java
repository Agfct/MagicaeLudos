package com.magicaeludos.mobile.magicaeludos.implementation;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.Content;
import com.magicaeludos.mobile.magicaeludos.framework.Grid;
import com.magicaeludos.mobile.magicaeludos.framework.Layout;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
import com.magicaeludos.mobile.magicaeludos.framework.TouchHandler;
import com.magicaeludos.mobile.magicaeludos.framework.TouchHandler.TouchEvent;

import java.util.ArrayList;
/**
 * Created by Anders on 10.02.2016.
 */
public class GameContent implements Content{

    private MotherActivity activity;
    private Layout layout;
    private Grid grid;
    private TouchHandler touchHandler;
    private Bitmap temporaryBackground;

    //Test
    ArrayList<Dummy> dummies;
    Paint paint = new Paint();

    public GameContent(MotherActivity activity, Layout layout) {
        this.activity = activity;
        this.layout = layout;
        this.grid = new Grid(this);
        this.touchHandler = new TouchHandler(layout, activity.getScreenWidth(), activity.getScreenHeight());

        //Test:
        temporaryBackground = BitmapFactory.decodeResource(activity.getResources(), R.drawable.teardrop);
        dummies = new ArrayList<>();
        dummies.add(new Dummy(getActivity(), grid.getLane(1), grid.getColWidth(), grid.getRowHeight()));
//        dummies.add(new Dummy(getActivity(),grid.getLane(2),grid.getColWidth(),grid.getRowHeight()));
        dummies.add(new Dummy(getActivity(), grid.getLane(3), grid.getColWidth(), grid.getRowHeight()));
    }

    /**
     * Updates the game status. Update is ran every tick of the thread.
     * (How many ticks are based on the AndroidThread class current FPS setting)
     * Update shoud contain everything that needs to be updated in the game (movement, calculations.. everything!)
     */
    @Override
    public void update() {
        //TODO: Discuss the use of single touch, and how to solve the issue of "no finger" on screen
        //TODO: Discuss the issue of no redraw on background
        TouchEvent input = touchHandler.getSingleTouch(); //Gets the current input finger






        if(input != null) { // Checks if there exsists a finger onscreen at all
            Log.w("GameContent", "x: " + input.x + " y: " + input.y + " id: " + input.pointer + " type: " + input.type);
            dummies.get(0).setX(input.x);
            dummies.get(0).setY(input.y);
        }
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

        //TODO: Explain that XML background does not work because surfaceView transparancy does not clear all pixels on every re-draw
        canvas.drawBitmap(temporaryBackground,new Rect(0,0,temporaryBackground.getWidth(),temporaryBackground.getWidth()),grid.getScreenBorders(),paint);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawText("Its working! :D", 100, 100, paint);
        canvas.drawText("Its not working! :D", 200, 200, paint);
        canvas.drawText("Its branching! :D", 300, 300, paint);



        //Test
        for (Dummy dummy: dummies
                ) {
            dummy.draw(canvas);
        }

        grid.draw(canvas);
    }

    public MotherActivity getActivity() {
        return activity;
    }
}
