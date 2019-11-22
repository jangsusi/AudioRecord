package com.example.audiorecorder;

import java.util.logging.Handler;

public class SendSoundThread extends Thread {
    private Handler handler;
    private boolean isRun = true;

    public SendSoundThread(Handler handler) {
        this.handler = handler;
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run() {

    }
}
