package com.example.audiorecorder;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Handler;

public class SendSoundThread extends Thread {
    private final String TAG = "[SendThread]";

    private String host = "localhost";
    private int port = 1111;
    private byte[] buffer;
    public static DatagramSocket socket;

    private int mAudioSource = MediaRecorder.AudioSource.MIC;
    private int mSampleRate = 44100;
    private int mChannelCount = AudioFormat.CHANNEL_IN_STEREO;
    private int mAudioFormat = AudioFormat.ENCODING_PCM_16BIT;
    private int mBufferSize = AudioTrack.getMinBufferSize(mSampleRate, mChannelCount, mAudioFormat);
    public AudioRecord mAudioRecord = null;
    public Thread mRecordThread = null;
    public boolean isRecording = false;

    public SendSoundThread() {
    }

    public void stopForever() {
        synchronized (this) {
            this.isRecording = false;
        }
    }
    @Override
    public void run() {
        isRecording = true;
        if(mAudioRecord == null) {
            mAudioRecord =  new AudioRecord(mAudioSource, mSampleRate, mChannelCount, mAudioFormat, mBufferSize);
            mAudioRecord.startRecording();
        }

        byte[] readData = new byte[mBufferSize];
        String mFilepath = Environment.getExternalStorageDirectory().getAbsolutePath() +"/dorun.mp3";
        File file = new File(mFilepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "filepath is " + mFilepath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mFilepath, true);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        while(isRecording) {
            int ret = mAudioRecord.read(readData, 0, mBufferSize);  //  AudioRecord의 read 함수를 통해 pcm data 를 읽어옴
            Log.d(TAG, "read bytes is " + ret);

            try {
                fos.write(readData, 0, mBufferSize);    //  읽어온 readData 를 파일에 write 함
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        mAudioRecord.stop();
        mAudioRecord.release();
        mAudioRecord = null;

        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
