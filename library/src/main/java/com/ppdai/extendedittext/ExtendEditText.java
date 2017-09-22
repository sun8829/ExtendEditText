package com.ppdai.extendedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import java.util.regex.Pattern;

/**
 * Created by sunhuahui on 2017/9/22.
 */

public class ExtendEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    public static final String TAG = "ExtendEditText";
    private Drawable mClearDrawable;
    private Drawable mOkDrawable;
    private String mRegex;
    private boolean hasFocus;
    private OnClearListener mListener;
    private OnMatchListener mOnMatchListener;
    private OnTextChangedListener mOnTextChangedListener;

    public ExtendEditText(Context context) {
        this(context, null);
    }

    public ExtendEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ExtendEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExtendEditText);
        int mClearResId = a.getInteger(R.styleable.ExtendEditText_extend_icon_clear, R.mipmap.clean);
        if (mClearResId > 0) {
            mClearDrawable = getResources().getDrawable(mClearResId);
        }

        int mOkResId = a.getInteger(R.styleable.ExtendEditText_extend_icon_ok, R.mipmap.correct);
        if (mOkResId > 0) {
            mOkDrawable = getResources().getDrawable(mOkResId);
        }

        mRegex = a.getString(R.styleable.ExtendEditText_extend_regex);
        a.recycle();

        init();
    }

    private void init() {
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        mOkDrawable.setBounds(0, 0, mOkDrawable.getIntrinsicWidth(), mOkDrawable.getIntrinsicHeight());
        setRightIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    handleRightDrawableTouchEvent();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    protected void handleRightDrawableTouchEvent() {
        if (!isMatch(mRegex == null ? "" : mRegex, getText())) {
            this.setText(null);
            setRightIconVisible(false);
            if (mListener != null) {
                mListener.onClear();
            }
        }
    }

    public void setOnClearListener(OnClearListener listener) {
        mListener = listener;
    }

    public void setOnMatchListener(OnMatchListener listener) {
        mOnMatchListener = listener;
    }

    public void setOnTextChangedListener(OnTextChangedListener listener) {
        mOnTextChangedListener = listener;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        Log.i(TAG, "onFocusChange");
        if (hasFocus) {
            setRightIconVisible(length() > 0);
        } else {
            setRightIconVisible(false);
        }
    }

    public void setRightIconVisible(boolean visible) {
        Log.i(TAG, "setRightIconVisible");
        if (isMatch(mRegex == null ? "" : mRegex, getText().toString())) {
            if (mOnMatchListener != null) {
                mOnMatchListener.onMatch();
            }
            updateRightDrawable(visible ? mOkDrawable : null);
        } else {
            if (mOnMatchListener != null) {
                mOnMatchListener.onUnMatch();
            }
            updateRightDrawable(visible ? mClearDrawable : null);
        }
    }

    public void updateRightDrawable(@DrawableRes int drawableRes) {
        updateRightDrawable(getResources().getDrawable(drawableRes));
    }

    protected void updateRightDrawable(Drawable right) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (hasFocus) {
            setRightIconVisible(s.length() > 0);
        }

        if (mOnTextChangedListener != null) {
            mOnTextChangedListener.onChanged(getText());
        }
    }

    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public interface OnClearListener {
        void onClear();
    }

    public interface OnMatchListener {
        void onMatch();

        void onUnMatch();
    }

    public interface OnTextChangedListener {
        void onChanged(Editable editable);
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }
}