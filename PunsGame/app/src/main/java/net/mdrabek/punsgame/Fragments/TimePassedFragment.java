package net.mdrabek.punsgame.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.mdrabek.punsgame.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTimePassedTimeoutExceededListener} interface
 * to handle interaction events.
 * Use the {@link TimePassedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimePassedFragment extends Fragment
{
    private static final String ARG_TIMEOUT = "timeout";

    private OnTimePassedTimeoutExceededListener listener;
    private int timeout = 2000;

    public TimePassedFragment()
    {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param timeout Timeout value after which fragment sends event
     * @return A new instance of fragment TimePassedFragment.
     */
    public static TimePassedFragment newInstance(int timeout)
    {
        TimePassedFragment fragment = new TimePassedFragment();
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
        View root = inflater.inflate(R.layout.fragment_time_passed, container, false);
        new CountDownTimer(timeout, timeout)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
            }

            @Override
            public void onFinish()
            {
                onTimeoutExceeded();
            }
        };

        return root;
    }

    private void onTimeoutExceeded()
    {
        if(listener != null)
        {
            listener.onTimeoutExceeded();
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnTimePassedTimeoutExceededListener)
        {
            listener = (OnTimePassedTimeoutExceededListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnTimePassedTimeoutExceededListener");
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
    public interface OnTimePassedTimeoutExceededListener
    {
        void onTimeoutExceeded();
    }
}
