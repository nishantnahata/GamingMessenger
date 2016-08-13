package com.games.gamingmessenger;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class BounceTheBall extends AppCompatActivity {

    private static final String TAG = "BounceTheBall";
    private ImageView ball;
    private FrameLayout bound;
    private int boundHeight,boundWidth;
    private double x,y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bounce_the_ball);

        final Handler handler=new Handler();
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                triggerFall();
            }
        };
        bound= (FrameLayout) findViewById(R.id.bound);
        ball = (ImageView) findViewById(R.id.ball_bounce);
        bound.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                boundHeight=bound.getHeight();
                boundWidth=bound.getWidth();
            }
        });
        ball.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ball.setX((boundWidth-ball.getWidth())/2);
                ball.setY(boundHeight-ball.getHeight());
            }
        });


        ball.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction()== MotionEvent.ACTION_DOWN) {
                    double cx = ball.getX()+ball.getWidth()/2 - 50;
                    double cy = ball.getY() + ball.getHeight()/2 - 50;
                    Log.d(TAG, "BallCenterX: " + cx);
                    Log.d(TAG, "BallCenterY: " + cy);
                    double x1 = event.getRawX() - cx;
                    double y1 = event.getRawY() - cy;
                    Log.d(TAG, "TouchX: " + x1);
                    Log.d(TAG, "TouchY: " + y1);

                    double z= Math.sqrt(x1*x1+y1*y1);
                    x1=-(250*x1)/z;
                    y1=-(500*y1)/z;
                    x = ball.getX() + x1 ;
                    y = ball.getY() + y1 ;
                    Log.d(TAG, "CalculatedX: " + x);
                    Log.d(TAG, "CalculatedY: " + y);
                    if (y < bound.getTop()) y = bound.getTop();
                    if (x < bound.getLeft()) x = bound.getLeft();
                    if (x > bound.getRight() - ball.getWidth())
                        x = bound.getRight() - ball.getWidth();
                    if (y > boundHeight - ball.getHeight())
                        y = boundHeight - ball.getHeight();
                    ball.animate()
                            .translationY((float) y)
                            .translationX((float) x)
                            .setDuration(300)
                            .setInterpolator(new DecelerateInterpolator())
                            .start();
                    handler.postDelayed(runnable, 300);
                    return true;
                }
                return false;
            }
        });
    }

    private void triggerFall() {
        ball.animate()
                .translationY(boundHeight-ball.getHeight())
                .setDuration(600)
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }

}
