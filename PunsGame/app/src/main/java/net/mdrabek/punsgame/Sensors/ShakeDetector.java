package net.mdrabek.punsgame.Sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector implements SensorEventListener
{
    public interface ShakeListener
    {
        void onShake(int shakeCount, float acceleration);
    }

    private static final long SHAKE_THRESHOLD = 200;
    private static final float SHAKE_GRAVITY_THRESHOLD = 10.0f;
    private static final long SHAKE_COUNT_RESET_THRESHOLD = 3000;

    private long lastUpdate;
    private int count;
    private ShakeListener listener;

    public ShakeDetector(ShakeListener listener)
    {
        this.listener = listener;
    }

    public void setListener(ShakeListener listener)
    {
        this.listener = listener;
    }

    private void onShake(int count, float acceleration)
    {
        if(listener != null)
        {
            listener.onShake(count, acceleration);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
        {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float g = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

            long currentTime = System.currentTimeMillis();

            if (currentTime - lastUpdate >= SHAKE_THRESHOLD)
            {
                if (g >= SHAKE_GRAVITY_THRESHOLD)
                {
                    if(currentTime - lastUpdate > SHAKE_COUNT_RESET_THRESHOLD)
                    {
                        count = 0;
                    }

                    lastUpdate = currentTime;
                    count++;
                    onShake(count, g);
                }
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
