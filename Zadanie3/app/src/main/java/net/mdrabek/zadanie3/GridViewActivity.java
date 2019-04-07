package net.mdrabek.zadanie3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new MyAdapter());
    }

    public class MyAdapter extends BaseAdapter {

        public Integer[] idObrazkow = {
                R.drawable.img1, R.drawable.img2, R.drawable.img3,
                R.drawable.img2, R.drawable.img3, R.drawable.img1,
                R.drawable.img3, R.drawable.img1, R.drawable.img2,
                R.drawable.img1, R.drawable.img2, R.drawable.img3,
                R.drawable.img2, R.drawable.img3, R.drawable.img1,
                R.drawable.img3, R.drawable.img1, R.drawable.img2,
                R.drawable.img1, R.drawable.img2, R.drawable.img3,
                R.drawable.img2, R.drawable.img3, R.drawable.img1,
                R.drawable.img3, R.drawable.img1, R.drawable.img2
        };

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
                mV = new ImageView(getApplicationContext());
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
}
