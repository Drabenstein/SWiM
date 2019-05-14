package net.mdrabek.punsgame.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.mdrabek.punsgame.MyCategoryAdapter;
import net.mdrabek.punsgame.R;

public class CategoryRecyclerViewFragment extends Fragment
{
    private static final String ARG_CATEGORIES = "categories";
    private static final String ARG_CATEGORIES_IMAGE_IDS = "categories-image-ids";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private String[] myDataset;
    private int[] categoriesImagesId;

    private OnFragmentInteractionListener mListener;

    public CategoryRecyclerViewFragment()
    {
    }

    public static CategoryRecyclerViewFragment newInstance(@NonNull String[] categories, @NonNull int[] categoryImageIds)
    {
        CategoryRecyclerViewFragment fragment = new CategoryRecyclerViewFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_CATEGORIES, categories);
        args.putIntArray(ARG_CATEGORIES_IMAGE_IDS, categoryImageIds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            myDataset = getArguments().getStringArray(ARG_CATEGORIES);
            categoriesImagesId = getArguments().getIntArray(ARG_CATEGORIES_IMAGE_IDS);
        }
        else
        {
            myDataset = new String[1];
            categoriesImagesId = new int[1];
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_category_recycler_view, container, false);

        recyclerView = root.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyCategoryAdapter(myDataset, categoriesImagesId);
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }
}
