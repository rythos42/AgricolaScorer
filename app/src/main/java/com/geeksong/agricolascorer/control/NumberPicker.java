package com.geeksong.agricolascorer.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.geeksong.agricolascorer.R;

public class NumberPicker extends LinearLayout {
	private int number;
	private OnValueChangeListener listener;
	private final EditText editText;
	
	public NumberPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.setOrientation(LinearLayout.HORIZONTAL);
		
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.NumberPicker);
        int textColour = attributes.getColor(R.styleable.NumberPicker_android_textColor, Color.WHITE);
		attributes.recycle();
		
	    editText = new EditText(context);
	    editText.setText(String.format("%d", number));
	    editText.setTextColor(textColour);
	    editText.setPadding(editText.getPaddingLeft(), editText.getPaddingTop(), editText.getPaddingRight(), editText.getPaddingBottom() + 15);
	    
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
	
    public int getValue() {
        return number;
    }

    public void setValue(int number) {
		this.number = number;
		editText.setText(String.format("%d", number));
		listener.onValueChange(this.number);
	}
	
	public void setOnValueChangedListener(OnValueChangeListener listener) {
		this.listener = listener;
	}
	
	class IncrementListener implements View.OnClickListener {
		private final NumberPicker picker;
		private final EditText input;
		
		IncrementListener(NumberPicker picker, EditText input) {
			this.picker = picker;
			this.input = input;
		}
		
		public void onClick(View v) {
        	number++;
        	listener.onValueChange(number);
        	
        	input.setText(String.format("%d", number));
        }
	}
	
	class DecrementListener implements View.OnClickListener {
		private final NumberPicker picker;
		private final EditText input;
		
		DecrementListener(NumberPicker picker, EditText input) {
			this.picker = picker;
			this.input = input;
		}
		
		public void onClick(View v) {
        	number--;
        	if(number < 0) {
        		number = 0;
        		return;
        	}
        	
        	listener.onValueChange(number);
        	
        	input.setText(String.format("%d", number));
        }
	}
}
