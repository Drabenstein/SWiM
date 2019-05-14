package net.mdrabek.punsgame;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyCategoryAdapter extends RecyclerView.Adapter<MyCategoryAdapter.MyViewHolder>
{
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public TextView textView;

        public MyViewHolder(TextView v)
        {
            super(v);
            textView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyCategoryAdapter(String[] myDataset)
    {
        mDataset = myDataset;
    }

    @Override
    public MyCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);

        TextView v = root.findViewById(R.id.categoryTitleTextView);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.textView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount()
    {
        return mDataset.length;
    }
}
