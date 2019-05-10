package net.mdrabek.punsgame;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import net.mdrabek.punsgame.Fragments.GiveUpFragment;
import net.mdrabek.punsgame.Fragments.QuestionFragment;
import net.mdrabek.punsgame.Fragments.TimePassedFragment;
import net.mdrabek.punsgame.Models.Question;
import net.mdrabek.punsgame.Models.QuestionLimitReachedException;
import net.mdrabek.punsgame.Models.QuestionSetManager;
import net.mdrabek.punsgame.Repositories.FakeQuestionRepository;
import net.mdrabek.punsgame.Repositories.QuestionRepository;

import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements QuestionFragment.OnQuestionEventListener,
        GiveUpFragment.OnGiveUpTimeoutExceededListener, TimePassedFragment.OnTimePassedTimeoutExceededListener
{
    public static final int INFO_TIMEOUT = 800;

    public static final String ARG_RANDOM_SEED = "random-seed";
    public static final String ARG_QUESTION_COUNT = "question-count";

    public static final String ARG_GOOD_ANSWERS = "good-answers";
    public static final String ARG_WRONG_ANSWERS = "wrong-answers";

    private FragmentManager fragmentManager;
    private int maxQuestionCount = 10;
    private Random random;
    private QuestionSetManager questionSetManager;

    private QuestionFragment questionFragment;
    private TimePassedFragment timePassedFragment;
    private GiveUpFragment giveUpFragment;

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
            fragmentTransaction.add(R.id.gameFrameLayout, questionFragment);
            fragmentTransaction.commit();
        }
        catch (QuestionLimitReachedException e)
        {
            finish();
        }
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
            transaction.replace(R.id.gameFrameLayout, questionFragment);
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
        if (questionFragment == null)
        {
            questionFragment = new QuestionFragment();
        }

        try
        {
            questionFragment.setQuestion(questionSetManager.getNextQuestion());
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.gameFrameLayout, questionFragment);
            transaction.commit();
        }
        catch (QuestionLimitReachedException e)
        {
            finish();
        }
    }
}