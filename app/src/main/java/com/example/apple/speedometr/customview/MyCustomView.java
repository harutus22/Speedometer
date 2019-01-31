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
import android.widget.Button;
import android.widget.Toast;

import com.example.apple.speedometr.R;

import java.util.logging.Handler;

public class MyCustomView extends View{

    private Paint OvalPaint, backgrounPaint, numberPaint;
    private RectF oval, background;
    private int mWidth, mHeigh;

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawLines(canvas);
        drawNumbers(canvas);
    }

    private void drawLines(Canvas canvas) {
        mHeigh = canvas.getHeight() / 2;
        mWidth = canvas.getWidth() / 2;
        oval.set(mWidth - 500, mHeigh - 500, mWidth + 500, mHeigh + 500);
        canvas.drawRect(background, backgrounPaint);
        canvas.drawArc(oval, 180, 272,false, OvalPaint);
        int arcCenterX = mWidth - 10;
        int arcCenterY = mHeigh - 10;
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
        int arcCenterY = mHeigh - 10;
        int startX = arcCenterX - 350;
        int startY = arcCenterY;
        final int totalNoOfPointers = 10;

        for (int i = 0; i <= totalNoOfPointers; i++) {

            numberPaint.setTextSize(50f);

            canvas.drawText(String.valueOf(i * 10), startX + 300, startY - 300, numberPaint);

            canvas.rotate(270f / totalNoOfPointers, arcCenterX, arcCenterY);
            }
    }
}
