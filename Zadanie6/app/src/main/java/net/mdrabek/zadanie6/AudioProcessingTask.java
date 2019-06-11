package net.mdrabek.zadanie6;

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

        if (linkedBlockingQueue.size() > 0)
        {
            byte[] header = WavFilesHelper.prepareWavHeader(linkedBlockingQueue.size() * linkedBlockingQueue.peek().length);

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
    }

    public void stop()
    {
        isStopRequested = true;
    }
}
