package net.mdrabek.punsgame;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnQuestionSkippedListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment implements OnViewClicked
{
    private static final String ARG_QUESTIONS = "questions";
    private static final String ARG_CURRENT = "current";

    private List<String> questions;
    private int currentQuestionIndex;
    private OnQuestionSkippedListener listener;

    public QuestionFragment()
    {
        // Required empty public constructor
    }

    public static QuestionFragment newInstance(ArrayList<String> questions, int currentQuestionIndex)
    {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_QUESTIONS, questions);
        args.putInt(ARG_CURRENT, currentQuestionIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            questions = getArguments().getStringArrayList(ARG_QUESTIONS);
            currentQuestionIndex = getArguments().getInt(ARG_CURRENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_question, container, false);
        TextView questionTextView = root.findViewById(R.id.questionTextView);
        questionTextView.setText(questions.get(currentQuestionIndex));
        return root;
    }

    public void onQuestionSkipped()
    {
        if (listener != null)
        {
            listener.onQuestionSkipped(questions.get(currentQuestionIndex));
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnQuestionSkippedListener)
        {
            listener = (OnQuestionSkippedListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnQuestionSkippedListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onViewClicked(View view)
    {
        if(view.getId() == R.id.questionTextView || view.getId() == R.id.questionFrameLayout)
        {
            onQuestionSkipped();
        }
    }

    public interface OnQuestionSkippedListener
    {
        void onQuestionSkipped(String question);
    }


}
