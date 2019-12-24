package com.jofiagtech.trivia.model;

public class Question
{
    private String questionText;
    private boolean mRightAnswer;

    public Question(){}

    public Question(String text, boolean rightAnswer)
    {
        questionText = text;
        mRightAnswer = rightAnswer;
    }

    public String getQuestionText()
    {
        return questionText;
    }

    public void setQuestionText(String questionText)
    {
        this.questionText = questionText;
    }

    public boolean isRightAnswer()
    {
        return mRightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer)
    {
        mRightAnswer = rightAnswer;
    }

    @Override
    public String toString()
    {
        return  '\'' +
                "Question{" +
                "questionText='" + questionText + '\'' +
                ", mRightAnswer=" + mRightAnswer +
                '}' +
                '\'' ;
    }
}
