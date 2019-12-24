package com.jofiagtech.trivia.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs
{
    private SharedPreferences mPreferences;

    public Prefs(Activity activity)
    {
        mPreferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void saveHighScore(int score)
    {
        int lastScore = mPreferences.getInt("high_score", 0);

        if (score > lastScore)
        {
            mPreferences.edit().putInt("high_score", score).apply();
        }
    }

    public int getHeighScore()
    {
       return mPreferences.getInt("high_score", 0);
    }

    public void saveQuestionState(int index)
    {
        mPreferences.edit().putInt("question_index", index).apply();
    }

    public int getQuestionState()
    {
        return mPreferences.getInt("question_index", 0);
    }
}
