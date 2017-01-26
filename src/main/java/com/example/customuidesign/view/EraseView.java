package com.example.customuidesign.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.customuidesign.R;

/**
 * Created by zy on 2017/1/20.
 */

public class EraseView extends View {
    private final static String TAG = "EraseView";
    private Paint mPaint = new Paint();
    private Bitmap avator;

    public EraseView(Context context) {
        this(context, null);
    }

    public EraseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public EraseView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    private EraseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.EraseView, defStyleAttr, defStyleRes);
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String name = attrs.getAttributeName(i);
            String value = attrs.getAttributeValue(i);
            Log.e(TAG, "name is :" + name + "\n value is :" + value, null);
        }
        Log.e(TAG, "EraseView: " + array.getIndexCount() +
                (array.getIndex(0) == R.attr.erasebackgroundcolor ? "true" : "false"), null);
        Log.e(TAG, "attr number of erasebackgroundcolor is :" + R.attr.erasebackgroundcolor + "\n array number is :" + array.getIndex(0), null);
        for (int i = 0; i < array.getIndexCount(); i++) {
            // deal with the def attr
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.PictureView_pictureviewbackgroundcolor:
                    break;
                case R.styleable.PictureView_erasebackgroundpic: {
                    avator = BitmapFactory.decodeResource(getResources(),
                            array.getResourceId(i, 0));
                    break;
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
    }
}
