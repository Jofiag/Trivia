package com.jofiagtech.trivia.data;

import com.jofiagtech.trivia.model.Question;

import java.util.ArrayList;

public interface QuestionAsyncResponce
{
    void processFinished(ArrayList<Question> questionArrayList);
}
