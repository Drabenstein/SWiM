package net.mdrabek.punsgame.Sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.view.WindowManager;

public class RotationDetector implements SensorEventListener
{
    private static final int FROM_RADS_TO_DEGS = -57;
    private RotationChangedListener listener;

    public RotationDetector(@NonNull RotationChangedListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
        {
            if (event.values.length > 4)
            {
                float[] truncatedRotationVector = new float[4];
                System.arraycopy(event.values, 0, truncatedRotationVector, 0, 4);
                updateRotation(truncatedRotationVector);
            }
            else
            {
                updateRotation(event.values);
            }
        }
    }

    private void updateRotation(float[] vectors)
    {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, vectors);
        int worldAxisX = SensorManager.AXIS_X;
        int worldAxisZ = SensorManager.AXIS_Z;
        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisX, worldAxisZ, adjustedRotationMatrix);

        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);

        float pitch = orientation[1] * FROM_RADS_TO_DEGS;
        float roll = Math.abs(orientation[2] * FROM_RADS_TO_DEGS);
        if (pitch < -70 && pitch > -135)
        {
            // if device is laid flat on a surface, we don't want to change the orientation
            onOrientationChanged(RotationState.UP_TO_THE_SKY);
        }
        else if (roll > 45 && roll < 135)
        {
            // The device is closer to landscape orientation. Enable fullscreen
            onOrientationChanged(RotationState.PERPENDICULAR);
        }
        else
        {
            // The device is closer to portrait orientation. Disable fullscreen
            onOrientationChanged(RotationState.DOWN_TO_THE_GROUND);
        }
    }

    private void onOrientationChanged(RotationState state)
    {
        if (listener != null)
        {
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
