package com.surabhi.remotemouse;

import android.content.DialogInterface;
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

public class RemoteClient extends AppCompatActivity implements View.OnTouchListener, View.OnKeyListener{
    Button leftClickButton, rightClickButton;
    EditText editText;
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

    private class SendMessageToServer extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {
            System.out.println("Sending message: " + params[0]);
            TCPClient.connect("192.168.1.100", 7890);
            TCPClient.sendMessage(params[0]);
            TCPClient.close();
            return null;
        }
    }
}
