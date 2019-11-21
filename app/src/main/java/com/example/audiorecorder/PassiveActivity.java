package com.example.audiorecorder;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PassiveActivity extends Activity {
	RecordAudio recordTask;
	PlayAudio playTask;
	final int CUSTOM_FREQ_SOAP = 2;;
	Button startRecordingButton, stopRecordingButton, startPlaybackButton,
			stopPlaybackButton;
	TextView statusText;

	File recordingFile;

	boolean isRecording = false;
	boolean isPlaying = false;

	int frequency = 11025;
	int outfrequency = frequency*CUSTOM_FREQ_SOAP;
	int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);



		startRecordingButton = (Button) findViewById(R.id.StartRecordingButton);
		stopRecordingButton = (Button) findViewById(R.id.StopRecordingButton);
		startPlaybackButton = (Button) findViewById(R.id.StartPlaybackButton);
		stopPlaybackButton = (Button) findViewById(R.id.StopPlaybackButton);

		startRecordingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				record();
			}
		});
		stopRecordingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopRecording();

			}
		});
		startPlaybackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				play();

			}
		});
		stopPlaybackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopPlaying();
			}
		});

		stopRecordingButton.setEnabled(false);
		startPlaybackButton.setEnabled(false);
		stopPlaybackButton.setEnabled(false);

		File path = new File(
				Environment.getExternalStorageDirectory().getAbsolutePath()
						+ "/sdcard/meditest/");
		path.mkdirs();
		try {
			recordingFile = File.createTempFile("recording", ".pcm", path);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't create file on SD card", e);
		}
	}


	public void play() {
		startPlaybackButton.setEnabled(true);

		playTask = new PlayAudio();
		playTask.execute();

		stopPlaybackButton.setEnabled(true);
	}

	public void stopPlaying() {
		isPlaying = false;
		stopPlaybackButton.setEnabled(false);
		startPlaybackButton.setEnabled(true);
	}

	public void record() {
		startRecordingButton.setEnabled(false);
		stopRecordingButton.setEnabled(true);

		// For Fun
		startPlaybackButton.setEnabled(true);

		recordTask = new RecordAudio();
		recordTask.execute();
	}

	public void stopRecording() {
		isRecording = false;
	}

	private class PlayAudio extends AsyncTask<Void, Integer, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			isPlaying = true;

			int bufferSize = AudioTrack.getMinBufferSize(outfrequency,
					channelConfiguration, audioEncoding);
			short[] audiodata = new short[bufferSize / 4];

			try {
				DataInputStream dis = new DataInputStream(
						new BufferedInputStream(new FileInputStream(
								recordingFile)));

				AudioTrack audioTrack = new AudioTrack(
						AudioManager.STREAM_MUSIC, outfrequency,
						channelConfiguration, audioEncoding, bufferSize,
						AudioTrack.MODE_STREAM);

				audioTrack.play();

				while (isPlaying && dis.available() > 0) {
					int i = 0;
					while (dis.available() > 0 && i < audiodata.length) {
						audiodata[i] = dis.readShort();
						i++;
					}
					audioTrack.write(audiodata, 0, audiodata.length);
				}

				dis.close();

				startPlaybackButton.setEnabled(false);
				stopPlaybackButton.setEnabled(true);

			} catch (Throwable t) {
				Log.e("AudioTrack", "Playback Failed");
			}

			return null;
		}
	}

	private class RecordAudio extends AsyncTask<Void, Integer, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			isRecording = true;

			try {
				DataOutputStream dos = new DataOutputStream(
						new BufferedOutputStream(new FileOutputStream(
								recordingFile)));

				int bufferSize = AudioRecord.getMinBufferSize(frequency,
						channelConfiguration, audioEncoding);

				AudioRecord audioRecord = new AudioRecord(
						MediaRecorder.AudioSource.MIC, frequency,
						channelConfiguration, audioEncoding, bufferSize);

				short[] buffer = new short[bufferSize];
				audioRecord.startRecording();


				while (isRecording) {
					int bufferReadResult = audioRecord.read(buffer, 0,
							bufferSize);
					for (int i = 0; i < bufferReadResult; i++) {
						dos.writeShort(buffer[i]);
					}
				}

				audioRecord.stop();
				dos.close();
			} catch (Throwable t) {
				Log.e("AudioRecord", "Recording Failed");
			}

			return null;
		}



		protected void onPostExecute(Void result) {
			startRecordingButton.setEnabled(true);
			stopRecordingButton.setEnabled(false);
			startPlaybackButton.setEnabled(true);
		}
	}

}
