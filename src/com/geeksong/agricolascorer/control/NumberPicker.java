package com.geeksong.agricolascorer.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NumberPicker extends LinearLayout {
	private int number;
	
	public NumberPicker(Context context, AttributeSet attributes) {
		super(context);
		
		this.setOrientation(LinearLayout.HORIZONTAL);
		
	    final EditText editText = new EditText(context);
	    editText.setText(Integer.toString(number));
	    
	    Button decrementButton = new Button(context);
	    decrementButton.setText("-");
	    decrementButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	number--;
	            editText.setText(Integer.toString(number));
	        }
	    });
	    
	    Button incrementButton = new Button(context);
	    incrementButton.setText("+");
	    incrementButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	number++;
	            editText.setText(Integer.toString(number));
	        }
	    });
	    
	    addView(decrementButton);
	    addView(editText);
	    addView(incrementButton);
	}
}
