package net.mdrabek.zadanie2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String[] list = {"Pozycja 1", "Pozycja 2", "Pozycja 3"};
    private String[] p = {"1", " 2", " 3"};

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner options = findViewById(R.id.spinner1);
        if(options != null)
        {
            options.setOnItemSelectedListener(this);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            options.setAdapter(adapter);
        }
    }

    public void onSimpleListButtonClick(View view)
    {
        final Intent simpleListActivityIntent = new Intent(this, Lista1.class);
        startActivity(simpleListActivityIntent);
    }

    public void onMultiChoiceListButtonClick(View view)
    {
        final Intent multichoiceListActivityIntent = new Intent(this, Lista2.class);
        startActivity(multichoiceListActivityIntent);
    }

    public void onGridViewButtonClick(View view)
    {
        final Intent gridViewActivityIntent = new Intent(this, Grid1.class);
        startActivity(gridViewActivityIntent);
    }

    public void onList3ButtonClick(View view)
    {
        final Intent list3ActivityIntent = new Intent(this, Lista3.class);
        startActivity(list3ActivityIntent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Wybrałeś: " + p[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
