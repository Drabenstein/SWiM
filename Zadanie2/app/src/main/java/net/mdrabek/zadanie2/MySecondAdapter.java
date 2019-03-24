package net.mdrabek.zadanie2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MySecondAdapter extends BaseAdapter {
    private String[] ltxt1 = {"Pozycja nr 1","Pozycja nr 2","Pozycja nr 3","Pozycja nr 4","Pozycja nr 5","Pozycja nr 6","Pozycja nr 7","Pozycja nr 8", "Pozycja nr 9", "Pozycja nr 10"};
    private String[] ltxt2 = {"Tekst 1","Tekst 2","Tekst 3","Tekst 4","Tekst 5","Tekst 6","Teskt 7","Tekst 8", "Tekst 9","Tekst 10"};
    private LayoutInflater inflater = null;
    boolean [] zazn_pozycje;
    LVitem myLVitem;

    public MySecondAdapter(Context context){
        super();
        zazn_pozycje = new boolean[ltxt1.length];
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ltxt1.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, null);
            myLVitem = new LVitem();
            myLVitem.tv1 = (TextView)convertView.findViewById(R.id.row_tv1);
            myLVitem.tv2 = (TextView)convertView.findViewById(R.id.row_tv2);
            myLVitem.img = (ImageView)convertView.findViewById(R.id.row_image);
            myLVitem.cBox = (CheckBox)convertView.findViewById(R.id.lrow_checkBox);
            convertView.setTag(myLVitem);
        }
        else
            myLVitem = (LVitem) convertView.getTag();

        myLVitem.tv1.setText(ltxt1[position]);
        myLVitem.tv2.setText(ltxt2[position]);
        myLVitem.cBox.setChecked(zazn_pozycje[position]);

        myLVitem.cBox.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                if(((CheckBox)v).isChecked())
                    zazn_pozycje[position]=true;
                else
                    zazn_pozycje[position]=false;
                Toast.makeText(v.getContext().getApplicationContext(), "Zanaczyłeś/odznaczyłeś: "+(position+1), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    private class LVitem{
        TextView tv1;
        TextView tv2;
        ImageView img;
        CheckBox cBox;
    }
}