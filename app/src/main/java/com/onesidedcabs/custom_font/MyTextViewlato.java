package com.onesidedcabs.custom_font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by one on 3/12/15.
 */
public class MyTextViewlato extends TextView {

    public MyTextViewlato(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextViewlato(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewlato(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/open-sans.light.ttf");
            setTypeface(tf);
        }
    }

}