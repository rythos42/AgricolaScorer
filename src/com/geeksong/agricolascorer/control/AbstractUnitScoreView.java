package com.geeksong.agricolascorer.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.geeksong.agricolascorer.R;

/**
 * Base view for segmented and pickers controls
 */
public abstract class AbstractUnitScoreView extends RelativeLayout {

    private final TextView scoreTextView;

    public AbstractUnitScoreView(Context context, AttributeSet attrs, int layoutId) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutId, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UnitScoreView, 0, 0);
        ((TextView) findViewById(R.id.label)).setText(a.getString(R.styleable.UnitScoreView_label));

        scoreTextView = (TextView) findViewById(R.id.unit_score);
        scoreTextView.setVisibility(a.getBoolean(R.styleable.UnitScoreView_hideScore, false)
                ? View.INVISIBLE : View.VISIBLE);
    }

    public void updateScore(int score) {
        scoreTextView.setText(String.valueOf(score));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    protected static class LayoutParams extends RelativeLayout.LayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        @Override
        protected void setBaseAttributes(TypedArray a,
                                         int widthAttr, int heightAttr) {

            if (a.hasValue(widthAttr)) {
                width = a.getLayoutDimension(widthAttr, "layout_width");
            } else {
                width = MATCH_PARENT;
            }

            if (a.hasValue(heightAttr)) {
                height = a.getLayoutDimension(heightAttr, "layout_height");
            } else {
                height = WRAP_CONTENT;
            }
        }
    }
}
