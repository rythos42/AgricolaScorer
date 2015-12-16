package com.geeksong.agricolascorer.control;

import com.geeksong.agricolascorer.R;

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

		setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String input = editText.getText().toString();
				listener.onClick(thisDialog, DialogResult.OK, input);
			}
		});

		setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				listener.onClick(thisDialog, DialogResult.CANCEL, "");
			}
		});
	}
	
	public void setOnClickListener(OnClickListener listener) {
		this.listener = listener;
	}
}
