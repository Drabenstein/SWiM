package net.mdrabek.punsgame.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.Toast;

public class CloseProximityDetector implements SensorEventListener
{
    private static final long COVER_GESTURE_THRESHOLD = 400;

    private CloseProximityListener listener;
    private boolean isClose;
    private long lastUpdate;

    public CloseProximityDetector(CloseProximityListener listener)
    {
        this.listener = listener;
    }

    public void setListener(CloseProximityListener listener)
    {
        this.listener = listener;
    }

    private void onProximityClose()
    {
        if (listener != null)
        {
            listener.onProximityClose();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        Toast.makeText((Context) listener, Float.toString(event.values[0]), Toast.LENGTH_SHORT).show();

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastUpdate > COVER_GESTURE_THRESHOLD)
        {
            if (event.values[0] == event.sensor.getMaximumRange())
            {
                if (!isClose)
                {
                    isClose = true;
                }
                lastUpdate = currentTime;

            }
            else
            {
                if (isClose)
                {
                    isClose = false;
                    onProximityClose();
                    lastUpdate = currentTime;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    public interface CloseProximityListener
    {
        void onProximityClose();
    }
}
