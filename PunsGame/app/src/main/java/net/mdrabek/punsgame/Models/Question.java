package net.mdrabek.punsgame.Models;

import android.support.annotation.NonNull;

public class Question
{
    public enum QuestionCategory
    {
        FOOD,
        ANIMALS,
        FICTION_CHARACTERS,
        ADAGES
    }

    private QuestionCategory category;
    private String description;

    public Question(@NonNull QuestionCategory category, @NonNull String description)
    {
        this.category = category;
        this.description = description;
    }

    public QuestionCategory getCategory()
    {
        return category;
    }

    public String getDescription()
    {
        return description;
    }
}
