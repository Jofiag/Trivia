package com.jofiagtech.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.jofiagtech.trivia.controller.AppController;
import com.jofiagtech.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank
{
   /* private ArrayList<Question> mQuestionArrayList = new ArrayList<>();
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    private AppController mAppController;

    public List<Question> getQuestions(final AnswerAsynResponse callBack){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                       for (int i = 0; i < response.length(); i++){
                           try
                           {
                               Question question = new Question();
                               question.setQuestionText(response.getJSONArray(i).getString(0));
                               question.setRightAnswer(response.getJSONArray(i).getBoolean(1));

                               mQuestionArrayList.add(question);

                               //Log.d("QUESTION", "onResponse: " + question);
                           } catch (JSONException e)
                           {
                               e.printStackTrace();
                           }
                       }

                       if (callBack != null)
                           callBack.processFinished(mQuestionArrayList);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return mQuestionArrayList;
    }*/

   private ArrayList<Question> mQuestionBank = new ArrayList<>();
   private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
   private AppController mAppController;

   public ArrayList<Question> getQuestionBank(final QuestionAsyncResponce callback){
       JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
               new Response.Listener<JSONArray>()
               {
                   @Override
                   public void onResponse(JSONArray response)
                   {
                        for (int i = 0; i < response.length(); i++){
                            try
                            {
                                JSONArray jsonArray = response.getJSONArray(i);
                                Question question = new Question();

                                question.setQuestionText(jsonArray.getString(0));
                                question.setRightAnswer(jsonArray.getBoolean(1));

                                mQuestionBank.add(question);

                                //Log.d("JSON", "onResponse: " + question);
                                        /*+ question.isRightAnswer()
                                        + " "
                                        + question.getQuestionText());*/
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        if (callback != null)
                            callback.processFinished(mQuestionBank);
                   }
               },
               new Response.ErrorListener()
               {
                   @Override
                   public void onErrorResponse(VolleyError error)
                   {

                   }
               });

       AppController.getInstance().addToRequestQueue(jsonArrayRequest);

       return mQuestionBank;
   }

}
