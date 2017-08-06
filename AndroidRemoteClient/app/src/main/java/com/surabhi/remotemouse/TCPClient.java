package com.surabhi.remotemouse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {
    public static Socket clientSocket;
    public static void connect(String host, int port) {
        try {
            clientSocket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(char message) {
        if(clientSocket != null) {
            DataOutputStream outToServer = null;
            try {
                outToServer = new DataOutputStream(clientSocket.getOutputStream());
                outToServer.writeChar(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
