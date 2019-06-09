package net.mdrabek.zadanie6;

import android.media.AudioRecord;

import java.util.concurrent.LinkedBlockingQueue;

public class AudioGrabbingTask implements Runnable
{
    private int bufferSize;
    private AudioRecord audioRecord;
    private LinkedBlockingQueue<byte[]> bufferQueue;
    private boolean isStopRequested;
    private boolean isPauseRequested;

    public AudioGrabbingTask(int bufferSize, AudioRecord audioRecord, LinkedBlockingQueue<byte[]> bufferQueue)
    {
        this.bufferSize = bufferSize;
        this.audioRecord = audioRecord;
        this.bufferQueue = bufferQueue;
    }

    @Override
    public void run()
    {
        while (!isStopRequested)
        {
            if (!isPauseRequested)
            {
                try
                {
                    byte[] block = getNextBlock();
                    if (block != null)
                    {
                        bufferQueue.put(block);
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }

        audioRecord.release();
    }

    public void pause()
    {
        isPauseRequested = true;
    }

    public void resume()
    {
        isPauseRequested = false;
    }

    public void stop()
    {
        isStopRequested = true;
    }

    byte[] getNextBlock()
    {
        byte[] buffer = new byte[bufferSize];
        int returnCode = audioRecord.read(buffer, 0, buffer.length);
        if (returnCode >= 0)
        {
            return buffer;
        }
        else
        {
            return null;
        }
    }

    public GrabberState getState()
    {
        if(isStopRequested)
        {
            return GrabberState.STOPPED;
        }
        else if(isPauseRequested)
        {
            return GrabberState.PAUSED;
        }
        else
        {
            return GrabberState.RUNNING;
        }
    }

    public enum GrabberState
    {
        RUNNING,
        PAUSED,
        STOPPED
    }
}

