package net.mdrabek.punsgame.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.mdrabek.punsgame.Models.Question;
import net.mdrabek.punsgame.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnQuestionEventListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment implements View.OnClickListener
{
    private static final String ARG_QUESTION_DESCRIPTION = "question-description";
    private static final String ARG_QUESTION_CATEGORY = "question-category";

    private static final int TIME_PER_QUESTION = 60000;
    private static final int TIME_COUNT_INTERVAL = 1000;
    private static final int MINUTE_IN_MILLIS = 60000;
    private static final int SECOND_IN_MILLIS = 1000;

    private Question question;
    private CountDownTimer timer;
    private OnQuestionEventListener listener;

    public QuestionFragment()
    {
        // Required empty public constructor
    }

    public static QuestionFragment newInstance(Question question)
    {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_CATEGORY, question.getCategory().name());
        args.putString(ARG_QUESTION_DESCRIPTION, question.getDescription());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            Question.QuestionCategory category = Question.QuestionCategory.valueOf(
                    getArguments().getString(ARG_QUESTION_CATEGORY));
            String description = getArguments().getString(ARG_QUESTION_DESCRIPTION);
            question = new Question(category, description);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_question, container, false);
        TextView questionTextView = root.findViewById(R.id.questionTextView);
        questionTextView.setText(question.getDescription());

        root.findViewById(R.id.questionTextView).setOnClickListener(this);
        root.findViewById(R.id.questionTimerTextView).setOnClickListener(this);
        root.findViewById(R.id.questionFrameLayout).setOnClickListener(this);

        final TextView timerTextView = root.findViewById(R.id.questionTimerTextView);
        timer = new CountDownTimer(TIME_PER_QUESTION, TIME_COUNT_INTERVAL) {

            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / MINUTE_IN_MILLIS;
                long seconds = (millisUntilFinished % MINUTE_IN_MILLIS) / SECOND_IN_MILLIS;
                timerTextView.setText(minutes + ":" + seconds);
            }

            public void onFinish() {
                timerTextView.setText("0:00");
                onQuestionTimePassed();
            }
        }.start();
        return root;
    }

    public void onQuestionTimePassed()
    {
        if(listener != null)
        {
            listener.onQuestionTimePassed(question);
        }
    }

    public void onQuestionSkipped()
    {
        if (listener != null)
        {
            listener.onQuestionSkipped(question);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnQuestionEventListener)
        {
            listener = (OnQuestionEventListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnQuestionEventListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onClick(View v)
    {
        onQuestionSkipped();
    }

    public interface OnQuestionEventListener
    {
        void onQuestionSkipped(Question question);
        void onQuestionTimePassed(Question question);
    }
}
