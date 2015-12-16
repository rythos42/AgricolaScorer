package com.geeksong.agricolascorer.control;

import android.content.Context;
import android.util.AttributeSet;
import com.geeksong.agricolascorer.R;

public class PickerUnitScoreView extends AbstractUnitScoreView {

    private NumberPicker numberPicker;

    public PickerUnitScoreView(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.picker_unit_score_view);
        numberPicker = (NumberPicker) findViewById(R.id.picker);
    }

    public NumberPicker getNumberPicker() {
        return numberPicker;
    }
}
