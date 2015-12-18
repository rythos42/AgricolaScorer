package com.geeksong.agricolascorer.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import com.geeksong.agricolascorer.R;

public class SegmentedUnitScoreView extends AbstractUnitScoreView {
    /**
     * RadioGroup where to add nested children
     */
    private RadioGroup radioGroup;
    private final int segmentCount;

    public SegmentedUnitScoreView(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.segmented_unit_score_view);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.UnitScoreView, 0, 0);
        segmentCount = attributes.getInt(R.styleable.UnitScoreView_segmentCount, 0);
        attributes.recycle();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        // Intercept nested children to put them inside radioGroup
        if (child instanceof SegmentedControlButton) {
            // Use RadioGroup.LayoutParams instead of current Relative.LayoutParams
            // to custom attributes such as "weight"
            radioGroup.addView(child, index, createRadioGroupLayoutParams());
        } else {
            if (child instanceof RadioGroup) {
                radioGroup = (RadioGroup) child;
            }
            super.addView(child, index, params);
        }
    }

    private ViewGroup.LayoutParams createRadioGroupLayoutParams() {
        return new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, segmentCount);
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    protected static class LayoutParams extends AbstractUnitScoreView.LayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
    }
}
