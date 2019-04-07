package net.mdrabek.zadanie3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCustomListButtonClicked(View view)
    {
        final Intent customListActivityIntent = new Intent(this, PhonesActivity.class);
        startActivity(customListActivityIntent);
    }

    public void onNormalListButtonClicked(View view)
    {
        final Intent normalListActivityIntent = new Intent(this, ListActivity.class);
        startActivity(normalListActivityIntent);
    }

    public void onGridViewButtonClicked(View view)
    {
        final Intent gridViewActivityIntent = new Intent(this, GridViewActivity.class);
        startActivity(gridViewActivityIntent);
    }
}
