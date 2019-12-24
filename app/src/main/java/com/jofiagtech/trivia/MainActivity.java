package com.jofiagtech.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jofiagtech.trivia.data.QuestionAsyncResponce;
import com.jofiagtech.trivia.data.QuestionBank;
import com.jofiagtech.trivia.model.Question;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView mQuestionCounterText;
    private TextView mQuestionText;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPreviewButton;
    private ImageButton mNextButton;
    private CardView mCardView;
    private TextView mScoreText;
    private TextView mHeightScoreText;
    private int mScore = 0;
    private int mHeightScore = 0;
    private int mCurrentQuestionIndex = 0;
    ArrayList<Question> mQuestionBank;
    private static final String DATE_ID = "user_score";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionCounterText = findViewById(R.id.question_counter_text);
        mQuestionText = findViewById(R.id.question_text);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mPreviewButton = findViewById(R.id.preview_button);
        mNextButton = findViewById(R.id.next_button);
        mCardView = findViewById(R.id.cardView);
        mScoreText = findViewById(R.id.score_text);
        mHeightScoreText = findViewById(R.id.height_core_text);

        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mPreviewButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);

        mQuestionBank = new QuestionBank().getQuestionBank(new QuestionAsyncResponce()
        {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList)
            {
                updatePrint();
                Log.d("JSON", "processFinished: " + questionArrayList);
            }
        });

        getBestScoreSaved();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.true_button:
                checkAnswer(true);
                updatePrint();
                break;
            case R.id.false_button:
                checkAnswer(false);
                updatePrint();
                break;
            case R.id.preview_button:
                if (mCurrentQuestionIndex > 0)
                    mCurrentQuestionIndex = (mCurrentQuestionIndex - 1) % mQuestionBank.size();
                else if (mCurrentQuestionIndex == 0)
                    mCurrentQuestionIndex = mQuestionBank.size()-1;
                updatePrint();
                break;
            case R.id.next_button:
                    goToNextQuestion();
                updatePrint();
                break;
                default:
                    break;
        }
    }

    private void updatePrint()
    {
        mQuestionText.setText(mQuestionBank.get(mCurrentQuestionIndex).getQuestionText());
        mQuestionCounterText.setText(String.format("%d/%d", mCurrentQuestionIndex + 1, mQuestionBank.size()));

        mHeightScoreText.setText(String.format("Best : %s", String.valueOf(mHeightScore)));
        mScoreText.setText(String.format("Now : %s", String.valueOf(mScore)));
    }

    private void checkAnswer(boolean UserAnswer)
    {
        boolean rightAnswer = mQuestionBank.get(mCurrentQuestionIndex).isRightAnswer();

        int toastMessage = 0;

        if (UserAnswer == rightAnswer)
        {
            fadeView();
            toastMessage = R.string.right_answer;
            if (mScore >= 0)
                mScore++;
        }
        else
        {
            shakeAnimation();
            toastMessage = R.string.wrong_answer;
            if (mScore > 0)
                mScore--;
        }

        saveBestScoreInDisque();
        goToNextQuestion();

        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    private void goToNextQuestion()
    {
        mCurrentQuestionIndex = (mCurrentQuestionIndex + 1) % mQuestionBank.size();
    }

    //Animation when answering wrong
    private void shakeAnimation()
    {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        mCardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                mCardView.setBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                mCardView.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    //Animation when answering right
    private void fadeView()
    {
        AlphaAnimation fade = new AlphaAnimation(1.0f, 0.0f);
        fade.setDuration(100);
        fade.setRepeatCount(1);
        fade.setRepeatMode(Animation.REVERSE);

        mCardView.setAnimation(fade);

        fade.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                mCardView.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                mCardView.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });

    }

    private void saveBestScoreInDisque()
    {
        if (mScore > mHeightScore)
        {
            mHeightScore = mScore;
            SharedPreferences sharedPreferences = getSharedPreferences(DATE_ID, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("best_score", mHeightScore);
            editor.apply();
        }
    }

    private void getBestScoreSaved()
    {
        SharedPreferences dataSP = getSharedPreferences(DATE_ID, MODE_PRIVATE);

        if (dataSP.getInt("best_score", -1) != -1)
            mHeightScore = dataSP.getInt("best_score", -1);
    }
}
