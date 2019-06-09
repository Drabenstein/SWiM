package net.mdrabek.zadanie6;

import android.media.AudioRecord;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class AudioProcessingTask implements Runnable
{
    private LinkedBlockingQueue<byte[]> linkedBlockingQueue;
    private String fileName;
    private boolean isStopRequested;

    public AudioProcessingTask(String fileName, LinkedBlockingQueue<byte[]> linkedBlockingQueue)
    {
        this.linkedBlockingQueue = linkedBlockingQueue;
        this.fileName = fileName;
    }

    @Override
    public void run()
    {
        while (!isStopRequested)
        {
            try
            {
                Thread.sleep(300);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }

        byte[] header = prepareWavHeader(linkedBlockingQueue.size() * linkedBlockingQueue.peek().length);

        try
        {
            File f = new File(fileName);
            if (!f.exists())
            {
                f.createNewFile();
            }

            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileName));
            outputStream.write(header);
            while (!linkedBlockingQueue.isEmpty())
            {
                outputStream.write(linkedBlockingQueue.take());
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void stop()
    {
        isStopRequested = true;
    }

    private byte[] prepareWavHeader(int pcmDataLengthInBytes)
    {
        int totalDataLen = pcmDataLengthInBytes + 36;
        byte[] wawHeader = prepareWavFileHeader(16, pcmDataLengthInBytes, totalDataLen, 44100, 1, (44100 * 16 * 1) / 2);

        return wawHeader;
    }

    private byte[] prepareWavFileHeader(int bitsPerSample, long totalAudioLen, long totalDataLen, long longSampleRate, int channels, long byteRate)
    {
        byte[] header = new byte[44];
        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (1 * 16 / 8);
        header[33] = 0;
        header[34] = (byte) bitsPerSample;
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

        return header;
    }

}
