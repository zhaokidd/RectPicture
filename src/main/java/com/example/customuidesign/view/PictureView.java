package com.example.customuidesign.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.example.customuidesign.R;

/**
 * Created by zy on 2017/1/21.
 */

public class PictureView extends View {
    /*round image type*/
    private final static int TYPE_CIRCLE = 0;
    /*round rect type*/
    private final static int TYPE_ROUND = 1;

    /*
    * default round radius
    * */
    private final static int DEFAULT_ROUND_RADIUS = 10;

    /**
     * default round rect radius
     */
    private static final int DEFAULT_ROUND_RECT_RADIUS = 5;

    /*
    * round image bitmap
    * */
    private Bitmap avator;

    /*
    * avator image type
    * */
    private int avatorType = -1;

    /*
    * image radius
    * */
    private int roundRadius = 0;

    /*
    * round Rect radius
    * */
    private float roundRectRadius = 0f;
    /*
    * image view radius
    * */
    private int imageRadius;

    /*
    * bitmap shader
    * */
    private BitmapShader bitmapShader;

    /*
    * to scale the avator
    * */
    private Matrix matrix;

    private Paint mPaint;

    public PictureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray defaultArray = getContext().
                getTheme().
                obtainStyledAttributes(attrs, R.styleable.PictureView, defStyleAttr, 0);

        for (int i = 0; i < defaultArray.getIndexCount(); i++) {
            int attr = defaultArray.getIndex(i);
            switch (attr) {
                case R.styleable.PictureView_erasebackgroundpic:
                    int resId = defaultArray.getResourceId(i, 0);
                    avator = BitmapFactory.decodeResource(getResources(), resId);
                    break;
                case R.styleable.PictureView_roundtype:
                    avatorType = defaultArray.getInt(attr, -1);
                    break;
                case R.styleable.PictureView_roundImageRadius:
                    roundRadius = defaultArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP,
                                    DEFAULT_ROUND_RADIUS,
                                    getResources().getDisplayMetrics()));
                    break;
                case R.styleable.PictureView_roundRectRadius:
                    roundRectRadius = defaultArray.
                            getDimensionPixelSize(attr,
                                    (int) TypedValue.applyDimension(
                                            TypedValue.COMPLEX_UNIT_DIP,
                                            DEFAULT_ROUND_RECT_RADIUS,
                                            getResources().getDisplayMetrics()));
                    break;
            }
        }

        init();
    }

    public PictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PictureView(Context context) {
        this(context, null);
    }

    public PictureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void init() {
        matrix = new Matrix();
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (avatorType == TYPE_CIRCLE) {
            int mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
            setMeasuredDimension(mWidth, mWidth);
            roundRadius = mWidth / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //init the shader
        setUpShader();

        if (avatorType == TYPE_CIRCLE) {
            canvas.drawCircle(roundRadius, roundRadius, roundRadius, mPaint);
        } else if (avatorType == TYPE_ROUND) {
            RectF roundRect = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRoundRect(roundRect, roundRadius, roundRadius, mPaint);
        } else {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), mPaint);
        }
    }

    /*
     * init the bitmapshader
     * */
    private void setUpShader() {
        //传入avator，将avator作为要渲染的对象，设置高和宽度的渲染模式.
        bitmapShader = new BitmapShader(avator, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //避免图片被拉伸，在绘制前需要将图片放大到高和宽都超过view空间的高和宽
        float scale = 1.0f;
        float scaleX = 1.0f;
        float scaleY = 1.0f;
        if (avatorType == TYPE_CIRCLE) {
            int bitmapRadius = Math.min(avator.getWidth(), avator.getHeight());
            scale = roundRadius * scale / bitmapRadius;
        } else if (avatorType == TYPE_ROUND) {
            scale = Math.max(getWidth() * scale / avator.getWidth(),
                    getHeight() * scale / avator.getHeight());
        } else {
            scaleX = getMeasuredWidth() * scaleX / avator.getWidth();
            scaleY = getMeasuredHeight() * scaleY / avator.getHeight();
        }

        matrix.setScale(scaleX, scaleY);
        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);

    }
}
