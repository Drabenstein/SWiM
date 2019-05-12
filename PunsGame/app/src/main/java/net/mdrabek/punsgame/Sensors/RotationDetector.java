package net.mdrabek.punsgame.Sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.annotation.NonNull;
import android.view.WindowManager;

public class RotationDetector implements SensorEventListener
{
    private WindowManager windowManager;
    private RotationChangedListener listener;

    public RotationDetector(@NonNull WindowManager windowManager,@NonNull RotationChangedListener listener)
    {
        this.windowManager = windowManager;
        this.listener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
        {
            handleRotation(event.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    private void handleRotation(float[] rotationVector)
    {
//        PERPENDICULAR
//                [0] 0.5-0.6
//            [1] < 0.1
//            [2] > 0.75
//
//        UP_TO_THE_SKY
//                [0] < 0.1
//                [1] < 0.1
//                [2] > 0.95 (0.98)
//
//        TO THE GROUND
//            [0] < -0.95 (-0.99)
//            [1] < 0.1
//            [2] < 0.1

        if(rotationVector[0] > 0.5f && rotationVector[0] < 0.6f && rotationVector[1] < 0.1f && rotationVector[2] > 0.75f)
        {
            onOrientationChanged(RotationState.PERPENDICULAR);
        }
        else if(rotationVector[0] < 0.1f && rotationVector[1] < 0.1f && rotationVector[2] > 0.95f)
        {
            onOrientationChanged(RotationState.UP_TO_THE_SKY);
        }
        else if(rotationVector[0] < -0.95f && rotationVector[1] < 0.1f && rotationVector[2] < 0.1)
        {
            onOrientationChanged(RotationState.DOWN_TO_THE_GROUND);
        }
    }

    private void onOrientationChanged(RotationState state)
    {
        if(listener != null)
        {
            //listener.onOrientationChanged(pitch, roll);
            listener.onOrientationChanged(state);
        }
    }

    public interface RotationChangedListener
    {
        void onOrientationChanged(RotationState state);
    }

    public enum RotationState
    {
        DOWN_TO_THE_GROUND,
        UP_TO_THE_SKY,
        PERPENDICULAR
    }
}
