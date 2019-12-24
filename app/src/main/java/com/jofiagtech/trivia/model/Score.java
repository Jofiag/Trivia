package com.jofiagtech.trivia.model;

public class Score
{
    private int score;

    public Score()
    {
    }

    public Score(int score)
    {
        this.score = score;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    @Override
    public String toString()
    {
        return "Score{" +
                "score=" + score +
                '}';
    }
}
