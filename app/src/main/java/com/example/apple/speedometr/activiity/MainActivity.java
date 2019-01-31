package com.example.apple.speedometr.activiity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.IpSecManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apple.speedometr.R;
import com.example.apple.speedometr.customview.CursorView;
import com.example.apple.speedometr.customview.MyCustomView;

public class MainActivity extends AppCompatActivity {

    private CursorView cursorView;
    private Button accelerate;
    private float rotate = 0;
    private float MAX_ANGLE = 270, MIN_ANGLE = 0;
    private Handler handler;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cursorView = this.findViewById(R.id.cursor);
        handler = new Handler();

        accelerate = findViewById(R.id.gasBtn);
        accelerate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(event.getAction() == MotionEvent.ACTION_UP){
                            speedDown();
                        }
                        else {
                            speedUp();
                        }
                        handler.postDelayed(this, 100);
                    }
                }, 2000);
                return false;
            }
        });
    }

    private void speedUp(){
        if(rotate >= MAX_ANGLE){
            rotate = MAX_ANGLE;
        } else {
            rotate += 5;
            cursorView.setRotation(rotate);
        }
    }

    private void speedDown(){
        if(rotate <= MIN_ANGLE){
            rotate = MIN_ANGLE;
        } else {
            rotate -= 5;
            cursorView.setRotation(rotate);
        }
    }

}
