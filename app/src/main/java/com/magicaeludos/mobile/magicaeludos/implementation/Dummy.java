package com.magicaeludos.mobile.magicaeludos.implementation;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.TouchHandler;
import com.magicaeludos.mobile.magicaeludos.framework.TouchHandler.TouchEvent;
import com.magicaeludos.mobile.magicaeludos.implementation.activities.GameContent;

import java.util.List;
/**
 * Created by Anders on 24.02.2016.
 * This is a dummy
 */
public class Dummy {

    private GameContent content;
    private Bitmap tear;
    private int spriteX,spriteY;
    private int spriteWidth, spriteHeight;
    private int hitBoxX, hitBoxY;
    private int hitBoxWidth, hitBoxHeight;
    private Paint paint = new Paint();
    private int color;
    private Rect dstBounds, srcBounds;

    //The variables needed to check for a straight line drag
    private TouchHandler touchHandler;
    private int swipeBoxX, swipeBoxY; //The box that the swipe works inside.
    private int swipeBoxWidth, swipeBoxHeight;
    private int startX = 0;
    private int startY = 0;
    private int lastDraggedX = 0;
    private int lastDraggedY = 0;
    private int endX = 0;
    private int endY = 0;
    private int diagonalLength = 0;

    //To support multi touch we need to keep track of pointer id
    private int tEventId = -1;
    private boolean touch_state = false;
    private boolean dragged = false;

    //Lanes: Three different lane spots
    private Point currentLane;
    private Point lane1;
    private Point lane2;
    private Point lane3;

    public Dummy(GameContent content, Point point, int width, int height, int color){
        this.content = content;
        this.touchHandler = content.getTouchHandler();
        this.spriteX = point.x;
        this.spriteY = point.y;
        this.spriteWidth = width;
        this.spriteHeight = height;

        //Defining the box that registers swipe input
        this.swipeBoxX = 0;
        this.swipeBoxY = content.getGrid().getScreenHeight()/2;
        this.swipeBoxWidth = content.getGrid().getScreenWidth();
        this.swipeBoxHeight = swipeBoxY;

        //Defines the three lane spots that the player can move between
        this.lane1 = content.getGrid().getPlayerLane(1);
        this.lane2 = content.getGrid().getPlayerLane(2);
        this.lane3 = content.getGrid().getPlayerLane(3);
        this.currentLane = lane2;

        if(color == Color.BLUE){
            this.spriteX = currentLane.x;
            this.spriteY = currentLane.y;
        }

        Log.w("Dummy","SwipeX: " +swipeBoxX+" swipeY: "+ swipeBoxY+" swipe Width: " + swipeBoxWidth+ " swipeHeight: "+ spriteHeight);

        tear = BitmapFactory.decodeResource(content.getActivity().getResources(), R.drawable.teardrop);
        this.dstBounds = new Rect(spriteX, spriteY, spriteX +width, spriteY +height);
        this.srcBounds = new Rect(0,0,tear.getWidth(),tear.getHeight());

        this.color = color;
        paint.setColor(color);
    }

    public void update(List<TouchEvent> touchEvents){

        int tEventLen = touchEvents.size();
        for (int i = 0; i < tEventLen; i++) { //For all Touch events
            TouchEvent event = touchEvents.get(i);
            checkThumbSwipe(event);
        }

        movePlayer();

    }

    /**
     * Swipe validator method.
     * It calculates from the point of TOUCH_DOWN to the drag ends and a TOUCH_UP happens.
     */
    public void checkThumbSwipe(TouchEvent event){

        int temp_position_x = event.x;
        int temp_position_y = event.y;

        //If you touch inside the square
        if (event.x > swipeBoxX && event.x < swipeBoxX + swipeBoxWidth - 1 && event.y > swipeBoxY
                && event.y < swipeBoxY + swipeBoxHeight - 1) {
            Log.w("Dummy", "PRESSED INSIDE BOX");

            //If there are no previously touched events we keep the X and Y of the touch for further references
            if(tEventId == -1 ){
                Log.w("Dummy", "NR 1");
                startX = temp_position_x;
                startY = temp_position_y;
                touch_state = true;
                tEventId = event.pointer;

            }else{

                //If you touch up inside the box wihtout dragging first
                if(event.type == TouchEvent.TOUCH_UP && event.pointer == tEventId && !dragged){
                    Log.w("Dummy", "NR EXTRA -----------------------------------------------");
                    tEventId = -1;
                    touch_state = false;
                }
                //if you are dragging inside the square it keeps the current drag value for calculation.
                else if (event.type == TouchEvent.TOUCH_DRAGGED && touch_state && event.pointer == tEventId) {
                    Log.w("Dummy", "NR 2");
                    lastDraggedX = event.x;
                    lastDraggedY = event.y;
                    dragged = true;
                } else if (event.type == TouchEvent.TOUCH_UP && event.pointer == tEventId && dragged) {
                    Log.w("Dummy", "NR 3 -----------------------------------------------");
                    tEventId = -1;
                    touch_state = false;
                    dragged = false;
                    endX = event.x;
                    endY = event.y;
                    calculateCast(startX, startY, endX, endY);
                }
            }

        }else {
            //if one has an id and you release outside the square
            if (event.type == TouchEvent.TOUCH_UP && event.pointer == tEventId && dragged) {
                Log.w("Dummy", "NR 4 -----------------------------------------------");
                tEventId = -1;
                touch_state = false;
                dragged = false;
                endX = event.x;
                endY = event.y;
                calculateCast(startX,startY,lastDraggedX,lastDraggedY);
            }
        }

    }

