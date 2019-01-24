package com.example.apple.speedometr.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.apple.speedometr.R;

public class MyCustomView extends View {

    private Paint OvalPaint,linePaint, backgrounPaint, numberPaint;
    private RectF oval, background;
    private int MAX_SPEED = 100;
    private int mMaxSpeed, mCurrentSpeed;
    private int mainXLineStart = 330, mainXLineEnd = 280, mainYLineStart = 1130, mainYLineEnd = 1200;
    private int mWidth, mHeight;
    private Paint needleLinePaint;
    private Paint needleScrewPaint;
    private Path linePath;


    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.MyCustomView,
                0, 0);
        mMaxSpeed = a.getInt(R.styleable.MyCustomView_maxSpeed, MAX_SPEED);
        mCurrentSpeed = a.getInt(R.styleable.MyCustomView_currentSpeed, 0);
        a.recycle();
        initDrawing();
    }

    private void initDrawing() {
        oval = new RectF();
        OvalPaint = new Paint();
        OvalPaint.setStyle(Paint.Style.STROKE);
        OvalPaint.setColor(Color.RED);
        OvalPaint.setStrokeWidth(15f);
        OvalPaint.setAntiAlias(true);
        numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numberPaint.setTextSize(15f);
        numberPaint.setStyle(Paint.Style.FILL);
        numberPaint.setColor(Color.WHITE);
        numberPaint.setStrokeWidth(1);
        background = new RectF();
        backgrounPaint = new Paint();
        background.left = getLeft();
        background.top = getTop();
        background.right = 1200;
        background.bottom = 1800;
        backgrounPaint.setColor(Color.BLACK);
        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(5.0f);
        linePaint.setShadowLayer(8.0f, 0.1f, 0.1f, Color.GRAY);

        needleScrewPaint = new Paint();
        needleScrewPaint.setColor(Color.BLACK);
        needleScrewPaint.setAntiAlias(true);
        needleScrewPaint.setShader(new RadialGradient(15.0f, 15.0f, 10.0f,
                Color.DKGRAY, Color.BLACK, Shader.TileMode.CLAMP));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLines(canvas);
        drawNumbers(canvas);
        drawCursor(canvas);
        drawElectricCounter(canvas);

    }

    private void drawLines(Canvas canvas) {

        mHeight = canvas.getHeight() / 2;
        mWidth = canvas.getWidth() / 2;
        oval.set(mWidth - 500, mHeight - 500, mWidth + 500, mHeight + 500);
        canvas.drawRect(background, backgrounPaint);
        canvas.drawArc(oval, 180, 272,false, OvalPaint);
        int arcCenterX = mWidth - 10;
        int arcCenterY = mHeight - 10;
        int startX = arcCenterX - 400;
        int startY = arcCenterY;
        final int totalNoOfPointers = 50;
        final int pointerMaxHeight = 55;
        final int pointerMinHeight = 45;
        int pointerHeight;
        for (int i = 0; i <= totalNoOfPointers; i++) {
            if(i%5 == 0){
                pointerHeight = pointerMaxHeight;
            }else{
                pointerHeight = pointerMinHeight;
            }
            canvas.drawLine(startX, startY, startX - pointerHeight, startY, OvalPaint);
            canvas.rotate(270f/totalNoOfPointers, arcCenterX, arcCenterY);
        }

    }

    private void drawNumbers(Canvas canvas) {
        int arcCenterX = mWidth - 10;
        int arcCenterY = mHeight - 10;
        int startX = arcCenterX - 350;
        int startY = arcCenterY;
        final int totalNoOfPointers = 10;

        for (int i = 0; i <= totalNoOfPointers; i++) {

            numberPaint.setTextSize(50f);

            canvas.drawText(String.valueOf(i * 10), startX + 300, startY - 300, numberPaint);


//                canvas.drawText(k, startX, startY, startX - pointerHeight, startY, numberPaint);
                canvas.rotate(270f / totalNoOfPointers, arcCenterX, arcCenterY);
            }
    }

    private void drawCursor(Canvas canvas) {
        linePath = new Path();
        linePath.moveTo(mWidth, mHeight);
        linePath.lineTo(mWidth + 200 , mHeight - 150);
        linePath.addCircle(mWidth, mHeight, 10.0f, Path.Direction.CW);
        linePath.close();
        canvas.drawPath(linePath, linePaint);

        canvas.drawCircle(15.0f, 10.0f, 16.0f, needleScrewPaint);
    }

    private void drawElectricCounter(Canvas canvas) {
    }
}
