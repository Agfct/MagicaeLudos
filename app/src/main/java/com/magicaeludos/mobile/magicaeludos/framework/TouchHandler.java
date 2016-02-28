/*
 * Copyright (c) 2016 Anders Lunde
 */
package com.magicaeludos.mobile.magicaeludos.framework;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import com.magicaeludos.mobile.magicaeludos.framework.Pool.PoolObjectFactory;

/**
 * Created by Anders on 25.02.2016.
 */
public class TouchHandler implements View.OnTouchListener{

    private static final int MAX_TOUCHPOINTS = 1;

    //List of all the touch points, if its touching, its x and y values and the id
    boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
    int[] touchX = new int[MAX_TOUCHPOINTS];
    int[] touchY = new int[MAX_TOUCHPOINTS];
    int[] id = new int[MAX_TOUCHPOINTS];
    Pool<TouchEvent> touchEventPool;
    List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
    List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
    float scaleX;
    float scaleY;

    public TouchHandler(View view, float scaleX, float scaleY){
        //Defines a pool object factory for TouchEvents
        PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };
        // factory and maz size
        touchEventPool = new Pool<TouchEvent>(factory, 100);

        //Sets this listener to a view
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            Log.w("TouchHandler","Type: "+ event.getAction());
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK ) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            int pointerCount = event.getPointerCount();
            TouchEvent touchEvent;
            for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
                if (i >= pointerCount) {
                    isTouched[i] = false;
                    id[i] = -1;
                    continue;
                }
                int pointerId = event.getPointerId(i);
                if (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
                    // if it's an up/down/cancel/out event, mask the id to see if we should process it for this touch
                    // point
                    continue;
                }
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_DOWN;
                        touchEvent.pointer = pointerId;
//                        touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
//                        touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
                        touchEvent.x = touchX[i] = (int) (event.getX(i));
                        touchEvent.y = touchY[i] = (int) (event.getY(i));
                        isTouched[i] = true;
                        id[i] = pointerId;
                        touchEventsBuffer.add(touchEvent);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_CANCEL:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_UP;
                        touchEvent.pointer = pointerId;
//                        touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
//                        touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
                        touchEvent.x = touchX[i] = (int) (event.getX(i));
                        touchEvent.y = touchY[i] = (int) (event.getY(i));
                        isTouched[i] = false;
                        id[i] = -1;
                        touchEventsBuffer.add(touchEvent);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                        touchEvent.pointer = pointerId;
//                        touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
//                        touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
                        touchEvent.x = touchX[i] = (int) (event.getX(i));
                        touchEvent.y = touchY[i] = (int) (event.getY(i));
                        isTouched[i] = true;
                        id[i] = pointerId;
                        touchEventsBuffer.add(touchEvent);
                        break;

                }
            }
            return true;
        }
    }


    //Returns if the pointer is down
    public boolean isTouchDown(int pointer){
        synchronized (this){
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS){
                return false;
            }else{
                return isTouched[index];
            }
        }
    }

    public int getTouchX(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchX[index];
        }
    }

    public int getTouchY(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchY[index];
        }
    }

    //Returns a list of all touchEvents
    public List<TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = touchEvents.size();
            for (int i = 0; i < len; i++)
                touchEventPool.free(touchEvents.get(i));
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }

    //Does not work, it only gives you the first event in the list, but there could be multiple events with the same id in the list.
//    public TouchEvent getSingleTouch(){
//        synchronized (this) {
//            int len = touchEvents.size();
//            for (int i = 0; i < len; i++)
//                touchEventPool.free(touchEvents.get(i));
//            touchEvents.clear();
//            touchEvents.addAll(touchEventsBuffer);
//            touchEventsBuffer.clear();
//            if(touchEvents.size() > 0){
//                return touchEvents.get(0);
//            }
//            return null;
//        }
//    }

    // returns the index for a given pointerId or -1 if no index.
    private int getIndex(int pointerId) {
        for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
            if (id[i] == pointerId) {
                return i;
            }
        }
        return -1;
    }

    public static class TouchEvent {
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_DRAGGED = 2;
        public static final int TOUCH_HOLD = 3;

        public int type;
        public int x, y;
        public int pointer;
    }

}
