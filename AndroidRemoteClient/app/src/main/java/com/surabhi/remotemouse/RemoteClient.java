package com.surabhi.remotemouse;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RemoteClient extends AppCompatActivity implements View.OnTouchListener, View.OnKeyListener, SensorEventListener{
    Button leftClickButton, rightClickButton;
    EditText editText;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_client);

        leftClickButton = (Button)findViewById(R.id.left_click_btn);
        rightClickButton = (Button)findViewById(R.id.right_click_btn);
        leftClickButton.setOnTouchListener(this);
        rightClickButton.setOnTouchListener(this);
        editText = (EditText)findViewById(R.id.editText);
        editText.setOnKeyListener(this);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                sendToRemoteServer("" + Constants.KEYBOARD + s.toString());
            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v == leftClickButton) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    sendToRemoteServer(Constants.LEFTMOUSEDOWN);
                    break;
                case MotionEvent.ACTION_UP:
                    sendToRemoteServer(Constants.LEFTMOUSEUP);
                    break;
            }
        } else if(v == rightClickButton) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    sendToRemoteServer(Constants.RIGHTMOUSEDOWN);
                    break;
                case MotionEvent.ACTION_UP:
                    sendToRemoteServer(Constants.RIGHTMOUSEUP);
                    break;
            }
        }
        return false;
    }

    private void sendToRemoteServer(String message) {
        new SendMessageToServer().execute(message);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        sendToRemoteServer("" + Constants.KEYBOARD + keyCode);
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] mGravity;
        float mAccel = 0.00f;
        float mAccelCurrent = SensorManager.GRAVITY_EARTH;
        float mAccelLast = SensorManager.GRAVITY_EARTH;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float)Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
            if(mAccel > 0.5){
                System.out.println("Sensor has been changed:mAccel: " + mAccel);
                sendToRemoteServer(Constants.MOUSEMOVED + x + ";" + y);
                // do something
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        System.out.println("on accuracy changed");
    }

    private class SendMessageToServer extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {
            System.out.println("Sending message: " + params[0]);
            TCPClient.connect("192.168.1.101", 7890);
            TCPClient.sendMessage(params[0]);
            TCPClient.close();
            return null;
        }
    }
}
