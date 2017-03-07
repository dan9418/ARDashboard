package com.catchoom.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created by Dan on 3/7/2017.
 */

public class TrackingBox extends View {

    public float LEFT_SIDE = 400;
    public float RIGHT_SIDE = 800;
    public float TOP_SIDE = 100;
    public float BOTTOM_SIDE = 500;
    private Paint paint = new Paint();

    TrackingBox(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) { // Override the onDraw() Method
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);

        //center
        int x0 = canvas.getWidth()/2;
        int y0 = canvas.getHeight()/2;
        int dx = canvas.getHeight()/3;
        int dy = canvas.getHeight()/3;
        //draw guide box
        //canvas.drawRect(x0-dx, y0-dy, x0+dx, y0+dy, paint);
        canvas.drawRect(LEFT_SIDE, TOP_SIDE, RIGHT_SIDE, BOTTOM_SIDE, paint);

        Log.d(TAG, "CONSTS: " + LEFT_SIDE + " " + TOP_SIDE + " " + RIGHT_SIDE + " " + BOTTOM_SIDE);
        Log.d(TAG, "PARAMS: " + (x0-dx) + " " + (y0-dy) + " " + (x0+dx) + " " + (y0+dy));
    }
}