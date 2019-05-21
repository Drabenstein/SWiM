package net.mdrabek.punsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewFlipper;

import net.mdrabek.punsgame.Fragments.TipFragment;
import net.mdrabek.punsgame.Sensors.ShakeDetector;

public class MainActivity extends AppCompatActivity implements ShakeDetector.ShakeListener,
        TipFragment.TipsSkippedListener
{
    public static final int START_GAME_REQ = 31231;

    private SensorManager sensorManager;
    private Sensor linearAccelerometer;
    private ShakeDetector shakeDetector;
    private long questionRandomSeed;

    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Fragment[] tips = new Fragment[]
                {
                  TipFragment.newInstance(R.drawable.tip1, R.drawable.pagination1),
                        TipFragment.newInstance(R.drawable.tip2, R.drawable.pagination2),
                        TipFragment.newInstance(R.drawable.tip3, R.drawable.pagination3),
                        TipFragment.newInstance(R.drawable.tip4, R.drawable.pagination4)
                };


        FragmentManager manager = getSupportFragmentManager();
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(manager)
        {
            @Override
            public Fragment getItem(int i)
            {
                return tips[i];
            }

            @Override
            public int getCount()
            {
                return tips.length;
            }
        });

        viewFlipper = findViewById(R.id.viewFlipper);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        linearAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        shakeDetector = new ShakeDetector(this);
        registerShakeDetector();
    }

    private void registerShakeDetector()
    {
        if (linearAccelerometer != null)
        {
            if (sensorManager == null)
            {
                sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            }

            if (shakeDetector == null)
            {
                shakeDetector = new ShakeDetector(this);
            }

            sensorManager.registerListener(shakeDetector, linearAccelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    private void unregisterShakeDetector()
    {
        if (sensorManager != null)
        {
            sensorManager.unregisterListener(shakeDetector);
        }
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
    public void onResume()
    {
        super.onResume();
        sensorManager.registerListener(shakeDetector, linearAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause()
    {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }

    @Override
    public void onShake(int shakeCount, float acceleration)
    {
        if (shakeCount < 3)
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

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == START_GAME_REQ)
        {
            if (resultCode == RESULT_OK)
            {
                Toast.makeText(this, "KONIEC GRY", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onTipsSkipped()
    {
        viewFlipper.showNext();
    }
}