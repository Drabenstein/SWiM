package net.mdrabek.punsgame.Models;

public class QuestionLimitReachedException extends Exception
{
    public QuestionLimitReachedException()
    {
        super();
    }

    public QuestionLimitReachedException(String message)
    {
        super(message);
    }
}
