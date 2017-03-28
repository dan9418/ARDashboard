package com.catchoom.test;

import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.craftar.CraftARBoundingBox;

public class TrackingBox {

    TextView overlayHeader;
    ImageView overlayBody;
    TextView overlayDescription;
    RelativeLayout cameraLayout;

    public TrackingBox(RelativeLayout layout, ImageView body, TextView header, TextView description) {
        cameraLayout = layout;
        overlayHeader = header;
        overlayBody = body;
        overlayDescription = description;
        reset();
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
        p.leftMargin = (int) leftSide;
        p.topMargin = (int) topSide;
        p.width = (int) (rightSide - leftSide);
        p.height = (int) (bottomSide - topSide);
        overlayBody.setLayoutParams(p);
        overlayHeader.bringToFront();

        // Force repaint
        overlayBody.invalidate();
    }

    public void reset() {
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) overlayBody.getLayoutParams();
        p.leftMargin = 0;
        p.topMargin = 0;
        p.width = 0;
        p.height = 0;
        overlayBody.setLayoutParams(p);
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

}