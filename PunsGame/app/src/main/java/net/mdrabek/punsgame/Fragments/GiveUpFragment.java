package net.mdrabek.punsgame.Fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.mdrabek.punsgame.R;

public class GiveUpFragment extends Fragment
{
    private static final String ARG_TIMEOUT = "timeout";

    private OnGiveUpTimeoutExceededListener listener;
    private int timeout = 1000;

    public GiveUpFragment()
    {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param timeout Timeout value after which fragment sends event
     * @return A new instance of fragment GiveUpFragment.
     */
    public static GiveUpFragment newInstance(int timeout)
    {
        GiveUpFragment fragment = new GiveUpFragment();
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
        View root = inflater.inflate(R.layout.fragment_give_up, container, false);
        new CountDownTimer(timeout, timeout)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
            }

            @Override
            public void onFinish()
            {
                onGiveUpTimeoutExceeded();
            }
        }.start();

        return root;
    }

    private void onGiveUpTimeoutExceeded()
    {
        if (listener != null)
        {
            listener.onGiveUpTimeoutExceeded();
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnGiveUpTimeoutExceededListener)
        {
            listener = (OnGiveUpTimeoutExceededListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnGoodAnswerTImeoutExceededListener");
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
    public interface OnGiveUpTimeoutExceededListener
    {
        void onGiveUpTimeoutExceeded();
    }
}
