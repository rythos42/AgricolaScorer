package com.geeksong.agricolascorer.control;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class DatePickerFragment extends DialogFragment {
	private DatePickerDialog.OnDateSetListener onDateSetListener;
	private Date defaultDate;
	
	public void setDefault(Date defaultDate) {
		this.defaultDate = defaultDate;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int year, month, day;
		Calendar defaultCalendar = Calendar.getInstance();
		
		if(defaultDate.getTime() != 0L)
			defaultCalendar.setTime(defaultDate);
		
		year = defaultCalendar.get(Calendar.YEAR);
		month = defaultCalendar.get(Calendar.MONTH);
		day = defaultCalendar.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this.onDateSetListener, year, month, day);
	}
	
	public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
		this.onDateSetListener = onDateSetListener;
	}
}