package net.mdrabek.punsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.mdrabek.punsgame.Models.Question;

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

        ImageView categoryImageView = findViewById(R.id.categoryImageView);

        switch (Question.QuestionCategory.values()[categoryId])
        {
            case FOOD:
                Glide.with(this).load(R.drawable.food_category_image).into(categoryImageView);
                break;
            case ANIMALS:
                Glide.with(this).load(R.drawable.animals_category_image).into(categoryImageView);
                break;
            case FICTION_CHARACTERS:
                Glide.with(this).load(R.drawable.fiction_characters_category_image).into(categoryImageView);
                break;
            case ADAGES:
                Glide.with(this).load(R.drawable.adages_category_image).into(categoryImageView);
                break;
        }

        TextView categoryTV = findViewById(R.id.categoryNameTextView);
        categoryTV.setText(Question.QuestionCategory.toPolishName(Question.QuestionCategory.values()[categoryId]));

        TextView scoreTV = findViewById(R.id.scoreTextView);
        scoreTV.setText(goodAnswers + "/" + totalQuestions);

        findViewById(R.id.backButton).setOnClickListener(this::onBackButtonClicked);
        findViewById(R.id.restartButton).setOnClickListener(this::onRestartButtonClicked);
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
