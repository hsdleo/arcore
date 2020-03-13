package com.google.ar.sceneform.samples.common.helpers;
import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class EditTextAlwaysLast extends AppCompatEditText {

    public EditTextAlwaysLast(Context context) {
        super(context);
    }

    public EditTextAlwaysLast(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextAlwaysLast(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        //if just tap - cursor to the end of row, if long press - selection menu
        if (selStart==selEnd)
            setSelection(getText().length());
        super.onSelectionChanged(selStart, selEnd);
    }

}