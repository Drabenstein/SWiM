package net.mdrabek.zadanie2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter {

    private Context context;

    public Integer[] idObrazkow = {
            R.drawable.img1, R.drawable.img2, R.drawable.img3,
            R.drawable.img2, R.drawable.img3, R.drawable.img1,
            R.drawable.img3, R.drawable.img1, R.drawable.img2
    };

    public MyAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public int getCount() {
        return idObrazkow.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView mV;

        if(convertView == null)
        {
            mV = new ImageView(context);
            mV.setLayoutParams(new GridView.LayoutParams(200, 200));
            mV.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mV.setPadding(8, 8, 8, 8);
        }
        else
        {
            mV = (ImageView) convertView;
        }

        mV.setImageResource(idObrazkow[position]);
        return mV;
    }
}
