package com.catchoom.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.craftar.CraftARBoundingBox;

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

    public void assignBoxPosition(RelativeLayout layout, CraftARBoundingBox box) {
        int w = layout.getWidth();
        int h = layout.getHeight();

        TOP_SIDE = box.TLy * h;
        BOTTOM_SIDE = box.BLy * h;
        LEFT_SIDE = box.TLx * w;
        RIGHT_SIDE = box.TRx * w;
        invalidate();

        Log.d(TAG, "[" + w + "," + h + "]");
        Log.d(TAG, "TL(" + box.TLx + "," + box.TLy + ") TR(" + box.TRx + "," + box.TRx + "), BL(" + box.BLx + "," + box.BLx + ") BR(" + box.BRx + "," + box.BRx + ")");

        //Top Left
        assignPosition((TextView) layout.findViewById(R.id.topLeft), (int) (w * box.TLx), (int) (h * box.TLy));
        //Top Right
        assignPosition((TextView) layout.findViewById(R.id.topRight), (int) (w * box.TRx), (int) (h * box.TRy));
        //Bottom Left
        assignPosition((TextView) layout.findViewById(R.id.bottomLeft), (int) (w * box.BLx), (int) (h * box.BLy));
        //Bottom Right
        assignPosition((TextView) layout.findViewById(R.id.bottomRight), (int) (w * box.BRx), (int) (h * box.BRy));
    }

    public void assignPosition(TextView tv, int x, int y) {
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) tv.getLayoutParams();
        p.leftMargin = x;
        p.topMargin = y;
        tv.setLayoutParams(p);
        tv.setTextColor(Color.RED);
    }
}