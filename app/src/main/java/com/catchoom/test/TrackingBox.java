package com.catchoom.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.craftar.CraftARBoundingBox;

public class TrackingBox extends View {

    TextView overlayHeader;
    ImageView overlayBody;
    TextView overlayDescription;
    RelativeLayout cameraLayout;

    public TrackingBox(Context context) {
        this(context, null);
    }

    public TrackingBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackingBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void reset() {
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) overlayBody.getLayoutParams();
        p.leftMargin = 0;
        p.topMargin = 0;
        p.width = 0;
        p.height = 0;
        overlayBody.setLayoutParams(p);
        invalidate();
    }

    public void assignPosition(CraftARBoundingBox box) {

        // Get dimensions
        int w = cameraLayout.getWidth();
        int h = cameraLayout.getHeight();

        // Calculate boundaries
        float topSide = box.TLy * h;
        float bottomSide = box.BLy * h;
        float leftSide = box.TLx * w;
        float rightSide = box.TRx * w;

        // Assign new positions
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) overlayBody.getLayoutParams();
        p.leftMargin = (int) (w * box.TLx);
        p.topMargin = (int) (h * box.TLy);
        p.width = (int) (rightSide - leftSide);
        p.height = (int) (bottomSide - topSide);
        overlayBody.setLayoutParams(p);
        overlayHeader.bringToFront();

        // Force repaint
        invalidate();

    }

    public void setHeaderText(String text) {
        if (overlayHeader != null) {
            overlayHeader.setText(text);
        }
    }

    public void setDescriptionText(String text) {
        if (overlayDescription != null) {
            overlayDescription.setText(text);
        }
    }

    public void setLayout(RelativeLayout layout) {
        this.cameraLayout = layout;
    }

    public void setHeader(TextView header) {
        this.overlayHeader = header;
    }

    public void setBody(ImageView body) {
        this.overlayBody = body;
    }

    public void setDescription(TextView description) {
        this.overlayDescription = description;
    }
}