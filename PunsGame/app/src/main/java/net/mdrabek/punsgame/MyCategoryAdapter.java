package net.mdrabek.punsgame;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MyCategoryAdapter extends RecyclerView.Adapter<MyCategoryAdapter.MyViewHolder>
{
    private String[] dataset;
    private Context context;
    private int[] categoryImageIds;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView imageView;
//        public TextView textView;

        public MyViewHolder(ImageView v)
        {
            super(v);
//            root = v;
//            root.setOnClickListener(this);
            v.setOnClickListener(this);
            imageView = v.findViewById(R.id.categoryImageView);
//            textView = v.findViewById(R.id.categoryTextView);
        }

        @Override
        public void onClick(View v)
        {
            Toast.makeText(imageView.getContext(), "SUKCES", Toast.LENGTH_SHORT).show();
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyCategoryAdapter(@NonNull String[] myDataset, @NonNull int[] categoryImageIds)
    {
        dataset = myDataset;
        this.categoryImageIds = categoryImageIds;
    }

    @Override
    public MyCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);
        context = root.getContext();
        ImageView v = root.findViewById(R.id.categoryImageView);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.setIsRecyclable(true);
        Glide.with(context).load(categoryImageIds[position]).into(holder.imageView);
        //holder.textView.setText(dataset[position]);
    }

    @Override
    public int getItemCount()
    {
        return dataset.length;
    }
}
