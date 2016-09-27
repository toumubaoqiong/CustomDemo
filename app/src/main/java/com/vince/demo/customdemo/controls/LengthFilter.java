package com.vince.demo.customdemo.controls;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

import com.vince.demo.customdemo.R;

/**
 * decription ：EditText到达最大值时给出提示
 * author ： zhua
 * Created at 2016/9/27.
 */
public class LengthFilter implements InputFilter {

    private int mMax;
    private Context mContext;
    private Toast mErrorToast;

    public LengthFilter(Context context, int max) {
        mMax = max;
        mContext = context;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        int keep = mMax - (dest.length() - (dend - dstart));

        if (keep <= 0) {
            if (mErrorToast == null) {
                mErrorToast = Toast.makeText(mContext, R.string.tip,
                        Toast.LENGTH_SHORT);
            }
            mErrorToast.show();
            return "";
        } else if (keep >= end - start) {
            return null; // keep original
        } else {
            keep += start;
            if (mErrorToast == null) {
                mErrorToast = Toast.makeText(mContext, R.string.tip,
                        Toast.LENGTH_SHORT);
            }
            mErrorToast.show();
            if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                --keep;
                if (keep == start) {
                    return "";
                }
            }
            return source.subSequence(start, keep);
        }
    }
}
