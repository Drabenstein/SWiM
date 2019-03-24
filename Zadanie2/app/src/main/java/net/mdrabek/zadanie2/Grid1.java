package net.mdrabek.zadanie2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class Grid1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid1);

        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new MyAdapter(this));
    }
}