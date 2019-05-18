package net.mdrabek.punsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.widget.Toast;

import net.mdrabek.punsgame.Fragments.CategoryRecyclerViewFragment;
import net.mdrabek.punsgame.Fragments.ShakeFragment;
import net.mdrabek.punsgame.Models.Question;
import net.mdrabek.punsgame.Repositories.FakeQuestionRepository;
import net.mdrabek.punsgame.Repositories.QuestionRepository;
import net.mdrabek.punsgame.Sensors.ShakeDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements ShakeDetector.ShakeListener,
        CategoryRecyclerViewFragment.OnFragmentInteractionListener
{
    private static final int SHAKE_TAB_POSITION = 0;
    private static final int CATEGORIES_TAB_POSITION = 1;
    public static final int START_GAME_REQ = 31231;

    private SensorManager sensorManager;
    private Sensor linearAccelerometer;
    private ShakeDetector shakeDetector;
    private long questionRandomSeed;

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        linearAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        shakeDetector = new ShakeDetector(this);
        registerShakeDetector();

//        viewPager = findViewById(R.id.pager);
//        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), null);
//        viewPager.setAdapter(pagerAdapter);
//        TabLayout tabLayout = findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
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
    public void onBackPressed()
    {
        if (viewPager.getCurrentItem() == 0)
        {
            super.onBackPressed();
        }
        else
        {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {
        Toast.makeText(this, "SUKCES", Toast.LENGTH_SHORT).show();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
    {
        private TabRequestedListener listener;
        private List<Fragment> fragments;
        private final String[] tabTitles = new String[]{
                "Losowa gra", "Kategorie"
        };

        public ScreenSlidePagerAdapter(FragmentManager fm, TabRequestedListener listener)
        {
            super(fm);
            fragments = new ArrayList<>();
            fragments.add(new ShakeFragment());
            QuestionRepository repository = new FakeQuestionRepository();
            List<Question.QuestionCategory> categories = repository.getCategories();
            String[] categoriesNames = new String[categories.size()];
            List<String> categoriesNamesList = categories.stream().map(Question.QuestionCategory::toPolishName).collect(Collectors.toList());
            for (int i = 0; i < categoriesNamesList.size(); i++)
            {
                categoriesNames[i] = categoriesNamesList.get(i);
            }
            int[] categoriesImagesIds =
                    {
                            R.drawable.food_category_image,
                            R.drawable.animals_category_image,
                            R.drawable.fiction_characters_category_image,
                            R.drawable.adages_category_image
                    };
            //fragments.add(CategoryRecyclerViewFragment.newInstance(categoriesNames, categoriesImagesIds));
            this.listener = listener;
        }

        @Override
        public Fragment getItem(int position)
        {
            if (listener != null)
            {
                listener.onTabRequested(position);
            }

            return fragments.get(position);
        }

        @Override
        public int getCount()
        {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position)
        {
            return tabTitles[position];
        }
    }
}

interface TabRequestedListener
{
    void onTabRequested(int position);
}