package net.mdrabek.punsgame.Models;

import android.support.annotation.NonNull;

public class Question
{
    public enum QuestionCategory
    {
        FOOD,
        ANIMALS,
        FICTION_CHARACTERS,
        ADAGES;

        private static final String FoodPL = "Jedzenie";
        private static final String AnimalsPL = "Zwierzęta";
        private static final String FictionCharactersPL = "Postacie fikcyjne";
        private static final String AdagesPL = "Powiedzenia";

        public static String toPolishName(QuestionCategory category)
        {
            switch (category)
            {
                case FOOD:
                    return FoodPL;
                case ANIMALS:
                    return AnimalsPL;
                case FICTION_CHARACTERS:
                    return FictionCharactersPL;
                case ADAGES:
                    return AdagesPL;
                default:
                    return "";
            }
        }
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
