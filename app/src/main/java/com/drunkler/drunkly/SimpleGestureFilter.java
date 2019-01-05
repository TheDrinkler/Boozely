package com.drunkler.drunkly;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

/**
 * This class is used to allow swiping controls on the app
 *
 * @author Oscar
 *
 * @reference Dr. Droid
 */
public class SimpleGestureFilter extends SimpleOnGestureListener {

    // Swipe gestures type
    final static int SWIPE_UP = 1;
    final static int SWIPE_DOWN = 2;
    final static int SWIPE_LEFT = 3;
    final static int SWIPE_RIGHT = 4;

    private final static int MODE_SOLID = 1;
    private final static int MODE_DYNAMIC = 2;

    private final static int ACTION_FAKE = -13; // just an unlikely number

    private int mode = MODE_DYNAMIC;
    private boolean running = true;
    private boolean tapIndicator = false;

    private Activity context;
    private GestureDetector detector;
    private SimpleGestureListener listener;

    /**
     * The method used to create the class from other classes
     * @param context the context of the activity that will use the class
     * @param simpleGestureListener Listener for the gesture of the user
     */
    SimpleGestureFilter(Activity context, SimpleGestureListener simpleGestureListener) {

        this.context = context;
        this.detector = new GestureDetector(context, this);
        this.listener = simpleGestureListener;
    }

    /**
     * Method to record once the user touches the screen
     * @param event The event of touching the screen
     */
    void onTouchEvent(MotionEvent event) {

        if (!this.running)
            return;

        boolean result = this.detector.onTouchEvent(event);
        // Get the gesture
        if (this.mode == MODE_SOLID)
            event.setAction(MotionEvent.ACTION_CANCEL);
        else if (this.mode == MODE_DYNAMIC) {

            if (event.getAction() == ACTION_FAKE)
                event.setAction(MotionEvent.ACTION_UP);
            else if (result)
                event.setAction(MotionEvent.ACTION_CANCEL);
            else if (this.tapIndicator) {
                event.setAction(MotionEvent.ACTION_DOWN);
                this.tapIndicator = false;
            }

        }
        // else just do nothing, it's Transparent
    }

    /**
     * See if the gesture means anything
     * @param status the status of the gesture
     */
    public void setEnabled(boolean status) {
        this.running = status;
    }

    /**
     * When the user touches the screen
     * @param e1 The X position of where the user touches the screen
     * @param e2 The Y position of where the user touches the screen
     * @param velocityX The speed of the gesture on the X axis
     * @param velocityY The speed of the gesture on the Y axis
     * @return The result of that gesture
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {

        final float xDistance = Math.abs(e1.getX() - e2.getX());
        final float yDistance = Math.abs(e1.getY() - e2.getY());

        int swipe_Max_Distance = 1000;
        if (xDistance > swipe_Max_Distance
                || yDistance > swipe_Max_Distance)
            return false;

        velocityX = Math.abs(velocityX);
        velocityY = Math.abs(velocityY);
        boolean result = false;

        int swipe_Min_Distance = 100;
        int swipe_Min_Velocity = 100;
        if (velocityX > swipe_Min_Velocity
                && xDistance > swipe_Min_Distance) {
            if (e1.getX() > e2.getX()) // right to left
                this.listener.onSwipe(SWIPE_LEFT);
            else
                this.listener.onSwipe(SWIPE_RIGHT);

            result = true;
        } else if (velocityY > swipe_Min_Velocity
                && yDistance > swipe_Min_Distance) {
            if (e1.getY() > e2.getY()) // bottom to up
                this.listener.onSwipe(SWIPE_UP);
            else
                this.listener.onSwipe(SWIPE_DOWN);

            result = true;
        }

        return result;
    }

    /**
     * When the user simply taps the screen
     * @param e The action  of touching the screen
     * @return  The result
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        this.tapIndicator = true;
        return false;
    }

    /**
     * Sames as above
     * @param arg the action
     * @return The result
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent arg) {

        if (this.mode == MODE_DYNAMIC) { // we owe an ACTION_UP, so we fake an
            arg.setAction(ACTION_FAKE); // action which will be converted to an
            // ACTION_UP later.
            this.context.dispatchTouchEvent(arg);
        }

        return false;
    }

    /**
     * The listener of the swipes gestures
     */
    interface SimpleGestureListener {
        void onSwipe(int direction);
    }
}