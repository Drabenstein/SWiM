package net.mdrabek.zadanie6;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

public class MainActivity extends Activity
{
    private static final int REQUEST_PERMISSION_CODE = 1000;
    private final int MILLIS_IN_HOUR = 3600000;
    private final int MILLIS_IN_MINUTE = 60000;
    private final int MILLIS_IN_SECOND = 1000;

    private final int SAMPLE_RATE = 44100;
    private final int BUFFER_SIZE = 8192;

    private AudioRecord audioRecord;
    private AudioGrabbingTask audioGrabbingTask;
    private AudioProcessingTask audioProcessingTask;
    private CountDownTimer timer;
    private LinkedBlockingQueue<byte[]> buffer;
    private long secondsElapsed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!checkPermissionFromDevice())
        {
            requestPermission();
        }

        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
    }

    @Override
    protected void onStop()
    {
        if(audioRecord != null)
        {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
        super.onStop();
    }

    public void onStartRecordingButtonClicked(View view)
    {
        view.setEnabled(false);
        if (audioRecord == null)
        {
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        }
        if (audioGrabbingTask == null || audioGrabbingTask.getState() != AudioGrabbingTask.GrabberState.PAUSED)
        {
            buffer = new LinkedBlockingQueue<>();
            audioGrabbingTask = new AudioGrabbingTask(BUFFER_SIZE, audioRecord, buffer);
            String fileName = getAbsolutePathForNewFile("audio-" + System.currentTimeMillis() + ".wav");
            audioProcessingTask = new AudioProcessingTask(fileName, buffer);
            audioRecord.startRecording();
            new Thread(audioGrabbingTask).start();
            new Thread(audioProcessingTask).start();
            final TextView timerTextView = findViewById(R.id.countingTextView);

            if(timer != null)
            {
                timer.cancel();
            }

            timer = new CountDownTimer(Long.MAX_VALUE, 1000)
            {
                @Override
                public void onTick(long millisUntilFinished)
                {
                    secondsElapsed++;
                    long passedMillis = secondsElapsed * MILLIS_IN_SECOND;
                    long hours = passedMillis / MILLIS_IN_HOUR;
                    long minutes = (passedMillis % MILLIS_IN_HOUR) / MILLIS_IN_MINUTE;
                    long seconds = (passedMillis % MILLIS_IN_MINUTE) / MILLIS_IN_SECOND;
                    timerTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                }

                @Override
                public void onFinish()
                {
                    start();
                }
            };
            timer.start();
        }
        else
        {
            audioGrabbingTask.resume();
            audioRecord.startRecording();
            timer.start();
        }
        findViewById(R.id.pauseRecordingButton).setEnabled(true);
        findViewById(R.id.saveRecToFileButton).setEnabled(true);
    }

    public void onPauseRecordingButtonClicked(View view)
    {
        view.setEnabled(false);
        audioRecord.stop();
        audioGrabbingTask.pause();
        timer.cancel();
        findViewById(R.id.startRecordingButton).setEnabled(true);
    }

    public void onSaveRecordingRecordingButtonClicked(View view)
    {
        view.setEnabled(false);
        findViewById(R.id.pauseRecordingButton).setEnabled(false);
        audioRecord.stop();
        timer.cancel();
        secondsElapsed = 0;
        timer = null;
        if (audioGrabbingTask != null)
        {
            audioGrabbingTask.stop();
        }
        if (audioProcessingTask != null)
        {
            audioProcessingTask.stop();
        }
        audioRecord = null;
        findViewById(R.id.startRecordingButton).setEnabled(true);
        TextView countingTextView = findViewById(R.id.countingTextView);
        countingTextView.setText(R.string.timerPlaceholder);
    }

    public void onListRecordingsButtonClicked(View view)
    {
        final Intent recordListingIntent = new Intent(this, RecordListingActivity.class);
        startActivity(recordListingIntent);
    }

    private String getAbsolutePathForNewFile(String filename)
    {
        File targetDir = new File(Environment.getExternalStorageDirectory(), "AudioGrabber");

        if (!targetDir.exists())
        {
            targetDir.mkdir();
        }

        File targetFile = new File(targetDir, filename);
        return targetFile.getAbsolutePath();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, REQUEST_PERMISSION_CODE);
    }

    private boolean checkPermissionFromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED && record_audio_result == PackageManager.PERMISSION_GRANTED;
    }
}
