package net.mdrabek.punsgame.Sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Surface;
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
        //if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
        //{
            handleRotation(event.values);
        //}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    private void handleRotation(float[] rotationVector)
    {
//        if (Float.compare(x, rotationVector[0]) != 0 || Float.compare(y, rotationVector[1]) != 0
//                || Float.compare(z, rotationVector[2]) != 0 )
////                || Float.compare(w, rotationVector[3]) != 0)
//        {
//            x = rotationVector[0];
//            y = rotationVector[1];
//            z = rotationVector[2];
//            //w = rotationVector[3];
//            Log.i("RotationDetector", "[0] = " + x + ", [1] = " + y
//                    + ", [2] = " + z + ", [3] = " + w);
//        }
//
//        float[] rotationMatrix = new float[9];
//        SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector);
//
//        final int worldAxisForDeviceAxisX;
//        final int worldAxisForDeviceAxisY;
//
//        // Remap the axes as if the device screen was the instrument panel,
//        // and adjust the rotation matrix for the device orientation.
//        switch (windowManager.getDefaultDisplay().getRotation()) {
//            case Surface.ROTATION_0:
//            default:
//                worldAxisForDeviceAxisX = SensorManager.AXIS_X;
//                worldAxisForDeviceAxisY = SensorManager.AXIS_Z;
//                break;
//            case Surface.ROTATION_90:
//                worldAxisForDeviceAxisX = SensorManager.AXIS_Z;
//                worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_X;
//                break;
//            case Surface.ROTATION_180:
//                worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_X;
//                worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_Z;
//                break;
//            case Surface.ROTATION_270:
//                worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_Z;
//                worldAxisForDeviceAxisY = SensorManager.AXIS_X;
//                break;
//        }
//
//        float[] adjustedRotationMatrix = new float[9];
//        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisForDeviceAxisX,
//                worldAxisForDeviceAxisY, adjustedRotationMatrix);
//
//        // Transform rotation matrix into azimuth/pitch/roll
//        float[] orientation = new float[3];
//        SensorManager.getOrientation(adjustedRotationMatrix, orientation);
//
//        // Convert radians to degrees
//        float pitch = orientation[1] * -57;
//        float roll = orientation[2] * -57;
//
//        onOrientationChanged(pitch, roll);

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

    private void onOrientationChanged(float pitch, float roll)
    {
        if(listener != null)
        {
            //listener.onOrientationChanged(pitch, roll);
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