    /**
     * calculates if the startX, StartY, and endX, endY is a valid swipe and if its an up/down left/right swipe.
     * Using a circle with 360 degrees as a reference to decide if the line dragged has the direction up/down or left/right.
     * It also takes into account the distance swiped to prevent the player from swiping in an unintended direction.
     * @param firstX
     * @param firstY
     * @param lastX
     * @param lastY
     */
    private void calculateCast(int firstX, int firstY, int lastX, int lastY){


        float x = lastX - firstX;
        float y = lastY - firstY;

        Log.w("Slingshot", " Is X or Y 0 ?.  X: " + x + " Y: " + y);
        //If both swipeBoxX and swipeBoxY is zero that means the start and end points are the same, this is not a valid cast.
        if(!(x == 0 && y == 0)) {
            double degree = -1;

            //This calculates the degree
            if (x >= 0 && y >= 0)
                degree = Math.toDegrees(Math.atan(y / x));
            else if (x < 0 && y >= 0)
                degree = Math.toDegrees(Math.atan(y / x)) + 180;
            else if (x < 0 && y < 0)
                degree = Math.toDegrees(Math.atan(y / x)) + 180;
            else if (x >= 0 && y < 0)
                degree = Math.toDegrees(Math.atan(y / x)) + 360;
            Log.e("Slingshot", "Degree: " + degree);

            //Sets the lane for the player
            //Left is between 126 & 234 degrees
            if(degree >= 126 && degree <= 234){
                setLaneToLeft();
            }else if ( (degree <= 54 && degree >= 0) || (degree >= 306 && degree <= 360)){
                setLaneToRight();
            }else if (degree > 54 && degree < 126 ){
                Log.w("Dummy", "Sliding DOWN");
            } else if (degree > 234 && degree < 306){
                Log.w("Dummy", "Jumping UP");
            }
        }
    }

    private void setLaneToLeft(){
        if(currentLane == lane3){
            currentLane = lane2;
        }else if (currentLane == lane2){
            currentLane = lane1;
        }
    }

    private void setLaneToRight(){
        if(currentLane == lane1){
            currentLane = lane2;
        }else if (currentLane == lane2){
            currentLane = lane3;
        }
    }
    /**
     * Moves the player to the current lane, the player only moves on the horizontal (x) axis
     * //TODO: Make shure that the value of x can be exacly "x" or else the player wont stop
     */
    private void movePlayer(){
        if(spriteX < currentLane.x){
            spriteX +=21; //TODO: Not scalable fix.
        }else if(spriteX > currentLane.x){
            spriteX -=21; //TODO: Not scalable fix.
        }
    }

    public void draw(Canvas canvas){
        setDstBounds(new Rect(spriteX, spriteY, spriteX + spriteWidth, spriteY + spriteHeight));
//        canvas.drawBitmap(tear, srcBounds, getDstBounds(),paint);

        canvas.drawRect(dstBounds,paint);

        //Draws additional information if development mode is on
        if(color == Color.BLUE) {

            paint.setColor(Color.BLUE);
            canvas.drawRect(startX, startY, startX + 5, startY + 5, paint);
            paint.setColor(Color.RED);
            canvas.drawRect(lastDraggedX, lastDraggedY, lastDraggedX + 5, lastDraggedY + 5, paint);
        }
        paint.setColor(color);
    }

    public void setDstBounds(Rect dstBounds) {
        this.dstBounds = dstBounds;
    }

    public Rect getDstBounds() {
        return dstBounds;
    }

    //These needs to set corrent sprite X and correct hitBox x.
    public void setPlayerX(){

    }

    public void setPlayerY(){

    }
}
