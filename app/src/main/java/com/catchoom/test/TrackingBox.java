package com.catchoom.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.craftar.CraftARBoundingBox;

import static android.content.ContentValues.TAG;

/**
 * Created by Dan on 3/7/2017.
 */

public class TrackingBox extends View {

    public float LEFT_SIDE = 0;
    public float RIGHT_SIDE = 0;
    public float TOP_SIDE = 0;
    public float BOTTOM_SIDE = 0;
    private Paint paint = new Paint();
    private String title = "";
    private String description = "";


    public TrackingBox(Context context) {
        this(context, null);
    }

    public TrackingBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackingBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    private void initPaints() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) { // Override the onDraw() Method
        super.onDraw(canvas);
        //canvas.drawRect(LEFT_SIDE, TOP_SIDE, RIGHT_SIDE, BOTTOM_SIDE, paint);

        //Log.d(TAG, "CONSTS: " + LEFT_SIDE + " " + TOP_SIDE + " " + RIGHT_SIDE + " " + BOTTOM_SIDE);
        //Log.d(TAG, "PARAMS: " + (x0-dx) + " " + (y0-dy) + " " + (x0+dx) + " " + (y0+dy));
    }

    public void assignBoxPosition(RelativeLayout layout, CraftARBoundingBox box) {
        int w = layout.getWidth();
        int h = layout.getHeight();

        TOP_SIDE = box.TLy * h;
        BOTTOM_SIDE = box.BLy * h;
        LEFT_SIDE = box.TLx * w;
        RIGHT_SIDE = box.TRx * w;

        ImageView body = (ImageView) layout.findViewById(R.id.overlay_1);
        TextView header = (TextView) layout.findViewById(R.id.component_name);

        assignPosition(body, (int) (w * box.TLx), (int) (h * box.TLy), (int) (RIGHT_SIDE-LEFT_SIDE), (int) (BOTTOM_SIDE-TOP_SIDE));
        header.bringToFront();

        invalidate();

    }

    public void assignPosition(View v, int x, int y, int w, int h) {
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) v.getLayoutParams();
        p.leftMargin = x;
        p.topMargin = y;
        p.width = w;
        p.height = h;
        v.setLayoutParams(p);
    }

    public void assignPosition(View v, int x, int y) {
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) v.getLayoutParams();
        p.leftMargin = x;
        p.topMargin = y;
        v.setLayoutParams(p);
    }
}