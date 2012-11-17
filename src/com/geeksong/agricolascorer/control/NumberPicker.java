package com.geeksong.agricolascorer.control;

import android.content.Context;
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
	
	public NumberPicker(Context context, AttributeSet attributes) {
		super(context, attributes);
		
		this.setOrientation(LinearLayout.HORIZONTAL);
		
	    editText = new EditText(context);
	    editText.setText(Integer.toString(number));
	    
	    Button decrementButton = new Button(context);
	    decrementButton.setText("-");
	    decrementButton.setOnClickListener(new DecrementListener(this, editText));
	    
	    Button incrementButton = new Button(context);
	    incrementButton.setText("+");
	    incrementButton.setOnClickListener(new IncrementListener(this, editText));
	    
	    addView(decrementButton);
	    addView(editText);
	    addView(incrementButton);
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
