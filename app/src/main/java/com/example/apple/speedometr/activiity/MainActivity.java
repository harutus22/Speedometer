package com.example.apple.speedometr.activiity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
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
import com.example.apple.speedometr.customview.MyCustomView;

public class MainActivity extends AppCompatActivity {

    private MyCustomView myCustomView;
    private Button accelerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCustomView = this.findViewById(R.id.speedometer);
//        accelerate = findViewById(R.id.gasBtn);
//        myCustomView.setButton(accelerate);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            myCustomView.onTouch(accelerate, event);
        }
        catch (java.lang.NullPointerException e){
            Toast.makeText(getApplicationContext(), "Push 'Gas' Button", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
