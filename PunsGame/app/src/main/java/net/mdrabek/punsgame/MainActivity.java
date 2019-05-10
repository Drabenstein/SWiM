package net.mdrabek.punsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.mdrabek.punsgame.Sensors.ShakeDetector;

public class MainActivity extends AppCompatActivity implements ShakeDetector.OnShakeListener
{
    public static final int START_GAME_REQ = 31231;

    private SensorManager sensorManager;
    private Sensor linearAccelerometer;
    private ShakeDetector shakeDetector;
    private long questionRandomSeed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        linearAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        shakeDetector = new ShakeDetector(this);
        sensorManager.registerListener(shakeDetector, linearAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    public void changeOrientation(View v)
    {
        int x = isPortraitMode() ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(x);
    }

    private boolean isPortraitMode()
    {
        Display screenDisplay = getWindowManager().getDefaultDisplay();
        return screenDisplay.getRotation() == Surface.ROTATION_0
                || screenDisplay.getRotation() == Surface.ROTATION_180;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, linearAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }

    @Override
    public void onShake(int shakeCount, float acceleration)
    {
        Toast.makeText(this, "SHAKE no. " + shakeCount + " - " + acceleration, Toast.LENGTH_SHORT).show();
        if(shakeCount < 3)
        {
            questionRandomSeed *= (long) shakeCount * 1000;
        }
        else
        {
            sensorManager.unregisterListener(shakeDetector);
            startGame();
        }
    }

    private void startGame()
    {
        final Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra(GameActivity.ARG_RANDOM_SEED, questionRandomSeed);
        //startActivityForResult(gameIntent, START_GAME_REQ);
        startActivity(gameIntent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == START_GAME_REQ) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "KONIEC GRY", Toast.LENGTH_SHORT).show();
            }
        }
    }
}