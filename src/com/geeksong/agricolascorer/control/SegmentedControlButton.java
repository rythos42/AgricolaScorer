package com.geeksong.agricolascorer.control;

import com.geeksong.agricolascorer.R;

import android.content.Context;
import android.content.res.TypedArray;
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
    private GradientDrawable checkedGradient;
    private GradientDrawable uncheckedGradient;
    private Paint checkedTextPaint;
    private Paint uncheckedTextPaint;
    private Paint borderPaint;
    private Rect rect = new Rect();
    
    public SegmentedControlButton(Context context) {
        super(context);
        init(null);
    }
 
    public SegmentedControlButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
 
    public SegmentedControlButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }
       
    private void init(AttributeSet attrs) { 
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SegmentedControlButton);
        int checkedTextColour = a.getColor(R.styleable.SegmentedControlButton_checkedTextColour, Color.WHITE);
        int checkedStartColour = a.getColor(R.styleable.SegmentedControlButton_checkedStartColour, 0xffdcdcdc);
        int checkedEndColour = a.getColor(R.styleable.SegmentedControlButton_checkedEndColour, 0xff111111);
        int uncheckedTextColour = a.getColor(R.styleable.SegmentedControlButton_uncheckedTextColour, 0xffcccccc);
        int uncheckedStartColour = a.getColor(R.styleable.SegmentedControlButton_uncheckedStartColour, 0xffa5a5a5);
        int uncheckedEndColour = a.getColor(R.styleable.SegmentedControlButton_uncheckedEndColour, 0xff000000);
        a.recycle();
        
        checkedTextPaint = new Paint();
        checkedTextPaint.setAntiAlias(true);
        checkedTextPaint.setTextAlign(Paint.Align.CENTER);
        checkedTextPaint.setColor(checkedTextColour);
        
        uncheckedTextPaint = new Paint();
        uncheckedTextPaint.setAntiAlias(true);
        uncheckedTextPaint.setTextAlign(Paint.Align.CENTER);
        uncheckedTextPaint.setColor(uncheckedTextColour);
        
        borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Style.STROKE);
        
        checkedGradient = new GradientDrawable(Orientation.TOP_BOTTOM, new int[] { checkedStartColour, checkedEndColour });
        uncheckedGradient = new GradientDrawable(Orientation.TOP_BOTTOM, new int[] { uncheckedStartColour, uncheckedEndColour });
    }
 
    @Override
    public void onDraw(Canvas canvas) {
        String text = this.getText().toString();
        Paint textPaint = (isChecked() ? checkedTextPaint : uncheckedTextPaint);
        GradientDrawable gradient = (isChecked() ? checkedGradient : uncheckedGradient);

        textPaint.setTextSize(this.getTextSize());
  
        int currentHeight = this.getHeight();
        int currentWidth = this.getWidth();
    	gradient.setBounds(0, 0, currentWidth, currentHeight);
    	gradient.draw(canvas);
    	
        float textHeight = textPaint.measureText("x");
    	float w = (currentWidth / 2);
    	float h = (currentHeight / 2) + textHeight;
        canvas.drawText(text, w, h, textPaint);
 
        rect.set(0, 0, currentWidth, currentHeight);
        canvas.drawRect(rect, borderPaint);
    }
}