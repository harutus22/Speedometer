package com.example.apple.speedometr.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CursorView extends View {

    private Paint needleScrewPaint, linePaint;
    private android.os.Handler handler;
    private float mWidth, mHeigh;
    private Path linePath;
    private boolean isAccelerate = false;

    public CursorView(Context context) {
        super(context);
        init();
    }

    public CursorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CursorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        linePath = new Path();

        needleScrewPaint = new Paint();
        needleScrewPaint.setColor(Color.BLACK);
        needleScrewPaint.setAntiAlias(true);
        needleScrewPaint.setShader(new RadialGradient(15.0f, 15.0f, 10.0f,
                Color.DKGRAY, Color.BLACK, Shader.TileMode.CLAMP));

        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(5.0f);
        linePaint.setShadowLayer(8.0f, 0.1f, 0.1f, Color.GRAY);

        handler = new android.os.Handler();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCursor(canvas);
    }

    private void drawCursor(Canvas canvas) {
        mWidth = canvas.getWidth() / 2;
        mHeigh = canvas.getHeight() / 2;
        linePath.moveTo(mWidth, mHeigh);
        linePath.lineTo(mWidth - 250 , mHeigh);
        linePath.addCircle(mWidth, mHeigh, 10.0f, Path.Direction.CW);
        linePath.close();
        canvas.drawPath(linePath, linePaint);
        canvas.drawCircle(15.0f, 10.0f, 16.0f, needleScrewPaint);
    }

    public void setAccelerate(boolean accelerate) {
        isAccelerate = accelerate;
    }

    public void speedChange(){

    }
}
