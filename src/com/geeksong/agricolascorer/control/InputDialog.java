package com.geeksong.agricolascorer.control;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

public class InputDialog extends AlertDialog.Builder {
	private OnClickListener listener;
	
	public InputDialog(Context context) {
		super(context);

		final EditText editText = new EditText(context);
		setView(editText);
		
		final InputDialog thisDialog = this;

		setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String input = editText.getText().toString();
				listener.onClick(thisDialog, DialogResult.OK, input);
			}
		});

		setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				listener.onClick(thisDialog, DialogResult.CANCEL, "");
			}
		});
	}
	
	public void setOnClickListener(OnClickListener listener) {
		this.listener = listener;
	}
}
