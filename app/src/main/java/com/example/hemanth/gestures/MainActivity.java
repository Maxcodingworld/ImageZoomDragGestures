package com.example.hemanth.gestures;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private TextView textview;
    private double lastdiff = 0;
    private ImageView image;
    private Bitmap bitmap;

    // Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);

        bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.image);

        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this,this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {

        if(e2.getPointerCount() == 2) {
            float x1 = e2.getX(0);
            float y1 = e2.getY(0);
            float x2 = e2.getX(1);
            float y2 = e2.getY(1);

            Double d3 = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));

            if( d3 <= lastdiff) {
                if(lastdiff!=0) {
                    image.setScaleX(Math.max(Math.min(image.getScaleX() - 0.1f, 4f), 0.5f));  //scale type is betwn 0.5 to 4
                    image.setScaleY(Math.max(Math.min(image.getScaleX() - 0.1f, 4f), 0.5f));

                    float windowwidth = getWindowManager().getDefaultDisplay().getWidth();
                    float windowheight = getWindowManager().getDefaultDisplay().getHeight();
                    float windowdiffX = Math.max((image.getWidth() * image.getScaleX()) - windowwidth,0);
                    float windowdiffY = Math.max((image.getHeight() * image.getScaleY()) - windowheight,0);
                    windowdiffX = windowdiffX/2;
                    windowdiffY = windowdiffY/2;
                    image.setTranslationY(Math.max(Math.min(image.getTranslationY(),windowdiffY),-windowdiffY));
                    image.setTranslationX(Math.max(Math.min(image.getTranslationX(),windowdiffX),-windowdiffX));
                }
                lastdiff = d3;

            }else {
                if(lastdiff!=0) {
                    image.setScaleX(Math.max(Math.min(image.getScaleX() + 0.1f, 4f), 0.5f));
                    image.setScaleY(Math.max(Math.min(image.getScaleX() + 0.1f, 4f), 0.5f));
                }
                lastdiff = d3;
            }
        }

        if(e2.getPointerCount() == 1){
            float x11 = e1.getX(0);
            float y11 = e1.getY(0);
            float x22 = e2.getX(0);
            float y22 = e2.getY(0);

            float dx = Math.abs(x11-x22);
            float dy = Math.abs(y11-y22);

            float windowwidth = getWindowManager().getDefaultDisplay().getWidth();
            float windowheight = getWindowManager().getDefaultDisplay().getHeight();

            float windowdiffX = Math.max((image.getWidth() * image.getScaleX()) - windowwidth,0);
            float windowdiffY = Math.max((image.getHeight() * image.getScaleY()) - windowheight,0);
            windowdiffX = windowdiffX/2;
            windowdiffY = windowdiffY/2;


            if(dx<=dy){
                if(y11<y22){
                    System.out.println("Going down");                                     // going down
                    image.setTranslationY(Math.min(image.getTranslationY() + 15,windowdiffY));
                }else{
                    System.out.println("Going up");                                          // going up
                    image.setTranslationY(Math.max(image.getTranslationY() - 15,-windowdiffY));
                }

            }else{
                if(x11<x22){
                    System.out.println("Going right");                                            // going right
                    image.setTranslationX(Math.min(image.getTranslationX() + 15,windowdiffX));
                }else{
                    System.out.println("Going left");                                            //going left
                    image.setTranslationX(Math.max(image.getTranslationX() - 15,-windowdiffX));
                }

            }
        }


        System.out.println("DistanceX :" + distanceX + "/n DistanceY :" + distanceY );
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        if(image.getScaleX()!=1f) {
            image.setScaleX(1f);   // image back to original
            image.setScaleY(1f);
            image.setTranslationY(0);
            image.setTranslationX(0);

        }else{
            image.setScaleX(2f);   // if original got to double
            image.setScaleY(2f);
        }
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        return true;
    }

}