package net.mdrabek.zadanie3;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private List<String> optionsList;
    private long lastClickTimeMillis = 0;
    private static final long MIN_CLICK_INTERVAL = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        optionsList = new ArrayList<>();
        optionsList.add("Telefony");
        optionsList.add("Todo's");
        optionsList.add("Emotikony");

        Spinner options = findViewById(R.id.optionsSpinner);
        options.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, optionsList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        options.setAdapter(adapter);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                final TextView textView = findViewById(R.id.counterTextView);
                textView.setText(Integer.toString(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }

    public void onResetTodosFileButtonClicked(View view)
    {
        File dir = getFilesDir();
        File file = new File(dir, ListActivity.listItemSourceFileName);
        boolean deleted = file.delete();
        String msg = deleted ? "Usunięto plik todos" : "Usuwanie pliku nie powiodło się";
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();
    }

    public void onGodModeSwitchActivated(View view)
    {
        View godmodeLayout = findViewById(R.id.godModeLayout);
        int newVisibility = godmodeLayout.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;
        godmodeLayout.setVisibility(newVisibility);
    }

    public void onRunActivityButtonClicked(View view)
    {
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - lastClickTimeMillis;

        lastClickTimeMillis = currentClickTime;

        if (elapsedTime <= MIN_CLICK_INTERVAL)
            return;

        view.setEnabled(false);

        Intent activityIntent = null;

        Spinner spinner = findViewById(R.id.optionsSpinner);
        int position = spinner.getSelectedItemPosition();

        switch (position)
        {
            case 0:
                activityIntent = new Intent(this, PhonesActivity.class);
                break;
            case 1:
                activityIntent = new Intent(this, ListActivity.class);
                break;
            case 2:
                activityIntent = new Intent(this, GridViewActivity.class);
                break;
        }

        if (activityIntent != null)
        {
            startActivity(activityIntent);
        }

        view.setEnabled(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
