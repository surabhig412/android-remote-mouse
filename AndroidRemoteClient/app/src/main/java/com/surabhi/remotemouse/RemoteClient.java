package com.surabhi.remotemouse;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class RemoteClient extends AppCompatActivity implements View.OnTouchListener{
    Button leftClickButton, rightClickButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_client);

        leftClickButton = (Button)findViewById(R.id.left_click_btn);
        rightClickButton = (Button)findViewById(R.id.right_click_btn);
        leftClickButton.setOnTouchListener(this);
        rightClickButton.setOnTouchListener(this);
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

    private void sendToRemoteServer(char message) {
        new SendMessageToServer().execute(message);
    }

    private class SendMessageToServer extends AsyncTask<Character, Void, Void>{
        @Override
        protected Void doInBackground(Character... params) {
            TCPClient.connect("192.168.1.101", 7890);
            TCPClient.sendMessage(params[0]);
            TCPClient.close();
            return null;
        }
    }
}
