package net.mdrabek.punsgame.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.mdrabek.punsgame.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StarterCountingTimeoutExceededListener} interface
 * to handle interaction events.
 * Use the {@link CountingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CountingFragment extends Fragment
{
    private static final long SECOND_IN_MILLIS = 1000;
    private static final String ARG_TIMEOUT = "timeout";

    private int timeout;

    private StarterCountingTimeoutExceededListener listener;

    public CountingFragment()
    {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param timeout Time to count
     * @return A new instance of fragment CountingFragment.
     */
    public static CountingFragment newInstance(int timeout)
    {
        CountingFragment fragment = new CountingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TIMEOUT, timeout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            timeout = getArguments().getInt(ARG_TIMEOUT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_counting, container, false);
        final TextView timerTV = root.findViewById(R.id.startTimeTextView);
        new CountDownTimer(timeout, SECOND_IN_MILLIS)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                timerTV.setText(Long.toString( 1 + millisUntilFinished / SECOND_IN_MILLIS));
            }

            @Override
            public void onFinish()
            {
                onTimeoutExceeded();
            }
        }.start();

        return root;
    }

    public void onTimeoutExceeded()
    {
        if (listener != null)
        {
            listener.onStarterCountingTimeoutExceeded();
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof StarterCountingTimeoutExceededListener)
        {
            listener = (StarterCountingTimeoutExceededListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement StarterCountingTimeoutExceededListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface StarterCountingTimeoutExceededListener
    {
        void onStarterCountingTimeoutExceeded();
    }
}
