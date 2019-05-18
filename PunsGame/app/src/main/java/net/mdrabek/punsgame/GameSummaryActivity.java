package net.mdrabek.punsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameSummaryActivity extends AppCompatActivity
{
    public static final int REQ_GAME_SUMMARY = 99;
    public static final String ARG_CATEGORY = "categoryId";
    public static final String ARG_GOOD_ANSWERS = "good-answers";
    public static final String ARG_TOTAL_QUESTIONS = "total-questions";
    public static final String ARG_REPLAY = "replay";

    private int totalQuestions;
    private int goodAnswers;
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game_summary);

        totalQuestions = getIntent().getIntExtra(ARG_TOTAL_QUESTIONS, 0);
        goodAnswers = getIntent().getIntExtra(ARG_GOOD_ANSWERS, 0);
        categoryId = getIntent().getIntExtra(ARG_CATEGORY, 0);

        TextView scoreTV = findViewById(R.id.actualScoreTextView);
        scoreTV.setText(goodAnswers + "/" + totalQuestions);

        findViewById(R.id.backToHomeButton).setOnClickListener(this::onBackButtonClicked);
        findViewById(R.id.playAgainButton).setOnClickListener(this::onRestartButtonClicked);
    }

    public void onBackButtonClicked(View view)
    {
        Intent result = new Intent();
        result.putExtra(ARG_REPLAY, false);
        setResult(RESULT_OK, result);
        finish();
    }

    public void onRestartButtonClicked(View view)
    {
        Intent result = new Intent();
        result.putExtra(ARG_REPLAY, true);
        setResult(RESULT_OK, result);
        finish();
    }
}
