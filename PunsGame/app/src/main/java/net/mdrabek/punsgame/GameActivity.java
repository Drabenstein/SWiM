package net.mdrabek.punsgame;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import net.mdrabek.punsgame.Fragments.GiveUpFragment;
import net.mdrabek.punsgame.Fragments.GoodAnswerFragment;
import net.mdrabek.punsgame.Fragments.QuestionFragment;
import net.mdrabek.punsgame.Fragments.TimePassedFragment;
import net.mdrabek.punsgame.Models.Question;
import net.mdrabek.punsgame.Models.QuestionLimitReachedException;
import net.mdrabek.punsgame.Models.QuestionSetManager;
import net.mdrabek.punsgame.Repositories.FakeQuestionRepository;
import net.mdrabek.punsgame.Repositories.QuestionRepository;
import net.mdrabek.punsgame.Sensors.CloseProximityDetector;
import net.mdrabek.punsgame.Sensors.RotationDetector;

import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements QuestionFragment.OnQuestionEventListener,
        GiveUpFragment.OnGiveUpTimeoutExceededListener, TimePassedFragment.OnTimePassedTimeoutExceededListener,
        GoodAnswerFragment.OnGoodAnswerTImeoutExceededListener, CloseProximityDetector.CloseProximityListener,
        RotationDetector.RotationChangedListener
{
    public static final int ROTATION_SAMPLING_RATE = 600;
    public static final int ROTATION_MAX_LATENCY = 200;
    public static final int INFO_TIMEOUT = 800;

    public static final String TAG_QUESTION_FRAGMENT = QuestionFragment.class.getSimpleName();

    public static final String ARG_RANDOM_SEED = "random-seed";
    public static final String ARG_QUESTION_COUNT = "question-count";

    private FragmentManager fragmentManager;
    private Random random;
    private QuestionSetManager questionSetManager;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private CloseProximityDetector proximityDetector;

    private int maxQuestionCount = 10;

    private Sensor rotationVectorSensor;
    private RotationDetector rotationDetector;

    private QuestionFragment questionFragment;
    private TimePassedFragment timePassedFragment;
    private GiveUpFragment giveUpFragment;
    private GoodAnswerFragment goodAnswerFragment;

    private boolean moveToNextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        QuestionRepository questionRepository = new FakeQuestionRepository();

        maxQuestionCount = getIntent().getIntExtra(ARG_QUESTION_COUNT, 10);
        long randomSeed = getIntent().getLongExtra(ARG_RANDOM_SEED, System.currentTimeMillis());
        random = new Random(randomSeed + System.currentTimeMillis());
        int category = random.nextInt(Question.QuestionCategory.values().length);
        List<Question> questionList = questionRepository.getQuestionList(Question.QuestionCategory.values()[category]);
        questionSetManager = new QuestionSetManager(questionList, random, maxQuestionCount);

        try
        {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            questionFragment = QuestionFragment.newInstance(questionSetManager.getNextQuestion());
            fragmentTransaction.add(R.id.gameFrameLayout, questionFragment, TAG_QUESTION_FRAGMENT);
            fragmentTransaction.commit();
        }
        catch (QuestionLimitReachedException e)
        {
            finish();
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (proximitySensor != null)
        {
            proximityDetector = new CloseProximityDetector(this);
            sensorManager.registerListener(proximityDetector, proximitySensor, SensorManager.SENSOR_DELAY_UI);
        }

        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
        if (rotationVectorSensor != null)
        {
            rotationDetector = new RotationDetector(getWindowManager(), this);
            registerRotationSensor();
        }
    }

    @Override
    protected void onPause()
    {
        if (proximitySensor != null)
        {
            if (proximityDetector != null)
            {
                sensorManager.unregisterListener(proximityDetector);
            }
        }

        unregisterRotationSensor();
        super.onPause();
    }

    private void registerRotationSensor()
    {
        if (rotationVectorSensor != null)
        {
            if (rotationDetector == null)
            {
                rotationDetector = new RotationDetector(getWindowManager(), this);
            }

            sensorManager.registerListener(rotationDetector, rotationVectorSensor, ROTATION_SAMPLING_RATE, ROTATION_MAX_LATENCY);
        }
    }

    private void unregisterRotationSensor()
    {
        if (rotationVectorSensor != null)
        {
            if (rotationDetector != null)
            {
                sensorManager.unregisterListener(rotationDetector);
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (proximitySensor != null)
        {
            if (proximityDetector == null)
            {
                proximityDetector = new CloseProximityDetector(this);
            }

            sensorManager.registerListener(proximityDetector, proximitySensor, 1000, 500);
        }
        registerRotationSensor();
    }

    @Override
    public void onQuestionSkipped(Question question)
    {
        Toast.makeText(this, "SKIP", Toast.LENGTH_SHORT).show();
        if (giveUpFragment == null)
        {
            giveUpFragment = GiveUpFragment.newInstance(INFO_TIMEOUT);
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.gameFrameLayout, giveUpFragment);
        transaction.commit();
    }

    @Override
    public void onQuestionTimePassed(Question question)
    {
        Toast.makeText(this, "TIME", Toast.LENGTH_SHORT).show();
        if (timePassedFragment == null)
        {
            timePassedFragment = TimePassedFragment.newInstance(INFO_TIMEOUT);
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.gameFrameLayout, timePassedFragment);
        transaction.commit();
    }

    @Override
    public void onGiveUpTimeoutExceeded()
    {
        Toast.makeText(this, "GIVE UP FINISHED", Toast.LENGTH_SHORT).show();

        try
        {
            questionFragment = QuestionFragment.newInstance(questionSetManager.getNextQuestion());
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.gameFrameLayout, questionFragment, TAG_QUESTION_FRAGMENT);
            transaction.commit();
        }
        catch (QuestionLimitReachedException e)
        {
            finish();
        }
    }

    @Override
    public void onTimePassedTimeoutExceeded()
    {
        Toast.makeText(this, "TIME PASSED FINISHED", Toast.LENGTH_SHORT).show();

        try
        {
            questionFragment = QuestionFragment.newInstance(questionSetManager.getNextQuestion());
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.gameFrameLayout, questionFragment, TAG_QUESTION_FRAGMENT);
            transaction.commit();
        }
        catch (QuestionLimitReachedException e)
        {
            finish();
        }
    }

    @Override
    public void onGoodAnswerTimeoutExceeded()
    {
        Toast.makeText(this, "GOOD ANSWER FINISHED", Toast.LENGTH_SHORT).show();
        moveToNextQuestion = true;
    }

    @Override
    public void onProximityClose()
    {
        Fragment fragment = fragmentManager.findFragmentByTag(TAG_QUESTION_FRAGMENT);
        if (fragment != null && fragment.isVisible())
        {
            questionFragment.onClick(null);
        }
    }

    @Override
    public void onOrientationChanged(RotationDetector.RotationState state)
    {
        Fragment questionFragment = fragmentManager.findFragmentByTag(TAG_QUESTION_FRAGMENT);

        if(moveToNextQuestion && state == RotationDetector.RotationState.PERPENDICULAR)
        {
            moveToNextQuestion = false;
            try
            {
                questionFragment = QuestionFragment.newInstance(questionSetManager.getNextQuestion());
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.gameFrameLayout, questionFragment, TAG_QUESTION_FRAGMENT);
                transaction.commit();
            }
            catch (QuestionLimitReachedException e)
            {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else if (questionFragment != null && questionFragment.isVisible()
                && state != RotationDetector.RotationState.PERPENDICULAR)
        {
            if (goodAnswerFragment == null)
            {
                goodAnswerFragment = GoodAnswerFragment.newInstance(INFO_TIMEOUT);
            }

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.gameFrameLayout, goodAnswerFragment);
            transaction.commit();
        }
    }
}