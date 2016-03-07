package com.magicaeludos.mobile.magicaeludos.implementation;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.magicaeludos.mobile.magicaeludos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MortenAlver on 07.03.2016.
 */
public class ObstacleHandler {
    List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private GameContent content;

    public ObstacleHandler(GameContent content){
        this.content = content;
    }

    public void addObstacle(Obstacle obstacle){
        obstacles.add(obstacle);
    }

    public boolean checkCollition(){
        Rect hitBox = content.player.getHitBox();
        for (Obstacle o: obstacles){
            // TODO:: Check if player collides with oblstace
            o.getHitBox();
        }
        return false;
    }

    public void update(){
        // test until the probability is done
        double rand = Math.random();
        if (rand < 0.04) {
            int lane = (int) (3 * Math.random()) + 1;
            Obstacle o = new Obstacle(content,
                    BitmapFactory.decodeResource(content.getActivity().getResources(),
                            R.drawable.teardrop),lane);
            obstacles.add(o);
        }
        for (Obstacle o : obstacles) {
            o.destRectOffset();
        }
    }

    public void draw(Canvas canvas){
        for (Obstacle o : obstacles){
            Paint paint = new Paint();
            canvas.drawBitmap(o.getImage(), o.getScrRect(), o.getDestRect(),paint);
        }
    }
}
