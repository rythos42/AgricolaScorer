package com.geeksong.agricolascorer.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.AttributeSet;
import android.widget.RadioButton;
 
public class SegmentedControlButton extends RadioButton {
    private float mX;
        
    private GradientDrawable checkedGradient = new GradientDrawable(Orientation.TOP_BOTTOM, new int[] { 0xffdcdcdc, 0xff111111 });
    private GradientDrawable uncheckGradient = new GradientDrawable(Orientation.TOP_BOTTOM, new int[] { 0xffa5a5a5, 0xff000000 });
    private Paint textPaint = new Paint();
    private Paint borderPaint = new Paint();
 
    public SegmentedControlButton(Context context) {
        super(context);
        setupDrawables();
    }
 
    public SegmentedControlButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawables();
    }
 
    public SegmentedControlButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupDrawables();
    }
    
    private void setupDrawables() {
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Style.STROKE);
    }
 
    @Override
    public void onDraw(Canvas canvas) {
        String text = this.getText().toString();

        //float currentWidth = textPaint.measureText(text);
        float currentHeight = textPaint.measureText("x");
 
        textPaint.setTextSize(this.getTextSize());
 
        if (isChecked()) {
        	checkedGradient.setBounds(0, 0, this.getWidth(), this.getHeight());
        	checkedGradient.draw(canvas);
            textPaint.setColor(Color.WHITE);
        } else {
        	uncheckGradient.setBounds(0, 0, this.getWidth(), this.getHeight());
        	uncheckGradient.draw(canvas);
            textPaint.setColor(0xffcccccc);
        }
 
        //float w = (this.getWidth() / 2) - currentWidth;
        float h = (this.getHeight() / 2) + currentHeight;
        canvas.drawText(text, mX, h, textPaint);
 
        Rect rect = new Rect(0, 0, this.getWidth(), this.getHeight());
        canvas.drawRect(rect, borderPaint);
    }
 
    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        mX = w * 0.5f; // remember the center of the screen
    } 
}