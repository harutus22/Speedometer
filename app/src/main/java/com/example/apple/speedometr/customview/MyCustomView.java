package com.example.apple.speedometr.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.apple.speedometr.R;

public class MyCustomView extends View implements View.OnTouchListener {

    private Paint OvalPaint,linePaint, backgrounPaint, numberPaint, needleScrewPaint, pedal;
    private RectF oval, background;
    private final int MAX_SPEED = 100;
    private final int MIN_SPEED = 0;
    private int mMaxSpeed, mCurrentSpeed;
    private int mWidth, mHeight;
    private Path linePath = new Path();
    Canvas canvasRe = new Canvas();

    @Override
    public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Toast.makeText(getContext(), "isTouched", Toast.LENGTH_LONG).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            linePath.lineTo(mWidth - 200, mHeight - 50);
                            canvasRe.drawPath(linePath, linePaint);
                            canvasRe.rotate(30);
                            invalidate();
                        }
                    }).start();

                    break;
                case MotionEvent.ACTION_UP:
                    Toast.makeText(getContext(), "Revealed", Toast.LENGTH_LONG).show();
                    break;
            }
            return true;
    }

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

        pedal = new Paint(Paint.ANTI_ALIAS_FLAG);
        pedal.setColor(Color.GRAY);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawLines(canvas);
        drawNumbers(canvas);
        drawCursor(canvas);
        drawElectricCounter(canvas);
        drawPedal(canvas);
    }

    private void drawLines(Canvas canvas) {
        canvasRe = canvas;
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
        linePath.moveTo(mWidth, mHeight);
        linePath.lineTo(mWidth - 250 , mHeight);
        linePath.addCircle(mWidth, mHeight, 10.0f, Path.Direction.CW);
        linePath.close();
        canvas.restore();
        canvas.drawPath(linePath, linePaint);

        canvas.drawCircle(15.0f, 10.0f, 16.0f, needleScrewPaint);
    }

    private void drawElectricCounter(Canvas canvas) {
    }

    private void drawPedal(Canvas canvas) {
        RectF rectF = new RectF();
        rectF.set(20, mHeight + mHeight *3/4 , 150, getBottom() + 150);
        canvas.save();
        canvas.drawRect(rectF, pedal);
        canvas.restore();
        canvas.drawText("Gas", 30, mHeight + mHeight *3/4 + 100, numberPaint);
    }
}
