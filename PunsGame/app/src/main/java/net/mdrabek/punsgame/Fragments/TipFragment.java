package net.mdrabek.punsgame.Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import net.mdrabek.punsgame.R;

public class TipFragment extends Fragment
{
    public static final String ARG_PAGINATION_DRAWABLE_ID = "pagination-drawable-id";
    public static final String ARG_TIP_DRAWABLE_ID = "tip-drawable-id";

    private int paginationDrawableId;
    private int tipDrawableId;

    private TipsSkippedListener listener;

    public TipFragment()
    {
    }

    public static TipFragment newInstance(int tipDrawableId, int paginationDrawableId)
    {
        TipFragment fragment = new TipFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TIP_DRAWABLE_ID, tipDrawableId);
        args.putInt(ARG_PAGINATION_DRAWABLE_ID, paginationDrawableId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            tipDrawableId = getArguments().getInt(ARG_TIP_DRAWABLE_ID);
            paginationDrawableId = getArguments().getInt(ARG_PAGINATION_DRAWABLE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_tip, container, false);

        final ImageView tipImageView = root.findViewById(R.id.tipImageView);

        Glide.with(this)
                .load(tipDrawableId)
                .into(tipImageView);

        final ImageView paginationImageView = root.findViewById(R.id.paginationImageView);

        Glide.with(this)
                .load(paginationDrawableId)
                .into(paginationImageView);

        final Button skipButton = root.findViewById(R.id.tipSkipButton);
        skipButton.setOnClickListener(this::onSkipTipButtonClicked);

        return root;
    }

    public void onSkipTipButtonClicked(View view)
    {
        onTipsSkippedButtonClick();
    }

    public void onTipsSkippedButtonClick()
    {
        if (listener != null)
        {
            listener.onTipsSkipped();
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof TipsSkippedListener)
        {
            listener = (TipsSkippedListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement TipsSkippedListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }

    public interface TipsSkippedListener
    {
        void onTipsSkipped();
    }
}
