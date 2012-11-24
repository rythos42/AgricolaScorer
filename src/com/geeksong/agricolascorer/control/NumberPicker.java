package com.geeksong.agricolascorer.control;

import com.geeksong.agricolascorer.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NumberPicker extends LinearLayout {
	private int number;
	private OnValueChangeListener listener;
	private int minimum = Integer.MIN_VALUE;
	private EditText editText;
	
	public NumberPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.setOrientation(LinearLayout.HORIZONTAL);
		
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NumberPicker);
        int textColour = a.getColor(R.styleable.NumberPicker_android_textColor, Color.WHITE);
		
	    editText = new EditText(context);
	    editText.setText(Integer.toString(number));
	    editText.setTextColor(textColour);
	    
	    Button decrementButton = new Button(context);
	    decrementButton.setText("-");
	    decrementButton.setOnClickListener(new DecrementListener(this, editText));
	    
	    Button incrementButton = new Button(context);
	    incrementButton.setText("+");
	    incrementButton.setOnClickListener(new IncrementListener(this, editText));
	    
	    addView(incrementButton, getButtonLayout());
	    addView(editText);
	    addView(decrementButton, getButtonLayout());
	}
	
	private LinearLayout.LayoutParams getButtonLayout() {
	    LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
	    layout.weight = 1.0f;
	    return layout;		
	}
	
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
	
	int getMinimum() {
		return this.minimum;
	}
	
	public void setValue(int number) {
		int oldVal = this.number;
		this.number = number;
		editText.setText(Integer.toString(number));
		listener.onValueChange(this, oldVal, this.number);
	}
	
	public void setOnValueChangedListener(OnValueChangeListener listener) {
		this.listener = listener;
	}
	
	protected class IncrementListener implements View.OnClickListener {
		private NumberPicker picker;
		private EditText input;
		
		protected IncrementListener(NumberPicker picker, EditText input) { 
			this.picker = picker;
			this.input = input;
		}
		
		public void onClick(View v) {
        	int oldVal = number;
        	number++;
        	listener.onValueChange(picker, oldVal, number);
        	
        	input.setText(Integer.toString(number));
        }
	}
	
	protected class DecrementListener implements View.OnClickListener {
		private NumberPicker picker;
		private EditText input;
		
		protected DecrementListener(NumberPicker picker, EditText input) { 
			this.picker = picker;
			this.input = input;
		}
		
		public void onClick(View v) {
        	int oldVal = number;
        	number--;
        	if(number < picker.getMinimum()) {
        		number = picker.getMinimum();
        		return;
        	}
        	
        	listener.onValueChange(picker, oldVal, number);
        	
        	input.setText(Integer.toString(number));
        }
	}
}
