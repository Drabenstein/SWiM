package net.mdrabek.punsgame.Models;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuestionSetManager
{
    private int maxQuestionCount;
    private Set<Integer> questionsAlreadyChosen;
    private List<Question> questions;
    private Random random;

    public QuestionSetManager(@NonNull List<Question> questions, @NonNull Random random, int maxQuestionCount)
    {
        if(maxQuestionCount <= 0)
        {
            throw new IllegalArgumentException("maxQuestionCount must be greater than 0, but was: " + maxQuestionCount);
        }

        this.questions = questions;
        this.random = random;
        this.maxQuestionCount = maxQuestionCount;
        questionsAlreadyChosen = new HashSet<>();
    }

    public Question getNextQuestion() throws QuestionLimitReachedException
    {
        if(isLimitReached())
        {
            throw new QuestionLimitReachedException();
        }

        int nextIndex;

        do
        {
            nextIndex = random.nextInt(questions.size());
        }
        while (questionsAlreadyChosen.contains(nextIndex));

        questionsAlreadyChosen.add(nextIndex);

        return questions.get(nextIndex);
    }

    public boolean isLimitReached()
    {
        return questionsAlreadyChosen.size() >= maxQuestionCount;
    }

    public void clear()
    {
        questionsAlreadyChosen.clear();
    }
}
