package com.example.audiorecorder;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Handler;

public class SendSoundThread extends Thread {
    private final String TAG = "[SendThread]";

    private boolean isRun = true;
    private String host = "localhost";
    private int port = 1111;
    private byte[] buffer;
    public static DatagramSocket socket;

    private AudioRecord recorder;
    private int sampleRate = 48000;/**8000, 16000, 22050, 24000, 32000, 44100, 48000 Choose the best for the device and the bandwidth **/
    private int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    private int minBufSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
    private boolean status = true;

    public SendSoundThread() {
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }

    @Override
    public void run() {
        this.isRun = true;

//        try {
//            DatagramSocket socket = new DatagramSocket();
//            byte[] buffer = new byte[minBufSize];
//            DatagramPacket packet;
//            final InetAddress destination = InetAddress.getByName(host);
            recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig,audioFormat, minBufSize*10);
            try {
                recorder.startRecording();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

            while(status) {
                //reading data from MIC into buffer
                minBufSize = recorder.read(buffer, 0, buffer.length);

                //putting buffer in the packet
//                packet = new DatagramPacket(buffer, buffer.length, destination,port);

//                socket.send(packet);
                Log.d(TAG, minBufSize+"");
            }

//        } catch(UnknownHostException e) {
//            Log.e("Ex", "UnknownHostException");
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("EX", "IOException");
//        }
    }
}
