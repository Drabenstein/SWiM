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
    //private WindowManager windowManager;
    private RotationChangedListener listener;

    public RotationDetector(@NonNull RotationChangedListener listener)
    {
        this.listener = listener;
    }

//    @Override
//    public void onSensorChanged(SensorEvent event)
//    {
//        if (event.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
//        {
//            handleRotation(event.values);
//        }
//    }

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

//    private void handleRotation(float[] rotationVector)
//    {
////        PERPENDICULAR
////            [0] 0.5-0.6
////            [1] < 0.1
////            [2] > 0.75
////
////        UP_TO_THE_SKY
////            [0] < 0.1
////            [1] < 0.1
////            [2] > 0.95 (0.98)
////
////        DOWN_TO_THE_GROUND
////            [0] < -0.95 (-0.99)
////            [1] < 0.1
////            [2] < 0.1
//
//        //        PERPENDICULAR
////            [0] < 0.15
////            [1] < -0.55 > -0.8
////            [2] < 0.15
////
////        UP_TO_THE_SKY
////            [0] < 0.1
////            [1] < 0.1
////            [2] -0.30 - 0.05
////
////        DOWN_TO_THE_GROUND
////            [0] 0.2 - 0.4
////            [1] < -0.8
////            [2] < 0.1
//
//        if (rotationVector[0] < 0.15f && rotationVector[1] < -0.55f && rotationVector[1] > -0.8f && rotationVector[2] < 0.15f)
//        {
//            onOrientationChanged(RotationState.PERPENDICULAR);
//        }
//        else if (rotationVector[0] < 0.15f && rotationVector[1] < 0.1f && rotationVector[2] > -0.3f && rotationVector[2] < 0.05f)
//        {
//            onOrientationChanged(RotationState.UP_TO_THE_SKY);
//        }
//        else if (rotationVector[0] > 0.2f && rotationVector[0] < 0.4f && rotationVector[1] < -0.8f && rotationVector[2] < 0.1)
//        {
//            onOrientationChanged(RotationState.DOWN_TO_THE_GROUND);
//        }
//
//
////        if (rotationVector[0] > 0.3f && rotationVector[0] < 0.75f && rotationVector[1] < 0.1f && rotationVector[2] > 0.7f)
////        {
////            onOrientationChanged(RotationState.PERPENDICULAR);
////        }
////        else if (rotationVector[0] < 0.1f && rotationVector[1] < 0.1f && rotationVector[2] > 0.90f)
////        {
////            onOrientationChanged(RotationState.UP_TO_THE_SKY);
////        }
////        else if (rotationVector[0] < -0.90f && rotationVector[1] < 0.1f && rotationVector[2] < 0.1)
////        {
////            onOrientationChanged(RotationState.DOWN_TO_THE_GROUND);
////        }
//    }

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
