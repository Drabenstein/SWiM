package net.mdrabek.punsgame.Repositories;

import net.mdrabek.punsgame.Models.Question;

import java.util.ArrayList;

public interface QuestionRepository
{
    ArrayList<Question> getQuestionList(Question.QuestionCategory category);
    ArrayList<Question> getQuestionList();
}
