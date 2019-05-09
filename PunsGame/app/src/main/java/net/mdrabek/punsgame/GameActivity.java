package net.mdrabek.punsgame;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import net.mdrabek.punsgame.Fragments.QuestionFragment;
import net.mdrabek.punsgame.Models.Question;
import net.mdrabek.punsgame.Repositories.FakeQuestionRepository;
import net.mdrabek.punsgame.Repositories.QuestionRepository;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements QuestionFragment.OnQuestionEventListener
{
    public static final String ARG_RANDOM_SEED = "random-seed";
    public static final String ARG_QUESTION_COUNT = "question-count";

    public static final String ARG_GOOD_ANSWERS = "good-answers";
    public static final String ARG_WRONG_ANSWERS = "wrong-answers";


    private QuestionRepository questionRepository;
    private QuestionFragment questionFragment;
    private ArrayList<Question> questionList;
    private int maxQuestionCount;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        questionRepository = new FakeQuestionRepository();

        maxQuestionCount = getIntent().getIntExtra(ARG_QUESTION_COUNT, 10);
        long randomSeed = getIntent().getLongExtra(ARG_RANDOM_SEED, System.currentTimeMillis());
        random = new Random(randomSeed + System.currentTimeMillis());
        int category = random.nextInt(Question.QuestionCategory.values().length);
        questionList = questionRepository.getQuestionList(Question.QuestionCategory.values()[category]);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        questionFragment = QuestionFragment.newInstance(questionList.get(random.nextInt(questionList.size())));
        fragmentTransaction.add(R.id.gameFrameLayout, questionFragment);
        fragmentTransaction.commit();

//        findViewById(R.id.questionTextView).setOnClickListener(this);
//        findViewById(R.id.questionTimerTextView).setOnClickListener(this);
//        findViewById(R.id.questionFrameLayout).setOnClickListener(this);
    }

    @Override
    public void onQuestionSkipped(Question question)
    {
        Toast.makeText(this, "SKIP", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQuestionTimePassed(Question question)
    {
        Toast.makeText(this, "TIME", Toast.LENGTH_SHORT).show();
    }
}